package com.googlecode.waruma.rushhour.framework;

public interface IMove {

	/**
	 * gibt die Distanz des IMoves zurück
	 * 
	 * @return Distanz eines IMoves
	 */
	public int getDistance();

	/**
	 * gibt das IMoveable-Objekt zurück
	 * 
	 * @return iMoveable-Objekt
	 */
	public IMoveable getMoveable();
	
	/**
	 * dreht die Richtung eines IMoves um (-1 wird zu 1)
	 */
	public void revertDirection();

}