package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

/**
 * Schnittstelle für Spielerobjekte
 * 
 * @author Florian
 */
public interface IPlayer {

	/**
	 * Registriert einen Observer
	 * 
	 * @param eventTarget
	 *            zu registrierender IReachedDestinationObserver
	 */
	public void registerReachedDestination(
			IReachedDestinationObserver eventTarget);

	public Point getDestination();

	/**
	 * Gibt zurück, ob ein IPlayer sein Ziel erreicht hat
	 * 
	 * @return true, wenn IPlayer sein Ziel erreicht hat
	 */
	public boolean reachedDestination();
}
