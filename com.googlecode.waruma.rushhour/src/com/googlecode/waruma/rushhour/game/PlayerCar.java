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
		observers = new HashSet<IReachedDestinationObserver>();
		reachedDestination = false;
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

	/*
	 * (non-Javadoc)
	 * @see com.googlecode.waruma.rushhour.game.StandardCar#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PlayerCar other = (PlayerCar) obj;
		if (destination == null) {
			if (other.destination != null) {
				return false;
			}
		} else if (!destination.equals(other.destination)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.googlecode.waruma.rushhour.framework.IPlayer#getDestination()
	 */
	@Override
	public Point getDestination() {
		return destination;
	}

	/*
	 * Einfache Implementierung, die unabhängig von der Destination den HashCode
	 * des PlayersCar bestimmt (non-Javadoc)
	 * 
	 * @see com.googlecode.waruma.rushhour.game.StandardCar#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 75503;
		int result = prime * super.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.googlecode.waruma.rushhour.game.StandardCar#move(int)
	 */
	@Override
	public void move(int distance) throws IllegalMoveException {
		super.move(distance);
		if (collisionDetector.hitPoint(this, destination)) {
			reachedDestination = true;
			System.out.println(observers.size());
			for (IReachedDestinationObserver currentObserver : observers) {
				currentObserver.updateReachedDestination(this);
			}
		}
	}
	
	/**
	 * Entfernt alle Observer aus der Liste
	 */
	public void unregisterAllObservers(){
		if(observers == null)
			observers = new HashSet<IReachedDestinationObserver>();
		observers.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see com.googlecode.waruma.rushhour.framework.IPlayer#reachedDestination()
	 */
	@Override
	public boolean reachedDestination() {
		return reachedDestination;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.IPlayer#registerReachedDestination
	 * (com.googlecode.waruma.rushhour.framework.IReachedDestinationObserver)
	 */
	@Override
	public void registerReachedDestination(
			IReachedDestinationObserver eventTarget) {
		if (observers == null) {
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

}