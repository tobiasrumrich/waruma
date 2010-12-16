package com.googlecode.waruma.rushhour.framework;

import java.util.List;

public interface ISolver {
	
	/**
	 * löst das Gameboard und gibt ein Liste von Zügen zurück
	 * 
	 * @return Liste von IMoves
	 */
	public List<IMove> solveGameBoard();

}