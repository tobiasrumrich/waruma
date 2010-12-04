package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public class AdditionalFunctionality extends AbstractMoveable {
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

}