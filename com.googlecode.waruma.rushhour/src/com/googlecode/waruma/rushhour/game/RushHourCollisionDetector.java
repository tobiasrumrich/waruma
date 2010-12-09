package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.Move;
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
			this.source = (Point) gameBoardObject.getPosition().clone();
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
	 * Erstellt einen RushHour spezifischen CollisionDetector auf der
	 * Basis einer vorgegebenen CollisionMap
	 * @param collisionMap
	 */
	public RushHourCollisionDetector(Boolean[][] collisionMap){
		this.collisionMap = collisionMap;
	}
	
	public Boolean[][] getCollisionMap(){
		return collisionMap;
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

	public Point getDestinationPoint(Point source, Orientation orientation,
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
	
	public List<IMove> getValidMoves(IGameBoardObject gameBoardObject){
		List<IMove> moveList = new ArrayList<IMove>();
		
		if(gameBoardObject instanceof IMoveable){
			CollisionPath objectBoundries = new CollisionPath(gameBoardObject);
			clearPathInCollisionMap(objectBoundries);
			
			// Positive Züge prüfen
			boolean moveExists = true;
			int currentMoveDistance = 0;
			while(moveExists){
				currentMoveDistance++;
				objectBoundries.moveByAmmount(1);
				if(checkPathCollisionFree(objectBoundries)){
					moveList.add(new Move((IMoveable)gameBoardObject, currentMoveDistance));
				} else {
					moveExists = false;
				}
			}	
			objectBoundries.moveByAmmount(- currentMoveDistance);
						
			// Negative Züge prüfen
			moveExists = true;
			currentMoveDistance = 0;
			while(moveExists){
				currentMoveDistance--;
				objectBoundries.moveByAmmount(-1);
				if(checkPathCollisionFree(objectBoundries)){
					moveList.add(new Move((IMoveable)gameBoardObject, currentMoveDistance));
				} else {
					moveExists = false;
				}
			}	
			objectBoundries.moveByAmmount(- currentMoveDistance);
			
			fillPathInCollisionMap(objectBoundries);
		}
		
		return moveList;
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
			doMoveWithoutCheck(move);
		} else {
			throw new IllegalMoveException();
		}
	}
	
	@Override
	public void doMoveWithoutCheck(IMove move){
		IGameBoardObject gameBoardObject = (IGameBoardObject) move.getMoveable();
		CollisionPath objectBoundries = new CollisionPath(gameBoardObject);
		
		clearPathInCollisionMap(objectBoundries);
		objectBoundries.moveByAmmount(move.getDistance());
		fillPathInCollisionMap(objectBoundries);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(collisionMap);
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
		RushHourCollisionDetector other = (RushHourCollisionDetector) obj;
		if (!Arrays.equals(collisionMap, other.collisionMap))
			return false;
		if (lastCheckedMove == null) {
			if (other.lastCheckedMove != null)
				return false;
		} else if (!lastCheckedMove.equals(other.lastCheckedMove))
			return false;
		return true;
	}
	
	
}