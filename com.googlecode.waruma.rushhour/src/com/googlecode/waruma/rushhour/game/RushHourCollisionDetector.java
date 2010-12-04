package com.googlecode.waruma.rushhour.game;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
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

	@Override
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

		lastCheckedMove = move;
	}

	/**
	 * Überprüft ob der Pfad ausgehend von der übergebenen Position in der
	 * Übergeben Richtung in einer bestimmten länge kollisionsfrei und gültig
	 * ist
	 */
	private boolean checkPathCollisionFree(Point source,
			Orientation orientation, int distance) {
		for (int i = 0; i < distance; i++) {
			switch (orientation) {
			case EAST:
				if (!validTile(source.x + i, source.y))
					return false;
				break;
			case NORTH:
				if (!validTile(source.x, source.y - i))
					return false;
				break;
			case SOUTH:
				if (!validTile(source.x, source.y + i))
					return false;
				break;
			case WEST:
				if (!validTile(source.x - i, source.y))
					return false;
				break;
			}
		}
		return true;
	}

	private void clearPathInCollisionMap(Point source, Orientation orientation,
			int distance) {
		setPathInCollisionMap(source, orientation, distance, true);
	}

	@Override
	public void doMove(IMove move) throws IllegalMoveException {
		// TODO Auto-generated method stub

	}

	private void fillPathInCollisionMap(Point source, Orientation orientation,
			int distance) {
		setPathInCollisionMap(source, orientation, distance, false);
	}

	private void setPathInCollisionMap(Point source, Orientation orientation,
			int distance, boolean value) {
		for (int i = 0; i < distance; i++) {
			switch (orientation) {
			case EAST:
				collisionMap[source.x + i][source.y] = value;
				break;
			case NORTH:
				collisionMap[source.x][source.y - i] = value;
				break;
			case SOUTH:
				collisionMap[source.x][source.y + i] = value;
				break;
			case WEST:
				collisionMap[source.x - i][source.y] = value;
				break;
			}
		}
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

}