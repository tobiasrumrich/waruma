
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
 * 
 * @author fabian
 * 
 *         Diese Klasse stellt ein Spielerauto dar. Sie erweitert die Klasse
 *         StandardCar, indem sie ein Ziel beinhaltet und nach erfolgtem Move
 *         prüft, ob eben dieses Ziel erreicht wurde. Darüber hinaus bietet sie
 *         Observern die Möglichkeit, sich zu registieren und informieren zu
 *         lassen, sobald das Spielerauto das Ziel erreicht hat.
 * 
 */

public class PlayerCar extends StandardCar implements IPlayer, Serializable {	
	private static final long serialVersionUID = -5048696909045388704L;
	private Point destination;
	private Set<IReachedDestinationObserver> observers;
	private ICollisionDetector collisionDetector;

	public PlayerCar(Boolean[][] collisionMap, Point position,
			Orientation orientation, ICollisionDetector collisionDetector) {
		super(collisionMap, position, orientation);
		this.collisionDetector = collisionDetector;
		this.observers = new HashSet<IReachedDestinationObserver>();
	}
	
	public PlayerCar(Boolean[][] collisionMap, Point position,
			Orientation orientation, Point destination, ICollisionDetector collisionDetector) {
		this(collisionMap, position, orientation, collisionDetector);
		this.destination = destination;
	}

	// führt den Move aus und fragt anschließend den CollisionDetector, ob das
	// Ziel erreicht wurde. Bei positiver Rückmeldung werden die Observer
	// informiert
	@Override
	public void move(int distance) throws IllegalMoveException {
		super.move(distance);
		if (collisionDetector.hitPoint(this, destination)) {
			for (IReachedDestinationObserver currentObserver : observers) {
				currentObserver.updateReachedDestination(this);
			}
		}
	}

	
	/**
	 * Registriert einen IReachedDestinationObserver 
	 */
	@Override
	public void registerReachedDestination(
			IReachedDestinationObserver eventTarget) {
		observers.add(eventTarget);
	}

	public void setDestination(Point destination) {
		this.destination = destination;
	}

	public Point getDestination(){
		return this.destination;
	}
		
	@Override
	public int hashCode() {
		final int prime = 75503;
		int result = super.hashCode();
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
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