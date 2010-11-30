package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;


public abstract class AbstractGameBoardObject implements IGameBoardObject,
		IMoveable {
	private Boolean[][] collisionMap;
	private Point position;
	private Enum<Orientation> orientation;
	
	// TODO Andere Konstruktoren
	
	public AbstractGameBoardObject(Boolean[][] collisionMap, Point position,
			Enum<Orientation> orientation) {
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

	public Enum<Orientation> getOrientation() {
		return orientation;
	}

	public void setOrientation(Enum<Orientation> orientation) {
		this.orientation = orientation;
	}

	public Boolean[][] getCollisionMap() {
		return collisionMap;
	}

}