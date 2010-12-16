package com.googlecode.waruma.rushhour.framework;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

/**
 * Schnittstelle für Bewegliche Objekte
 * 
 * @author Florian
 */
public interface IMoveable {

	/**
	 * Führt einen Zug durch
	 * 
	 * @param distance
	 *            Zugweite
	 * 
	 * @throws IllegalMoveException
	 *             Bei ungültigem Zug
	 */
	public void move(int distance) throws IllegalMoveException;

	/**
	 * Prüft, ob ein Zug möglich ist
	 * 
	 * @param distance
	 *            Die Distanz, um die sich das IMoveable in positiver oder
	 *            negativer Richtung bewegen möchte
	 * 
	 * @throws IllegalMoveException
	 *             Bei ungültigem Zug
	 */
	public void checkMove(int distance) throws IllegalMoveException;
}
