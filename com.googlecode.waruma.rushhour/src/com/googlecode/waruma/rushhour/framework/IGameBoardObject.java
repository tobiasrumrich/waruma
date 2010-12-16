package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

public interface IGameBoardObject {

	/**
	 * gibt den Kollisionsmap eines IGameBoardObjects zurück
	 * 
	 * @return Boolean-Array der CollisionMap
	 */
	public Boolean[][] getCollisionMap();

	/**
	 * gibt die Orientierung eines IGameBoardObjects zurück
	 * 
	 * @return Enum<Orientation>
	 */
	public Orientation getOrientation();

	/**
	 * setzt die Orientierung eines IGameBoardObjects
	 * 
	 * @param orientation
	 *            neue Orientierung
	 */
	public void setOrientation(Orientation orientation);

	/**
	 * gibt die Position eines IGameBoardObjects (obere, linke Ecke)
	 * 
	 * @return gibt die Position als Point zurück
	 */
	public Point getPosition();

	/**
	 * setzt die Position eines IGameBoardObject (obere, linke Ecke)
	 * 
	 * @param position neue Position als Point
	 */
	public void setPosition(Point position);

}