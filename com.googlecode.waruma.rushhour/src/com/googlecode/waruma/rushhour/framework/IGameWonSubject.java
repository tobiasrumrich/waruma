package com.googlecode.waruma.rushhour.framework;

public interface IGameWonSubject {

	/**
	 * registriert eine IGameWonObserver
	 * 
	 * @param eventTarget Observer
	 */
	public void registerGameWon(IGameWonObserver eventTarget);
}
