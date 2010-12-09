package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

public interface IPlayer {

	public void unregisterAllObservers();
	
	public void registerReachedDestination(
			IReachedDestinationObserver eventTarget);

	public Point getDestination();

	public boolean reachedDestination();
}
