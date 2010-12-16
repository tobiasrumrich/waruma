package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

/**
 * Stellt eine allgemeine Schnittstelle für Objekte auf dem Spielbrett dar
 * 
 * @author Fabian Malinowski
 */
public interface IGameBoardObject {

	/**
	 * Gibt die Kollisionsmap eines IGameBoardObjects zurück
	 * 
	 * @return Boolean-Array der CollisionMap
	 */
	public Boolean[][] getCollisionMap();

	/**
	 * Gibt die Orientierung eines IGameBoardObjects zurück
	 * 
	 * @return Enum<Orientation>
	 */
	public Orientation getOrientation();

	/**
	 * Gibt die Position eines IGameBoardObjects (obere, linke Ecke)
	 * 
	 * @return Gibt die Position als Point zurück
	 */
	public Point getPosition();

	/**
	 * Setzt die Orientierung eines IGameBoardObjects
	 * 
	 * @param orientation
	 *            Neue Orientierung
	 */
	public void setOrientation(Orientation orientation);

	/**
	 * Setzt die Position eines IGameBoardObject (obere, linke Ecke)
	 * 
	 * @param position
	 *            Neue Position als Point
	 */
	public void setPosition(Point position);

}