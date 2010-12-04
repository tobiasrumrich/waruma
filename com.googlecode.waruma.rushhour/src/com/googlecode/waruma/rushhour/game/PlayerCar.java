package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
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

public class PlayerCar extends StandardCar implements IPlayer {

	private Point destination;
	private Set<IReachedDestinationObserver> observers = new HashSet<IReachedDestinationObserver>();

	public PlayerCar(Boolean[][] collisionMap, Point position,
			Orientation orientation) {
		super(collisionMap, position, orientation);
	}

	// führt den Move aus und fragt anschließend den CollisionDetector, ob das
	// Ziel erreicht wurde. Bei positiver Rückmeldung werden die Observer
	// informiert
	public void move(Integer distance) throws IllegalMoveException {
		super.move(distance);

		if (RushHourCollisionDetector.hitPoint(this, destination)) {
			for (IReachedDestinationObserver currentObserver : observers) {
				currentObserver.updateReachedDestination(this);
			}
		}
	}

	// Ein Observer registriert sich beim Player, sodass dich beim Erreichen des
	// Ziels den Observer benachrichtigt
	@Override
	public void registerReachedDestination(
			IReachedDestinationObserver eventTarget) {
		observers.add(eventTarget);
	}

	public void setDestination(Point destination) {
		this.destination = destination;
	}

}