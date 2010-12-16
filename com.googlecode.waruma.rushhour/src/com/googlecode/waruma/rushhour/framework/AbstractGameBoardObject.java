package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
import java.io.Serializable;
import java.util.Arrays;

public abstract class AbstractGameBoardObject implements IGameBoardObject,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5260463490389698394L;
	private Boolean[][] collisionMap;
	private Point position;
	private Orientation orientation;


	public AbstractGameBoardObject(Boolean[][] collisionMap, Point position,
			Orientation orientation) {
		this.collisionMap = collisionMap;
		this.position = position;
		this.orientation = orientation;
	}

	@Override
	public Boolean[][] getCollisionMap() {
		return collisionMap;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public Point getPosition() {
		return position;
	}

	public void setCollisionMap(Boolean[][] collisionMap) {
		this.collisionMap = collisionMap;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public void setPosition(Point position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 20533;
		int result = 1;
		result = prime * result + collisionMap.length;
		result = prime * result
				+ ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + ((position == null) ? 0 : position.x);
		result = prime * result + ((position == null) ? 0 : position.y);

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractGameBoardObject other = (AbstractGameBoardObject) obj;
		if (collisionMap.length != other.collisionMap.length)
			return false;
		if (orientation != other.orientation)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (position.x != other.position.x || position.y != other.position.y)
			return false;
		return true;
	}

}