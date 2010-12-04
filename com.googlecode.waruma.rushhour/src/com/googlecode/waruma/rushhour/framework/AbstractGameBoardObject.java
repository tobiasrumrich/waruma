package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
import java.io.Serializable;

public abstract class AbstractGameBoardObject implements IGameBoardObject,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5260463490389698394L;
	private Boolean[][] collisionMap;
	private Point position;
	private Orientation orientation;

	// TODO Andere Konstruktoren

	public AbstractGameBoardObject(Boolean[][] collisionMap, Point position,
			Orientation orientation) {
		// TODO Prüfungen
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

}