package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

public interface IPlayer {

	/**
	 * registriert einen Observer
	 * 
	 * @param eventTarget
	 *            zu registrierender IReachedDestinationObserver
	 */
	public void registerReachedDestination(
			IReachedDestinationObserver eventTarget);

	public Point getDestination();

	/**
	 * gibt zurück, ob ein IPlayer sein Ziel erreicht hat
	 * 
	 * @return true, wenn IPlayer sein Ziel erreicht hat
	 */
	public boolean reachedDestination();
}
