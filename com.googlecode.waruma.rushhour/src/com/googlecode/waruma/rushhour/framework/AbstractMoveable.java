package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public abstract class AbstractMoveable extends AbstractGameBoardObject implements IMoveable {

	public AbstractMoveable(Boolean[][] collisionMap, Point position,
			Enum<Orientation> orientation) {
		super(collisionMap, position, orientation);
		// TODO Auto-generated constructor stub
	}

	public abstract void move(int distance) throws IllegalMoveException;

	
	
	
}


