package com.googlecode.waruma.rushhour.framework;

/**
 * Schnittstelle für Objekte die über den Spielgewinn benachrichtigen
 * 
 * @author Fabian Malinowski
 */
public interface IGameWonSubject {

	/**
	 * Wegistriert den übergebenen IGameWonObserver beim Subject
	 * 
	 * @param eventTarget
	 *            Observer
	 */
	public void registerGameWon(IGameWonObserver eventTarget);
}
