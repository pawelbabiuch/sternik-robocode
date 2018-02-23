package pl.sternik.pb.robocode;

import robocode.ScannedRobotEvent;

/*
 * Potrzebujemy klasy, kt�ra b�dzie
 * przechowywa� najwa�niejsze informacje
 * o przeciwniku, tak, aby�my mogli je
 * wykorzysta� w systemach poruszania,
 * skanowania oraz strzelania.
 * */
public final class Opponent {

	private double distance;	// Odleg�o�� od robota
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
