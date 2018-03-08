package pl.sternik.pb.robocode;

import robocode.*;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Probot extends AdvancedRobot {

	private Opponent opponent;			// przeciwnik
	private byte moveDir = 1;			// W którym kierunku sie poruszamy (-1, 1)
	
	@Override
	public void run() {
		
		setColors(	Color.GREEN,
				Color.GREEN,
				Color.BLACK,
				Color.RED,
				Color.WHITE);
		
		opponent = new Opponent(Double.MAX_VALUE);
		
		// Obracanie radaru o 360 stopni 
		// w celu znalezienia przeciwnika:
		turnRadarRightRadians(2* Math.PI);
		
		// Wlaczanie obrotów radaru i 
		// dziala czolgu w sposób niezale¿ny:
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		//TODO:
		// -> System "uciekania" <- w przypadku trafienia
		do {
			moveManager(false);
			scanManager();
			fireManager();
			execute();
		}while(true);
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		/*
		 * Gdy radar zeskanuje przeciwnika, musimy
		 * zmienic wartosc, jezeli znaleziony
		 * przeciwnik jest najblizej nas 
		 * lub jest to ten sam przeciwnik
		 * */
		
		if((event.getName().equals(opponent.getRobotName())) || 
			(event.getDistance() < opponent.getDistance()))
		{
			/*
			 * 
			 * Zaglebiajac sie gdzies w tych materialach:
			 * https://math.stackexchange.com/questions/2254901/compute-absolute-bearing-between-two-points-2d
			 * 
			 * */
			double absoluteBearingRadians = (getHeadingRadians() + event.getBearingRadians()) % (2*Math.PI);
			double x = getX() + Math.sin(absoluteBearingRadians)*event.getDistance();
			double y = getY() + Math.cos(absoluteBearingRadians)*event.getDistance();

			opponent.setupOpponent(event, new Point(x, y));
		}
	}
	
	@Override
	public void onRobotDeath(RobotDeathEvent event) {
		/*
		 * Po zastrzeleniu przeciwnika, musimy
		 * nadac mu max dystans, aby móc ponownie
		 * wyszukac kolejnego przeciwnika
		 * */
		if(event.getName().equals(opponent.getRobotName()))
			opponent.setDistance(Double.MAX_VALUE);
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		
		// W momencie uderzenia sciany nalezy zmienic kierunek
		moveManager(true);
	}
	
	private void moveManager(boolean change) {

		int moduloRange = ThreadLocalRandom.current().nextInt(8, 22);		
		
		if(getTime() % moduloRange == 0 || change) {
			moveDir *= -1;
			setAhead((6000 / moduloRange) * moveDir);
		}
		

		setTurnRightRadians((Math.PI / 2) + opponent.getBearing());
	}
	
	private void scanManager() {
		
		double scanRadiansOffset;
		
		// Gdy dawno nie widzielismy przeciwnika
		// musimy przeskanowac mape raz jeszcze.
		if(getTime() - opponent.getLastScanTime() > 4) {
			scanRadiansOffset = 360.0d;
		}
		else {
			Point myPosition = new Point(getX(), getY());
			scanRadiansOffset = getRadarHeadingRadians() - Point.calculateAbsoluteBearing(myPosition, opponent.getPosition()); 
		
			if(scanRadiansOffset < 0) scanRadiansOffset -= Math.PI/8;
			else scanRadiansOffset += Math.PI/8;
		}
		
		setTurnRadarLeftRadians(calculateBearingNormalised(scanRadiansOffset));
	}
	
	private void fireManager() {
		
		/*
		 * http://mark.random-article.com/weber/java/robocode/lesson4.html
		 * */
		
		double fireStrength = 200 / opponent.getDistance();
		long time = getTime() + (int)(opponent.getDistance() / ( 20 - (3 * fireStrength)));
		
		Point myPosition = new Point(getX(), getY());
		Point oppPosition = opponent.calculateNewPosition(time);
		double bearingAbsolute = Point.calculateAbsoluteBearing(myPosition, oppPosition);
		double offset = getGunHeadingRadians() - bearingAbsolute;
		
		setTurnGunLeftRadians(calculateBearingNormalised(offset));	
		fire(fireStrength);
	}
	
	// Obliczanie obrotu wzgledem cwiartki w ukladzie wspolrzednych
	private double calculateBearingNormalised(double rotate) {
		
		if(rotate > Math.PI) rotate -= 2*Math.PI;
		else if(rotate < -Math.PI) rotate += 2*Math.PI;
		
		return rotate;
	}
}
