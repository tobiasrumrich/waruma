package com.googlecode.waruma.rushhour.framework;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public interface IMoveable {

	/**
	 * führt einen Zug durch
	 * 
	 * @param distance
	 *            Zugweite
	 * 
	 * @throws IllegalMoveException
	 *             wird bei ungültigem Zug geworfen
	 */
	public void move(int distance) throws IllegalMoveException;

	/**
	 * prüft, ob ein Zug möglich ist
	 * 
	 * @param distance
	 *            Die Distanz, um die sich das IMoveable in positiver oder
	 *            negativer Richtung bewegen möchte
	 * 
	 * @throws IllegalMoveException
	 *             wird geworfen, wenn Zug nicht möglich
	 */
	public void checkMove(int distance) throws IllegalMoveException;
}
