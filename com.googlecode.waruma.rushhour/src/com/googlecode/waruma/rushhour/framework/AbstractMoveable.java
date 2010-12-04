package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
import java.io.Serializable;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public abstract class AbstractMoveable extends AbstractGameBoardObject
		implements IMoveable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3541842951074122478L;

	public AbstractMoveable(Boolean[][] collisionMap, Point position,
			Orientation orientation) {
		super(collisionMap, position, orientation);
	}

	@Override
	public abstract void move(int distance) throws IllegalMoveException;

	
}
