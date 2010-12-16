package com.googlecode.waruma.rushhour.framework;

import java.util.HashSet;
import java.util.Set;

/*
 * @author fabian
 * 
 * Die Klasse GameState dient als Vermittler zwischen den Players und dem RushHourGameplayController
 * Sie registriert sich bei allen Playern als Observer und wird von ihnen benachrichtigt, wenn ein Player sein Ziel erreicht hat.
 * Sind alle Player im Ziel, sendet es eine Nachricht an den eigenen Observer (RushHourGameplayController) und teilt ihm mit, dass das Spielende erreicht ist.
 * 
 */

public class GameState implements IReachedDestinationObserver, IGameWonSubject {
	private Set<IGameWonObserver> observers = new HashSet<IGameWonObserver>();
	private int players = 0;

	// Ein zuknftigt zu überwachender Player wird hinzugefügt
	public void addPlayer(IPlayer player) {
		players++;
		player.registerReachedDestination(this);
	}

	@Override
	public void registerGameWon(IGameWonObserver eventTarget) {
		observers.add(eventTarget);
	}

	// ReachedDestination Event wird behandelt
	@Override
	public void updateReachedDestination(IPlayer player) {
		players--;

		if (players == 0)
			for (IGameWonObserver currentobserver : observers) {
				currentobserver.updateGameWon();
			}
	}
}
