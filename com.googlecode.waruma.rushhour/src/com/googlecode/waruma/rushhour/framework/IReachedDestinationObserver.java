package com.googlecode.waruma.rushhour.framework;

public interface IReachedDestinationObserver {

	/**
	 * teilt dem Observer mit, dass der IPlayer das Ziel erreicht hat
	 * 
	 * @param player
	 *            IPlayer-Objekt, welches das Ziel erreicht hat
	 */
	public void updateReachedDestination(IPlayer player);

	/**
	 * fügt einen IPlayer in die Liste der observierten IPlayers hinzu und
	 * registriert sich beim Player
	 * 
	 * @param player
	 *            zu observierender IPlayer
	 */
	public void addPlayer(IPlayer player);
}
