package com.googlecode.waruma.rushhour.framework;

/**
 * Blablablabla
 * 
 * @author mail2fabi - Fabian Malinowski
 */
public class Move implements IMove {
	private IMoveable moveable;
	private int distance;

	public Move(IMoveable moveable, Integer distance) {
		if (moveable != null)
			this.moveable = moveable;
		else
			throw new IllegalArgumentException("Moveable is null");

		if (distance != null && distance != 0)
			this.distance = distance;
		else
			throw new IllegalArgumentException("Distance is null or 0");
	}

	@Override
	public int getDistance() {
		return distance;
	}

	@Override
	public IMoveable getMoveable() {
		return moveable;
	}

}