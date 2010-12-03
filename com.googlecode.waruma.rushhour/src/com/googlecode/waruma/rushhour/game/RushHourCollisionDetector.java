package com.googlecode.waruma.rushhour.game;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * Diese Klasse implementiert die RushHour-Spezifische Kollisionserkennung,
 * die sich um die Performance des Solvers nicht zu beeinträchtigen nur eine
 * eindimensionale auf Arraylänge Pro-Objekt Kollisionskarte beinhaltet
 * @author Florian
 */
public class RushHourCollisionDetector implements ICollisionDetector {
	private Boolean[][] collisionMap;
	private Boolean[][] tempCollisionMap;

	public RushHourCollisionDetector(int size){
		this(size, size);
	}
	
	public RushHourCollisionDetector(int width, int height) {
		this.collisionMap = new Boolean[width-1][height-1];
	}
	
	private boolean validTile(Point point){
		return validTile(point.x, point.y);
		
	}
	
	private boolean validTile(int x, int y){
		if(x < 0 || x > collisionMap[0].length)
			return false;
		if(y < 0 || y > collisionMap[0].length)
			return false;
		return collisionMap[x][y];
	}
	
	private boolean gameBoardObjectDoesNotCollide(IGameBoardObject gameBoardObject){
		Point position = gameBoardObject.getPosition();
		int size = gameBoardObject.getCollisionMap().length;
		Orientation orientation = gameBoardObject.getOrientation();
		
		for(int i = 0; i < gameBoardObject.getCollisionMap().length; i++){
			
			switch (gameBoardObject.getOrientation()) {
			case Orientation.EAST:
				
				break;

			default:
				break;
			}
		}
		
		return true;
	}
	
	public void addGameBoardObject(IGameBoardObject gameBoardObject) throws IllegalBoardPositionException {
		
	}

	public void move(IMove move) throws IllegalMoveException {
	}
	
	

}