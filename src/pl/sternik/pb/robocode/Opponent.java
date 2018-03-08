package pl.sternik.pb.robocode;

import robocode.ScannedRobotEvent;

/*
 * Potrzebujemy klasy, która bedzie
 * przechowywac najwazniejsze informacje
 * o przeciwniku, tak, abysmy mogli je
 * wykorzystac w systemach poruszania,
 * skanowania oraz strzelania.
 * */
public final class Opponent {

	private String robotName;

	private double robotSpeed;	// predkosc poruszania sie
	private double bearing;		// kierunek zwrotu  bearing(0-360)
	private double head;		// kierunek zwrotu heading(0-360)
	
	private long lastScanTime;	// Kiedy robiony byl skan
	private Point position;		// pozycja przeciwnika
	private double distance;	// Odleglosc od robota

	public Opponent(double distance) {
		this.distance = distance;
	}
		
	public void setupOpponent(ScannedRobotEvent opp, Point position) {
		
		this.robotName = opp.getName();
		
		this.robotSpeed = opp.getVelocity();
		this.bearing = opp.getBearingRadians();
		this.head = opp.getHeadingRadians();
		
		this.lastScanTime = opp.getTime();
		this.distance = opp.getDistance();
		this.position = position;
	}

	public String getRobotName() {
		return robotName;
	}
	
	public long getLastScanTime() {
		return lastScanTime;
	}
	
	public Point getPosition() {
		return position;
	}

	public double getBearing() {
		return bearing;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	// Obliczanie nowej (przypuszczanej) pozycji przeciwnika
	public Point calculateNewPosition(long newTime) {
		
		long calculateTime = Math.abs(newTime - lastScanTime);
		double newX = position.getX() + Math.sin(head) * robotSpeed * calculateTime;
		double newY = position.getY() + Math.cos(head) * robotSpeed * calculateTime;
		
		return new Point(newX, newY);
	}
}
