package com.googlecode.waruma.rushhour.game;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;

/**
 * Diese Klasse implementiert die RushHour-Spezifische Kollisionserkennung,
 * die sich um die Performance des Solvers nicht zu beeinträchtigen nur eine
 * eindimensionale Pro-Objekt Kollisionskarte beinhaltet
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
	
	
	
	public void addGameBoardObject(IGameBoardObject gameBoardObject) throws IllegalBoardPositionException {
		
	}

	public void move(IMove move) throws IllegalMoveException {
	}
	
	

}