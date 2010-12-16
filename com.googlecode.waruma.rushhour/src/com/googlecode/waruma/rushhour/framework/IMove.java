package com.googlecode.waruma.rushhour.framework;

/**
 * Schnittstellendefinition für einen Zug
 * 
 * @author Tobias Rumrich
 */
public interface IMove {

	/**
	 * Gibt die Distanz des Zuges zurück
	 * 
	 * @return Distanz eines IMoves
	 */
	public int getDistance();

	/**
	 * Gibt das Moveable Objekt zurück
	 * 
	 * @return Moveable Objekt
	 */
	public IMoveable getMoveable();

	/**
	 * Dreht die Richtung eines Zuges um (-1 wird zu 1)
	 */
	public void revertDirection();

}