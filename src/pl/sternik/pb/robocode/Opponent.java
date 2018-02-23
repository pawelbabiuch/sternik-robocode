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

	private double distance;	// Odleglosc od robota
	private double bearing;		// kierunek zwrotu
	
	public Opponent(double distance) {
		this.distance = distance;
	}
	
	public void setupOpponent(ScannedRobotEvent opp) {
		this.distance = opp.getDistance();
		this.bearing = opp.getBearing();
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
}
