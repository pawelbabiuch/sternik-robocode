package pl.sternik.pb.robocode;

import java.awt.Color;

import robocode.*;
import java.awt.Color;

public class Probot extends AdvancedRobot {
	

}

class Enemy {
	/*
	 * ok, we should really be using accessors and mutators here,
	 * (i.e getName() and setName()) but life's too short.
	 */
	String name;
	public double bearing;
	public double head;
	public long ctime; //game time that the scan was produced
	public double speed;
	public double x,y;
	public double distance;
	public double guessX(long when)
	{
		long diff = when - ctime;
		return x+Math.sin(head)*speed*diff;
	}
	public double guessY(long when)
	{
		long diff = when - ctime;
		return y+Math.cos(head)*speed*diff;
	}

}
