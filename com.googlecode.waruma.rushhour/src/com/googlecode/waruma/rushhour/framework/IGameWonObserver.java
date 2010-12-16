package com.googlecode.waruma.rushhour.framework;

/**
 * Schnittstellte die für Spiel-Gewonnen Benachrichtigungen implementiert werden
 * muss
 * 
 * @author Fabian Malinowski
 */
public interface IGameWonObserver {

	/**
	 * Wird vom Subject aufgerufen wenn das Spiel gewonnen wurde
	 */
	public void updateGameWon();
}
