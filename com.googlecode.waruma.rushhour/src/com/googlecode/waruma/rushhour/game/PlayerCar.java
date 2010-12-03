package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.GameState;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.IReachedDestinationObserver;
import com.googlecode.waruma.rushhour.framework.Orientation;

public class PlayerCar extends StandardCar implements IPlayer {

	private Point destination;
	private List<IReachedDestinationObserver> observers;


	public PlayerCar(Boolean[][] collisionMap, Point position,
			Orientation orientation) {
		super(collisionMap, position, orientation);
		
		observers = new ArrayList<IReachedDestinationObserver>();
		// TODO Auto-generated constructor stub
	}

	
	public void setDestination(Point destination) {
		this.destination = destination;
	}

	public void move(Integer distance) throws IllegalMoveException {
	}

	public void registerReachedDestination (IReachedDestinationObserver eventTarget) {
	}

}