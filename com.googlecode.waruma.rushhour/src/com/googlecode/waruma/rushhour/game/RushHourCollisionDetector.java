package com.googlecode.waruma.rushhour.game;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.Move;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * Diese Klasse implementiert die RushHour-Spezifische Kollisionserkennung, die
 * sich um die Performance des Solvers nicht zu beeinträchtigen nur eine
 * eindimensionale auf Arraylänge Pro-Objekt Kollisionskarte beinhaltet
 * 
 * @author Florian
 */
public class RushHourCollisionDetector implements ICollisionDetector {
	private Boolean[][] collisionMap;
	private IMove lastCheckedMove;

	/**
	 * Erstellt ein RushHour spezifischen CollisionDetector mit einem
	 * quadratischen Spielfeld
	 * 
	 * @param size
	 *            - Kantenlänge
	 */
	public RushHourCollisionDetector(int size) {
		this(size, size);
	}

	/**
	 * Erstellt einen RushHour spezifischen CollisionDetector mit einem
	 * rechteckigen Spielfeld
	 * 
	 * @param width
	 *            - Breite des Spielfeldes
	 * @param height
	 *            - Höhe des Spielfeldes
	 */
	public RushHourCollisionDetector(int width, int height) {
		this.collisionMap = new Boolean[width - 1][height - 1];
	}

	/**
	 * Überprpüft ob ein Punkt auf dem Spielfeld frei und gültig ist und gibt
	 * sofern die Überprüfung positiv war true zurück
	 * 
	 * @param point
	 * @return Ergebnis der Überprügung
	 */
	private boolean validTile(Point point) {
		return validTile(point.x, point.y);

	}

	/**
	 * Überprpüft ob ein Punkt auf dem Spielfeld frei und gültig ist und gibt
	 * sofern die Überprüfung positiv war true zurück
	 * 
	 * @param x
	 * @param y
	 * @return Ergebnis der Überprüfung
	 */
	private boolean validTile(int x, int y) {
		if (x < 0 || x > collisionMap[0].length)
			return false;
		if (y < 0 || y > collisionMap[0].length)
			return false;
		return collisionMap[x][y];
	}

	private Point getDestinationPoint(Point source, Orientation orientation,
			int distance) {
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
	
	/**
	 * Überprüft ob der Pfad ausgehend von der übergebenen Position in der
	 * Übergeben Richtung in einer bestimmten länge kollisionsfrei und gültig
	 * ist
	 */
	private boolean checkPathCollisionFree(Point source,
			Orientation orientation, int distance) {
		for (int i = 0; i < distance; i++) {
			Point pointToCheck = getDestinationPoint(source, orientation, i);
			if(!validTile(pointToCheck))
				return false;
		}
		return true;
	}

	private void setPathInCollisionMap(Point source, Orientation orientation,
			int distance, boolean value) {
		for (int i = 0; i < distance; i++) {
			Point point = getDestinationPoint(source, orientation, i);
			collisionMap[point.x][point.y] = value;
		}
	}

	private void clearPathInCollisionMap(Point source, Orientation orientation,
			int distance) {
		setPathInCollisionMap(source, orientation, distance, true);
	}

	private void fillPathInCollisionMap(Point source, Orientation orientation,
			int distance) {
		setPathInCollisionMap(source, orientation, distance, false);
	}

	/**
	 * Wandelt die im Spiel verwendete Ausgangspunkt-Definition in die vom
	 * CollisionDetector verwendete (immer "hinten") um
	 * 
	 * @param gameBoardObject
	 * @return Punkt
	 */
	private Point transposeGameBoardObjectSourcePoint(
			IGameBoardObject gameBoardObject) {

		Orientation orientation = gameBoardObject.getOrientation();
		Point sourcePoint = gameBoardObject.getPosition();
		int distance = gameBoardObject.getCollisionMap().length - 1;

		if (orientation == Orientation.NORTH) {
			return new Point(sourcePoint.x, sourcePoint.y + distance);
		}

		if (orientation == Orientation.WEST) {
			return new Point(sourcePoint.x + distance, sourcePoint.y);
		}

		return sourcePoint;
	}

	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException {
		Point source = transposeGameBoardObjectSourcePoint(gameBoardObject);
		Orientation orientation = gameBoardObject.getOrientation();
		int length = gameBoardObject.getCollisionMap().length;

		if (checkPathCollisionFree(source, orientation, length)) {
			fillPathInCollisionMap(source, orientation, length);
		} else {
			throw new IllegalBoardPositionException();
		}
	}

	@Override
	public void checkMove(IMove move) throws IllegalMoveException {
		if (move.getMoveable() instanceof IGameBoardObject) {
			IGameBoardObject gameBoardObject = (IGameBoardObject) move
					.getMoveable();
			Point source = transposeGameBoardObjectSourcePoint(gameBoardObject);
			Orientation orientation = gameBoardObject.getOrientation();
			int length = gameBoardObject.getCollisionMap().length;
			// Objekt von der Karte entfernen
			clearPathInCollisionMap(source, orientation, length);
			// Zug prüfen
			if (!checkPathCollisionFree(source, orientation,
					length + move.getDistance())) {
				fillPathInCollisionMap(source, orientation, length);
				throw new IllegalMoveException();
			}
			fillPathInCollisionMap(source, orientation, length);
			lastCheckedMove = move;
		} else {
			throw new IllegalMoveException();
		}

	}

	@Override
	public void doMove(IMove move) throws IllegalMoveException {
		if(move.equals(lastCheckedMove)){
			IGameBoardObject gameBoardObject = (IGameBoardObject) move.getMoveable();
			Point source = transposeGameBoardObjectSourcePoint(gameBoardObject);
			Orientation orientation = gameBoardObject.getOrientation();
			int length = gameBoardObject.getCollisionMap().length;
			clearPathInCollisionMap(source, orientation, length);
			fillPathInCollisionMap(source, orientation, length);
		} else {
			throw new IllegalMoveException();
		}
	}

	@Override
	public boolean hitPoint(IGameBoardObject gameBoardObject, Point point) {
		Point source = transposeGameBoardObjectSourcePoint(gameBoardObject);
		int length = gameBoardObject.getCollisionMap().length;
		Orientation orientation = gameBoardObject.getOrientation();
		
		for (int i = 0; i < length; i++) {
			Point tempPoint = getDestinationPoint(source, orientation, i);
			if(tempPoint.equals(point))
				return true;
		}
		
		return false;
	}
}