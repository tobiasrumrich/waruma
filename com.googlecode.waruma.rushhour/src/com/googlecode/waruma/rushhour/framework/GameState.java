package com.googlecode.waruma.rushhour.framework;

import java.util.Date;

public class GameState implements IReachedDestinationObserver, IGameWonSubject{

  private Date gameStartTime;
  private GameBoard gameBoard;
  private IPlayer player;

  public void start() {
  }

  public void stop() {
  }

  public void reset() {
  }
  
  public void updateReachedDestination(IPlayer player) {
	  
  }
  
  public void registerGameWon(IGameWonObserver eventTrigger) {

	
  }
}