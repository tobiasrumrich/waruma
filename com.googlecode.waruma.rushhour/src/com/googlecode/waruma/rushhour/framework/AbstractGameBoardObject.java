package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;


public abstract class AbstractGameBoardObject implements IGameBoardObject {
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

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public Boolean[][] getCollisionMap() {
		return collisionMap;
	}
	
	public void setCollisionMap(Boolean[][] collisionMap) {
		this.collisionMap = collisionMap;
	}

}