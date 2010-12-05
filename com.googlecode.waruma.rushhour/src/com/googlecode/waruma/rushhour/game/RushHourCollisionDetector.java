package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.Serializable;

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
public class RushHourCollisionDetector implements ICollisionDetector, Serializable {
	private static final long serialVersionUID = -121255920300069932L;

	private class CollisionPath {
		private Point source;
		private Orientation orientation;
		private int distance;
		
		private CollisionPath(IGameBoardObject gameBoardObject){
			this.source = gameBoardObject.getPosition();
			this.orientation = gameBoardObject.getOrientation();
			this.distance = gameBoardObject.getCollisionMap().length -1;

			if (this.orientation == Orientation.NORTH) {
				this.source = new Point(this.source.x, this.source.y + this.distance);
			}

			if (this.orientation == Orientation.WEST) {
				this.source = new Point(this.source.x + this.distance, this.source.y);
			}
		}
		
		private CollisionPath(IGameBoardObject gameBoardObject, int movedistance){
			this(gameBoardObject);
			if(movedistance > 0){
				this.distance = this.distance + movedistance;
			} else {
				this.distance = movedistance;
			}
		}
		
		private void moveByAmmount(int ammount){
			switch (this.orientation) {
			case EAST:
				this.source = new Point(this.source.x + ammount, this.source.y);
				break;
			case NORTH:
				this.source = new Point(this.source.x, this.source.y - ammount);
				break;
			case SOUTH:
				this.source = new Point(this.source.x, this.source.y + ammount);
				break;
			case WEST:
				this.source = new Point(this.source.x - ammount, this.source.y);
				break;
			}
		}
	}
	
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
		collisionMap = new Boolean[width][height];
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				collisionMap[i][j] = true;
			}
		}
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
		if (x < 0 || x >= collisionMap.length)
			return false;
		if (y < 0 || y >= collisionMap[0].length)
			return false;
		return collisionMap[x][y];
	}

	private void printGameBoardToConsole(){
		for (int i = 0; i < collisionMap.length; i++) {
			for (int j = 0; j < collisionMap[i].length; j++) {
				if(collisionMap[j][i])
					System.out.print(0);
				else
					System.out.print(1);
			}
			System.out.println();
		}
		System.out.println();
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
	private boolean checkPathCollisionFree(CollisionPath collisionPath) {
		if(collisionPath.distance >= 0){
			for (int i = 0; i <= collisionPath.distance; i++) {
				Point pointToCheck = getDestinationPoint(collisionPath.source, collisionPath.orientation, i);
				if(!validTile(pointToCheck))
					return false;
			}
		} else {
			for (int i = 0; i >= collisionPath.distance; i--) {
				Point pointToCheck = getDestinationPoint(collisionPath.source, collisionPath.orientation, i);
				if(!validTile(pointToCheck))
					return false;
			}
		}
		return true;
	}

	private void setPathInCollisionMap(CollisionPath collisionPath, boolean value) {
		for (int i = 0; i <= collisionPath.distance; i++) {
			Point point = getDestinationPoint(collisionPath.source, collisionPath.orientation, i);
			collisionMap[point.x][point.y] = value;
		}
	}

	private void clearPathInCollisionMap(CollisionPath collisionPath) {
		setPathInCollisionMap(collisionPath, true);
	}

	private void fillPathInCollisionMap(CollisionPath collisionPath) {
		setPathInCollisionMap(collisionPath, false);
	}



	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException {
		CollisionPath collisionPath = new CollisionPath(gameBoardObject);
		if (checkPathCollisionFree(collisionPath)) {
			fillPathInCollisionMap(collisionPath);
		} else {
			throw new IllegalBoardPositionException();
		}
	}

	@Override
	public void checkMove(IMove move) throws IllegalMoveException {
		if (move.getMoveable() instanceof IGameBoardObject) {
			IGameBoardObject gameBoardObject = (IGameBoardObject) move
					.getMoveable();
			CollisionPath objectBoundries = new CollisionPath(gameBoardObject);
			CollisionPath moveBoundries = new CollisionPath(gameBoardObject, move.getDistance());
			// Objekt von der Karte entfernen
			clearPathInCollisionMap(objectBoundries);
			// Zug Prüfen
			if (!checkPathCollisionFree(moveBoundries)) {
				fillPathInCollisionMap(objectBoundries);
				throw new IllegalMoveException();
			}
			fillPathInCollisionMap(objectBoundries);
			lastCheckedMove = move;
		} else {
			throw new IllegalMoveException();
		}

	}

	@Override
	public void doMove(IMove move) throws IllegalMoveException {
		if(move.equals(lastCheckedMove)){
			IGameBoardObject gameBoardObject = (IGameBoardObject) move.getMoveable();
			CollisionPath objectBoundries = new CollisionPath(gameBoardObject);
			
			clearPathInCollisionMap(objectBoundries);
			objectBoundries.moveByAmmount(move.getDistance());
			fillPathInCollisionMap(objectBoundries);
		} else {
			throw new IllegalMoveException();
		}
	}

	@Override
	public boolean hitPoint(IGameBoardObject gameBoardObject, Point point) {
		CollisionPath collisionPath = new CollisionPath(gameBoardObject);
		
		for (int i = 0; i < collisionPath.distance; i++) {
			Point tempPoint = getDestinationPoint(collisionPath.source, collisionPath.orientation, i);
			if(tempPoint.equals(point))
				return true;
		}
		
		return false;
	}
}