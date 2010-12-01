package com.googlecode.waruma.rushhour.game;

import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;

public class RushHourCollisionDetector implements ICollisionDetector {
	public Boolean[][] collisionMap;

	public RushHourCollisionDetector(int size){
		this(size, size);
	}
	
	public RushHourCollisionDetector(int width, int height) {
		this.collisionMap = new Boolean[width-1][height-1];
	}

	public void checkCollision(IMove move) {
	}
	
	public void addGameBoardObject(IGameBoardObject gameBoardObject) {
		
	}

}