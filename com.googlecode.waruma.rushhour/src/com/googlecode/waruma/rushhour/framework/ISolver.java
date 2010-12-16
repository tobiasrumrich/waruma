package com.googlecode.waruma.rushhour.framework;

import java.util.List;

/**
 * Schnittstellendefinition für Solver
 * 
 * @author Florian Warninghoff
 */
public interface ISolver {
	
	/**
	 * Löst das Spielbrett und gibt die zum Erreichen notwendigen Züge zurück
	 * 
	 * @return Liste von Zügen
	 */
	public List<IMove> solveGameBoard();

}