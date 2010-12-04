package com.googlecode.waruma.rushhour.framework;

import java.util.HashSet;
import java.util.Set;


public class GameState implements IReachedDestinationObserver, IGameWonSubject{
  private Set<IGameWonObserver> observers = new HashSet<IGameWonObserver>();
  private Set<IPlayer> players = new HashSet<IPlayer>();
  
  //Ein zukünftigt zu übwachender Player wird hinzugefügt
  public void addPlayer(IPlayer player) {
	  players.add(player);
	  player.registerReachedDestination(this);
  }


  public void registerGameWon(IGameWonObserver eventTrigger) {
	  observers.add(eventTrigger);
  }

  //ReachedDestination Event wird behandelt
  public void updateReachedDestination(IPlayer player) {
	  players.remove(player);
	
	  if(players.isEmpty())
		 for (IGameWonObserver currentobserver : observers) {
			  currentobserver.updateGameWon();
		 }
  }
}
  
