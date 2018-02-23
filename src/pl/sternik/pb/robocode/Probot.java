package pl.sternik.pb.robocode;

import robocode.AdvancedRobot;
import robocode.RobotDeathEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils; 
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Probot extends AdvancedRobot {

	private Opponent opponent;			// przeciwnik
	private double fireStrength = 1;	// Sila pocisku (obliczana na podstawie dystansu do przeciwnika)
	private byte moveDir = 1;			// W którym kierunku sie poruszamy (-1, 1)
	
	@Override
	public void run() {
		
		opponent = new Opponent(Double.MAX_VALUE);
		setColors(	Color.GREEN,
					Color.GREEN,
					Color.BLACK,
					Color.RED,
					Color.WHITE);
		
		// Obracanie radaru o 360 stopni 
		// w celu znalezienia przeciwnika:
		turnRadarRightRadians(2* Math.PI);
		
		// Wlaczanie obrotów radaru i 
		// dziala czolgu w sposób niezale¿ny:
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		//TODO:
		// -> System poruszania
		// -> System skanowania
		// -> System strzelania
		// -> System "uciekania" <- w przypadku trafienia
		do {
			moveManager();	
			execute();
		}while(true);
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		/*
		 * Gdy radar zeskanuje przeciwnika, musimy
		 * zmienic wartosc, jezeli znaleziony
		 * przeciwnik jest najblizej nas
		 * */
		
		if(event.getDistance() < opponent.getDistance())
		{
			opponent.setupOpponent(event);
		}
	}
	
	@Override
	public void onRobotDeath(RobotDeathEvent event) {
		/*
		 * Po zastrzeleniu przeciwnika, musimy
		 * nadac mu max dystans, aby móc ponownie
		 * wyszukac kolejnego przeciwnika
		 * */		
		opponent.setDistance(Double.MAX_VALUE);
	}
	
	private void moveManager() {
		long currentTime = getTime();
		int moduloRange = ThreadLocalRandom.current().nextInt(10, 20);
		
		if(currentTime % moduloRange == 0) {
			moveDir *= -1;
			setAhead((3000 / moduloRange) * moveDir);
		}
		
		// TODO: Obracanie sie do przeciwnika
		// Sprawdzanie poprawnoœci dzialania: //
		setTurnRightRadians((Math.PI / 2) + opponent.getBearing());
	}
	
	private void scanManager() {
		
	}
	
	private void fireManager() {
		
	}
}
