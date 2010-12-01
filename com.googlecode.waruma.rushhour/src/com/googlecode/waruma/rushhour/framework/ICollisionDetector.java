package com.googlecode.waruma.rushhour.framework;
public interface ICollisionDetector {
	
  public void checkCollision(IMove move);
  public void addGameBoardObject(IGameBoardObject gameBoardObject);

}