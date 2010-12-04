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
	private Boolean[][] tempCollisionMap;

	public RushHourCollisionDetector(int size) {
		this(size, size);
	}

	public RushHourCollisionDetector(int width, int height) {
		this.collisionMap = new Boolean[width - 1][height - 1];
	}

	private boolean validTile(Point point) {
		return validTile(point.x, point.y);

	}

	private boolean validTile(int x, int y) {
		if (x < 0 || x > collisionMap[0].length)
			return false;
		if (y < 0 || y > collisionMap[0].length)
			return false;
		return collisionMap[x][y];
	}

	private boolean checkPathCollisionFree(Point position,
			Orientation orientation, int distance) {
		for (int i = 0; i < distance; i++) {
			switch (orientation) {
			case EAST:
				if (!validTile(position.x + i, position.y))
					return false;
				break;
			case NORTH:
				if (!validTile(position.x, position.y - i))
					return false;
				break;
			case SOUTH:
				if (!validTile(position.x, position.y + i))
					return false;
				break;
			case WEST:
				if (!validTile(position.x - i, position.y))
					return false;
				break;
			}
		}
		return true;
	}

	private Point transposeGameBoardObjectSourcePoint(
			IGameBoardObject gameBoardObject) {
		Orientation orientation = gameBoardObject.getOrientation();
		Point sourcePoint = gameBoardObject.getPosition();
		int distance = gameBoardObject.getCollisionMap().length - 1;

		if (orientation == Orientation.NORTH) {
			return new Point(sourcePoint.x, sourcePoint.y + distance);
		}
		if (orientation == Orientation.WEST){
			return new Point(sourcePoint.x + distance, sourcePoint.y);
		}
		
		return sourcePoint;
	}

	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException {
		Point position = transposeGameBoardObjectSourcePoint(gameBoardObject);
		if(checkPathCollisionFree(position, gameBoardObject.getOrientation(), gameBoardObject.getCollisionMap().length)){
			
		}
	}

	public void move(IMove move) throws IllegalMoveException {

	}

}