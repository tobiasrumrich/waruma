package com.googlecode.waruma.rushhour.framework;

/**
 * Schnittstellendefinition für Observer die Player beobachten
 * 
 * @author Fabian Malinowski
 */
public interface IReachedDestinationObserver {

	/**
	 * Teilt dem Observer mit, dass der IPlayer das Ziel erreicht hat
	 * 
	 * @param player
	 *            Player der das Ziel erreicht hat
	 */
	public void updateReachedDestination(IPlayer player);

	/**
	 * Fügt einen Player in die Liste der observierten Players hinzu und
	 * registriert sich beim Player
	 * 
	 * @param player
	 *            Zu observierender Player
	 */
	public void addPlayer(IPlayer player);
}
