package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * Diese Klasse implementiert den zur Kollisionserkennung verwendeten Vektor.
 * 
 * @author Florian
 */
public class CollisionVector {
	private Point source;
	private Orientation orientation;
	private int distance;

	public Point getSource() {
		return source;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance){
		this.distance = distance;
	}

	/**
	 * Erzeugt einen neuen Kollisionsvektor aus einem Spielbrettobjekt
	 * 
	 * @param gameBoardObject
	 * @throws IllegalMoveException
	 */
	public CollisionVector(IGameBoardObject gameBoardObject)
			throws IllegalMoveException {
		if (gameBoardObject == null)
			throw new IllegalMoveException();

		this.source = new Point(gameBoardObject.getPosition().x,
				gameBoardObject.getPosition().y);
		this.orientation = gameBoardObject.getOrientation();
		this.distance = gameBoardObject.getCollisionMap().length - 1;
		// Vektorursprung auf hinterstes Feld des GameBoardObjects setzen
		if (this.orientation == Orientation.NORTH) {
			this.source = new Point(this.source.x, this.source.y
					+ this.distance);
		}
		// Vektorursprung auf hinterstes Feld des GameBoardObjects setzen
		if (this.orientation == Orientation.WEST) {
			this.source = new Point(this.source.x + this.distance,
					this.source.y);
		}
	}

	/**
	 * Erzeugt einen neuen Kollisionsvektor für eine Zugdistanz aus einem
	 * Spielbrettobjekt.
	 * 
	 * @param gameBoardObject
	 * @param movedistance
	 * @throws IllegalMoveException
	 */
	public CollisionVector(IGameBoardObject gameBoardObject, int movedistance)
			throws IllegalMoveException {
		this(gameBoardObject);
		if (movedistance > 0) {
			this.distance = this.distance + movedistance;
		} else {
			this.distance = movedistance;
		}
	}
	
	/**
	 * Erzeugt einen neuen Kollisionsvektor für eine Zugdistanz aus einem
	 * Spielbrettobjekt.
	 * 
	 * @param gameBoardObject
	 * @param movedistance
	 * @throws IllegalMoveException
	 */
	public CollisionVector(IGameBoardObject gameBoardObject, Orientation orientation)
			throws IllegalMoveException {
		if (gameBoardObject == null)
			throw new IllegalMoveException();

		this.source = new Point(gameBoardObject.getPosition().x,
				gameBoardObject.getPosition().y);
		this.orientation = orientation;
		this.distance = gameBoardObject.getCollisionMap().length - 1;
		// Vektorursprung auf hinterstes Feld des GameBoardObjects setzen
		if (this.orientation == Orientation.NORTH) {
			this.source = new Point(this.source.x, this.source.y
					+ this.distance);
		}
		// Vektorursprung auf hinterstes Feld des GameBoardObjects setzen
		if (this.orientation == Orientation.WEST) {
			this.source = new Point(this.source.x + this.distance,
					this.source.y);
		}
	
	}
	
	

	/**
	 * Setzt den Vektorursprung um die übergebene Distanz um
	 * 
	 * @param ammount
	 */
	public void moveBy(int ammount) {
		this.source = getPointAt(ammount);
	}

	/**
	 * Gibt eine Liste aller Punkte auf dem Vektor zurück
	 * 
	 * @return Liste der Punkte
	 */
	public List<Point> getPoints() {
		List<Point> list = new ArrayList<Point>();

		if (distance >= 0) {
			for (int currentDistance = 0; currentDistance <= distance; currentDistance++) {
				list.add(getPointAt(currentDistance));
			}
		} else {
			for (int currentDistance = 0; currentDistance >= distance; currentDistance--) {
				list.add(getPointAt(currentDistance));
			}
		}

		return list;
	}

	/**
	 * Setzt den Startpunkt des Vektors auf den definierten Punkt, dabei wird
	 * bei nördlicher und westlicher Orientierung der Punkt entsprechend
	 * transformiert
	 * 
	 * @param point
	 */
	public void setAbsolutePosition(Point point) {
		// Vektorursprung auf hinterstes Feld des GameBoardObjects setzen
		if (this.orientation == Orientation.NORTH) {
			point = new Point(point.x, point.y + this.distance);
		}
		// Vektorursprung auf hinterstes Feld des GameBoardObjects setzen
		if (this.orientation == Orientation.WEST) {
			point = new Point(point.x + this.distance, point.y);
		}
		this.source = point;
	}

	/**
	 * Bestimmt den Punkt in der übergebenen Entfernung
	 * 
	 * @param distance
	 * @return Point
	 */
	private Point getPointAt(int distance) {
		switch (orientation) {
		case EAST:
			return new Point(source.x + distance, source.y);
		case NORTH:
			return new Point(source.x, source.y - distance);
		case SOUTH:
			return new Point(source.x, source.y + distance);
		case WEST:
			return new Point(source.x - distance, source.y);
		}
		return null;
	}

}
