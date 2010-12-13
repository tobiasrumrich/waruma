package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

public interface IPlayer {
	public void registerReachedDestination(
			IReachedDestinationObserver eventTarget);

	public Point getDestination();
}
