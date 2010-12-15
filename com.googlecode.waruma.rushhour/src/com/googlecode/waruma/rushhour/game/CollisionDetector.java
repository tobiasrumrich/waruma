package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.graphics.Rectangle;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * Diese Klasse implementiert die RushHour spezifische Kollisionserkennung. *
 * 
 * @author Florian
 */
public class CollisionDetector implements ICollisionDetector, Serializable {
	private static final long serialVersionUID = -121255920300069932L;
	private boolean[][] collisionMap;
	private IMove lastCheckedMove;

	/**
	 * Erstellt ein RushHour spezifischen CollisionDetector mit einem
	 * quadratischen Spielfeld
	 * 
	 * @param size
	 *            Kantenlänge
	 */
	public CollisionDetector(int size) {
		this(size, size);
	}

	/**
	 * Erstellt einen RushHour spezifischen CollisionDetector mit einem
	 * rechteckigen Spielfeld
	 * 
	 * @param width
	 *            Breite des Spielfeldes
	 * @param height
	 *            Höhe des Spielfeldes
	 */
	public CollisionDetector(int width, int height) {
		collisionMap = new boolean[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				collisionMap[i][j] = true;
			}
		}
	}

	/**
	 * Überprpüft ob ein Punkt auf dem Spielbrett frei und gültig ist und gibt
	 * sofern die Überprüfung positiv war true zurück
	 * 
	 * @param point
	 * @return True bei freiem Spielfeld
	 */
	public boolean validTile(Point point) {
		if (point != null) {
			if (point.x < 0 || point.x >= collisionMap.length)
				return false;
			if (point.y < 0 || point.y >= collisionMap[point.x].length)
				return false;
			return collisionMap[point.x][point.y];
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Überprüft ob der Pfad Kollisionen beinhaltet
	 * 
	 * @param collisionPath
	 * @return True bei Kollision
	 */
	private boolean checkCollision(CollisionVector collisionPath) {
		List<Point> pathPoints = collisionPath.getPoints();

		for (Point point : pathPoints) {
			if (!validTile(point)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Setzt alle Felder des CollisionPath auf den übergebenen Wert
	 * 
	 * @param collisionPath
	 * @param value
	 *            True für Belegtes Feld
	 */
	private void setCollisionMap(CollisionVector collisionPath, boolean value) {
		List<Point> pathPoints = collisionPath.getPoints();

		for (Point point : pathPoints) {
			if (point.x >= 0 && point.x < collisionMap.length && point.y >= 0
					&& point.y < collisionMap[point.x].length) {
				collisionMap[point.x][point.y] = value;
			}
		}
	}

	/**
	 * Setzt alle Felder des CollisionPath auf Frei
	 * 
	 * @param collisionPath
	 */
	private void clearCollisionMap(CollisionVector collisionPath) {
		setCollisionMap(collisionPath, true);
	}

	/**
	 * Setzt alle Felder des CollisionPath auf belegt
	 * 
	 * @param collisionPath
	 */
	private void fillCollisionMap(CollisionVector collisionPath) {
		setCollisionMap(collisionPath, false);
	}

	public Rectangle getMoveRange(IGameBoardObject gameBoardObject) {
		try {
			CollisionVector objectBoundries = new CollisionVector(
					gameBoardObject);
			clearCollisionMap(objectBoundries);

			boolean validMove = true;
			while (validMove) {
				objectBoundries.moveBy(-1);
				validMove = !checkCollision(objectBoundries);
			}
			objectBoundries.moveBy(1);

			int maxDistance = objectBoundries.getDistance();
			validMove = true;
			while (validMove) {
				maxDistance++;
				objectBoundries.setDistance(maxDistance);
				validMove = !checkCollision(objectBoundries);
			}
			objectBoundries.setDistance(maxDistance - 1);

			fillCollisionMap(new CollisionVector(gameBoardObject));

			return new Rectangle(objectBoundries.getSource().x,
					objectBoundries.getSource().y,
					objectBoundries.getDistance(), 1);
		} catch (IllegalMoveException e) {
			return null;
		}
	}

	/**
	 * Versucht das übergebene GameBoardObject in der CollisionMap hinzuzufügen.
	 * 
	 * @param gameBoardObject
	 * @throws IllegalBoardPositionException
	 *             Bei einer ungültigen Position auf dem Spielbrett
	 */
	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException {
		CollisionVector collisionPath;
		try {
			collisionPath = new CollisionVector(gameBoardObject);
			if (!checkCollision(collisionPath)) {
				fillCollisionMap(collisionPath);
			} else {
				throw new IllegalBoardPositionException();
			}
		} catch (IllegalMoveException e) {
			throw new IllegalBoardPositionException();
		}
	}

	public void removeGameBoardObject(IGameBoardObject gameBoardObject) {
		CollisionVector objectBoundries;
		try {
			objectBoundries = new CollisionVector(gameBoardObject);
			clearCollisionMap(objectBoundries);
		} catch (IllegalMoveException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void rotateGameBoardObject(IGameBoardObject gameBoardObject,
			Orientation orientation) throws IllegalBoardPositionException {
		try {
			CollisionVector oldObjectBoundries = new CollisionVector(
					gameBoardObject);
			CollisionVector objectBoundries = new CollisionVector(
					gameBoardObject, orientation);

			clearCollisionMap(oldObjectBoundries);
			// Keine Kollision
			if (!checkCollision(objectBoundries)) {
				fillCollisionMap(objectBoundries);
			} else {
				fillCollisionMap(oldObjectBoundries);
				throw new IllegalBoardPositionException();
			}
		} catch (IllegalMoveException e) {
			// TODO Auto-generated catch block
			throw new IllegalBoardPositionException();
		}
	}

	/**
	 * Versucht das Objekt auf die angegebene Position zu bewegen
	 * 
	 * @param gameBoardObject
	 * @param position
	 * @throws IllegalBoardPositionException
	 */
	public void moveGameBoardObjectToPosition(IGameBoardObject gameBoardObject,
			Point position) throws IllegalBoardPositionException {

		try {
			CollisionVector oldObjectBoundries = new CollisionVector(
					gameBoardObject);
			CollisionVector objectBoundries = new CollisionVector(
					gameBoardObject);
			objectBoundries.setAbsolutePosition(position);
			clearCollisionMap(oldObjectBoundries);
			// Keine Kollision
			if (!checkCollision(objectBoundries)) {
				fillCollisionMap(objectBoundries);
			} else {
				fillCollisionMap(oldObjectBoundries);
				throw new IllegalBoardPositionException();
			}
		} catch (IllegalMoveException e) {
			throw new IllegalBoardPositionException();
		}

	}

	/**
	 * Überprüft den übergebenen Zug auf Gültigkeit
	 * 
	 * @param move
	 * @throws IllegalMoveException
	 *             Bei einem ungültigen Zug
	 */
	public void checkMove(IMove move) throws IllegalMoveException {
		if (move.getMoveable() instanceof IGameBoardObject) {
			IGameBoardObject gameBoardObject = (IGameBoardObject) move
					.getMoveable();
			CollisionVector objectBoundries = new CollisionVector(
					gameBoardObject);
			CollisionVector moveBoundries = new CollisionVector(
					gameBoardObject, move.getDistance());
			// Objekt von der Karte entfernen
			clearCollisionMap(objectBoundries);
			// Zug Prüfen
			if (checkCollision(moveBoundries)) {
				fillCollisionMap(objectBoundries);
				throw new IllegalMoveException();
			}
			fillCollisionMap(objectBoundries);
			lastCheckedMove = move;
		} else {
			throw new IllegalMoveException();
		}

	}

	/**
	 * Führt den übergebenen Zug aus, sofern es der zuletzt übergebene ist
	 * 
	 * @param move
	 * @throws IllegalMoveException
	 *             Wenn der Zug nicht der zuvor überprüfte ist
	 */
	public void doMove(IMove move) throws IllegalMoveException {
		if (move.equals(lastCheckedMove)) {
			CollisionVector objectBoundries = new CollisionVector(
					(IGameBoardObject) move.getMoveable());
			clearCollisionMap(objectBoundries);
			objectBoundries.moveBy(move.getDistance());
			fillCollisionMap(objectBoundries);
		} else {
			throw new IllegalMoveException();
		}
	}

	/**
	 * Überprüft ob das übergebene Objekt den spezifizierten Punkt berührt
	 * 
	 * @param gameBoardObject
	 * @param point
	 * @return True bei Berührung
	 */
	public boolean hitPoint(IGameBoardObject gameBoardObject, Point point) {
		try {
			CollisionVector collisionPath = new CollisionVector(gameBoardObject);
			List<Point> pathPoints = collisionPath.getPoints();

			for (Point pathPoint : pathPoints) {
				if (pathPoint.equals(point)) {
					return true;
				}
			}

			return false;
		} catch (IllegalMoveException e) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(collisionMap);
		result = prime * result
				+ ((lastCheckedMove == null) ? 0 : lastCheckedMove.hashCode());
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
		CollisionDetector other = (CollisionDetector) obj;
		if (!Arrays.deepEquals(collisionMap, other.collisionMap))
			return false;
		if (lastCheckedMove == null) {
			if (other.lastCheckedMove != null)
				return false;
		} else if (!lastCheckedMove.equals(other.lastCheckedMove))
			return false;
		return true;
	}

}