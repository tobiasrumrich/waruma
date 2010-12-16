package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

/**
 * Schnittstelle für Spielerobjekte
 * 
 * @author Fabian Malinowski
 */
public interface IPlayer {

	public Point getDestination();

	/**
	 * Gibt zurück, ob ein IPlayer sein Ziel erreicht hat
	 * 
	 * @return true, wenn IPlayer sein Ziel erreicht hat
	 */
	public boolean reachedDestination();

	/**
	 * Registriert einen Observer
	 * 
	 * @param eventTarget
	 *            zu registrierender IReachedDestinationObserver
	 */
	public void registerReachedDestination(
			IReachedDestinationObserver eventTarget);

	/**
	 * Deregistriert alle Observer
	 */
	public void unregisterAllObservers();
}
