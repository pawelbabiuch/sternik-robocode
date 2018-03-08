package pl.sternik.pb.robocode;

public class Point {

	private double x;
	private double y;
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Obrot bezwzgledny pomiedzy dwoma punktami (pozycjami)
	 * Zrodlo: http://mark.random-article.com/weber/java/robocode/lesson4.html
	 * */
	public static double calculateAbsoluteBearing(Point A, Point B) {
		
		double absoluteBearing = 0;
		
		double xo = B.x - A.x;
		double yo = B.y - A.y;
		double distance = getDistance(xo, yo);
		
		if ( xo > 0 && yo > 0 ) {
			absoluteBearing =  Math.asin( xo / distance );
		}
		else if ( xo > 0 && yo < 0 ) {
			absoluteBearing = Math.PI - Math.asin( xo / distance );
		}
		else if ( xo < 0 && yo < 0 ) {
			absoluteBearing = Math.PI + Math.asin( -xo / distance );
		}
		else if ( xo < 0 && yo > 0 ) {
			absoluteBearing = 2.0*Math.PI - Math.asin( -xo / distance );
		}
		
		return absoluteBearing;
	}
	
	private static double getDistance(double xo, double yo) {
		xo *= xo;
		yo *= yo;
		return Math.sqrt(xo + yo);
	}
}
