package com.googlecode.waruma.rushhour.framework;

import java.util.List;

public interface ISolver {
	
	/**
	 * Löst das Spielbrett und gibt die zum Erreichen notwendigen Züge zurück
	 * 
	 * @return Liste von IMoves
	 */
	public List<IMove> solveGameBoard();

}