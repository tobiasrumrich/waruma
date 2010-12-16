package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
import java.io.Serializable;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

/**
 * Dummyklasse die {@link AbstractGameBoardObject} und {@link IMoveable} vereint
 * um den Decorator für alle Funktionen nutzbar zu machen
 * 
 * @author Tobias Rumrich
 */
public abstract class AbstractMoveable extends AbstractGameBoardObject
		implements IMoveable, Serializable {
	private static final long serialVersionUID = 3541842951074122478L;

	/**
	 * Erzeugt ein neus AbstractMoveable mit den angegebenen Parametern
	 * 
	 * @param collisionMap
	 * @param position
	 * @param orientation
	 */
	public AbstractMoveable(Boolean[][] collisionMap, Point position,
			Orientation orientation) {
		super(collisionMap, position, orientation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.waruma.rushhour.framework.IMoveable#checkMove(int)
	 */
	@Override
	public abstract void checkMove(int distance) throws IllegalMoveException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.waruma.rushhour.framework.IMoveable#move(int)
	 */
	@Override
	public abstract void move(int distance) throws IllegalMoveException;

}
