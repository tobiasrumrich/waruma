package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public abstract class AbstractMoveable extends AbstractGameBoardObject implements IMoveable {

	public AbstractMoveable(Boolean[][] collisionMap, Point position,
			Orientation orientation) {
		super(collisionMap, position, orientation);
	}

	public abstract void move(int distance) throws IllegalMoveException;
	
}


