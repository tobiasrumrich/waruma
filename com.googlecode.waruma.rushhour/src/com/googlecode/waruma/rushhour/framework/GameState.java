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
	private Set<IPlayer> players = new HashSet<IPlayer>();

	// Ein zukünftigt zu übwachender Player wird hinzugefügt
	public void addPlayer(IPlayer player) {
		players.add(player);
		player.registerReachedDestination(this);
	}

	@Override
	public void registerGameWon(IGameWonObserver eventTarget) {
		observers.add(eventTarget);
	}

	// ReachedDestination Event wird behandelt
	@Override
	public void updateReachedDestination(IPlayer player) {
		players.remove(player);

		if (players.isEmpty())
			for (IGameWonObserver currentobserver : observers) {
				currentobserver.updateGameWon();
			}
	}
}
