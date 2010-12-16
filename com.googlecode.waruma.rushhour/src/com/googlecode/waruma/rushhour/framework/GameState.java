package com.googlecode.waruma.rushhour.framework;

import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse GameState dient als Vermittler zwischen den Players und dem
 * RushHourGameplayController. Sie registriert sich bei allen Playern als
 * Observer und wird von ihnen benachrichtigt, wenn ein Player sein Ziel
 * erreicht hat. Sind alle Player im Ziel, sendet es eine Nachricht an den
 * eigenen Observer (RushHourGameplayController) und teilt ihm mit, dass das
 * Spielende erreicht ist.
 * 
 * @author Fabian Malinowski
 */

public class GameState implements IReachedDestinationObserver, IGameWonSubject {
	private Set<IGameWonObserver> observers = new HashSet<IGameWonObserver>();
	private int players = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.IReachedDestinationObserver#
	 * addPlayer(com.googlecode.waruma.rushhour.framework.IPlayer)
	 */
	@Override
	public void addPlayer(IPlayer player) {
		players++;
		player.registerReachedDestination(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.IGameWonSubject#registerGameWon
	 * (com.googlecode.waruma.rushhour.framework.IGameWonObserver)
	 */
	@Override
	public void registerGameWon(IGameWonObserver eventTarget) {
		observers.add(eventTarget);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.IReachedDestinationObserver#
	 * updateReachedDestination
	 * (com.googlecode.waruma.rushhour.framework.IPlayer)
	 */
	@Override
	public void updateReachedDestination(IPlayer player) {
		players--;

		if (players == 0)
			for (IGameWonObserver currentobserver : observers) {
				currentobserver.updateGameWon();
			}
	}
}
