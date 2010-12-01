package com.googlecode.waruma.rushhour.game;

import java.awt.Point;

import com.googlecode.waruma.rushhour.framework.Game;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.Orientation;

public class PlayerCar extends StandardCar implements IPlayer {

	private Point destination;


	public PlayerCar(Boolean[][] collisionMap, Point position,
			Enum<Orientation> orientation) {
		super(collisionMap, position, orientation);
		// TODO Auto-generated constructor stub
	}

	
	public void setDestination(Point destination) {
		this.destination = destination;
	}

	public void move(Integer distance) {
	}

	public void registerReachedDestination (Game eventTarget) {
	}

}