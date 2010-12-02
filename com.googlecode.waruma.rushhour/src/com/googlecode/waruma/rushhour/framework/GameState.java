package com.googlecode.waruma.rushhour.framework;

import java.util.Date;
import java.util.Set;

public class GameState implements IReachedDestinationObserver, IGameWonSubject{
  private Set<IPlayer> player;

  
  public void updateReachedDestination(IPlayer player) {
	  
  }
  
  public void registerGameWon(IGameWonObserver eventTrigger) {

	
  }
}