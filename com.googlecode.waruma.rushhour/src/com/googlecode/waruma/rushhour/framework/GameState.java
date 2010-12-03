package com.googlecode.waruma.rushhour.framework;

import java.util.Set;

public class GameState implements IReachedDestinationObserver, IGameWonSubject{
  private Set<IGameWonObserver> observers;
  private Set<IPlayer> players;
  
  
  //fügt einen zu überwachenden Player hinzu
  public void addPlayer(IPlayer player) {
	  players.add(player);
	  player.registerReachedDestination(this);
  }


  //registriert einen GameWon-Observer
  public void registerGameWon(IGameWonObserver eventTrigger) {
	  observers.add(eventTrigger);
  }

  //ReachedDestination Event wird behandelt
  public void updateReachedDestination(IPlayer player) {
	for (IPlayer currentPlayer : players) {
		if(currentPlayer==player) {
			players.remove(player);
			checkWhetherLatest();
		}	
	}	
  }
  
  //prüft, ob das ReachedDestiantion Event das letzte war und informiert in diesem Fall die GameWonObserver
  public void checkWhetherLatest() {
	  if(players.isEmpty())
		  for (IGameWonObserver currentobserver : observers) {
			  currentobserver.updateGameWon();
		  }
  }
  
  

}