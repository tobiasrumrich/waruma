package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
import java.io.Serializable;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public class AdditionalFunctionality extends AbstractMoveable implements
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2028991516326549760L;
	public AbstractMoveable abstractMoveable;

	public AdditionalFunctionality(AbstractMoveable abstractMoveable) {
		super(new Boolean[0][0], new Point(0, 0), Orientation.NORTH);
		this.abstractMoveable = abstractMoveable;
	}
	
	@Override
	public Boolean[][] getCollisionMap() {
		return abstractMoveable.getCollisionMap();
	}

	@Override
	public Orientation getOrientation() {
		return abstractMoveable.getOrientation();
	}

	@Override
	public Point getPosition() {
		return abstractMoveable.getPosition();
	}

	@Override
	public void move(int distance) throws IllegalMoveException {
		abstractMoveable.move(distance);
	}
	
	@Override
	public void checkMove(int distance) throws IllegalMoveException {
		abstractMoveable.checkMove(distance);
	}

	@Override
	public void setCollisionMap(Boolean[][] collisionMap) {
		abstractMoveable.setCollisionMap(collisionMap);
	}

	@Override
	public void setOrientation(Orientation orientation) {
		abstractMoveable.setOrientation(orientation);
	}

	@Override
	public void setPosition(Point position) {
		abstractMoveable.setPosition(position);
	}

	@Override
	public int hashCode() {
		final int prime = 33889;
		int result = super.hashCode();
		result = prime
				* result
				+ ((abstractMoveable == null) ? 0 : abstractMoveable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdditionalFunctionality other = (AdditionalFunctionality) obj;
		if (abstractMoveable == null) {
			if (other.abstractMoveable != null)
				return false;
		} else if (!abstractMoveable.equals(other.abstractMoveable))
			return false;
		return true;
	}
}