package com.googlecode.waruma.rushhour.framework;

import java.io.Serializable;

/**
 * Implementiert IMove
 * 
 * @author mail2fabi - Fabian Malinowski
 */
public class Move implements IMove, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2627372793288189045L;
	private IMoveable moveable;
	private int distance;

	/**
	 * Erstellt einen neues Move Objekt mit den übergebenen Eigenschaften
	 * 
	 * @param moveable
	 * @param distance
	 */
	public Move(IMoveable moveable, Integer distance) {
		if (moveable != null) {
			this.moveable = moveable;
		} else {
			throw new IllegalArgumentException("Moveable is null");
		}

		if ((distance != null) && (distance != 0)) {
			this.distance = distance;
		} else {
			throw new IllegalArgumentException("Distance is null or 0");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.googlecode.waruma.rushhour.framework.IMove#getDistance()
	 */
	@Override
	public int getDistance() {
		return distance;
	}

	/*
	 * (non-Javadoc)
	 * @see com.googlecode.waruma.rushhour.framework.IMove#getMoveable()
	 */
	@Override
	public IMoveable getMoveable() {
		return moveable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.googlecode.waruma.rushhour.framework.IMove#revertDirection()
	 */
	@Override
	public void revertDirection() {
		distance *= -1;
	}

}