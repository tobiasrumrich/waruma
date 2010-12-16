package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.IReachedDestinationObserver;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * Diese Klasse implementiert das Spielerauto. Bei erreichen des Ziels werden
 * die registrierten Observer benachrichtigt.
 * 
 * @author Florian
 */

public class PlayerCar extends StandardCar implements IPlayer, Serializable {
	private static final long serialVersionUID = -5048696909045388704L;
	private Point destination;
	private transient Set<IReachedDestinationObserver> observers;
	private ICollisionDetector collisionDetector;
	private boolean reachedDestination;

	/**
	 * Erstellt ein PlayerCar OHNE das Ziel zu setzen
	 * 
	 * @param collisionMap
	 *            KollisionsMap des Autos
	 * @param position
	 *            Position auf dem Spielfeld
	 * @param orientation
	 *            Orientierung
	 * @param collisionDetector
	 *            Kollisionsdetektor zur Überprüfung auf erreichen des
	 *            Zielpunktes
	 */
	public PlayerCar(Boolean[][] collisionMap, Point position,
			Orientation orientation, ICollisionDetector collisionDetector) {
		super(collisionMap, position, orientation);
		this.collisionDetector = collisionDetector;
		this.observers = new HashSet<IReachedDestinationObserver>();
		this.reachedDestination = false;
	}

	/**
	 * Erstellt ein PlayerCar
	 * 
	 * @param collisionMap
	 *            KollisionsMap des Autos
	 * @param position
	 *            Position auf dem Spielfeld
	 * @param orientation
	 *            Orientierung
	 * @param destination
	 *            Zielpunkt
	 * @param collisionDetector
	 *            Kollisionsdetektor zur Überprüfung auf erreichen des
	 *            Zielpunktes
	 */
	public PlayerCar(Boolean[][] collisionMap, Point position,
			Orientation orientation, Point destination,
			ICollisionDetector collisionDetector) {
		this(collisionMap, position, orientation, collisionDetector);
		this.destination = destination;
	}

	/**
	 * Führt einen Zug durch
	 * 
	 * @param distance
	 *            Zugweite
	 */
	public void move(int distance) throws IllegalMoveException {
		super.move(distance);
		if (collisionDetector.hitPoint(this, destination)) {
			reachedDestination = true;
			for (IReachedDestinationObserver currentObserver : observers) {
				currentObserver.updateReachedDestination(this);
			}
		}
	}

	/**
	 * Registriert einen IReachedDestinationObserver bei dem PlayerCar
	 * 
	 * @param eventTarget
	 *            Observer
	 */
	public void registerReachedDestination(
			IReachedDestinationObserver eventTarget) {
		if(observers == null){
			observers = new HashSet<IReachedDestinationObserver>();
		}
		observers.add(eventTarget);
	}

	/**
	 * Setzen des Zielpunktes
	 * 
	 * @param destination
	 *            Zielpunkt
	 */
	public void setDestination(Point destination) {
		this.destination = destination;
	}

	/**
	 * Gibt das Ziel des PlayerCars zurück
	 * 
	 * @return Zielpunkt
	 */
	public Point getDestination() {
		return this.destination;
	}
	
	/**
	 * Gibt an ob das Auto sein Ziel erreicht hat
	 * @return True bei Erreichen
	 */
	public boolean reachedDestination(){
		return this.reachedDestination;
	}

	@Override
	public int hashCode() {
		final int prime = 75503;
		int result = prime* super.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerCar other = (PlayerCar) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		return true;
	}

}