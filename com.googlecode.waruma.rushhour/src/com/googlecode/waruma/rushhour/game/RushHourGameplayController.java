package com.googlecode.waruma.rushhour.game;

import java.util.List;
import java.util.Timer;

import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.GameState;
import com.googlecode.waruma.rushhour.framework.IGameWonObserver;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;

public class RushHourGameplayController implements IGameWonObserver {

	private GameState gameState;
	private Timer gameTimer;
	private GameBoard gameBoard;

	public void loadGame(String location) {
	}

	public void saveGame(String location) {
	}

	public List<IMove> solveGame() {
		return null;
	}

	public IMoveable getCars() {
		return null;
	}

	public void updateGameWon() {
	}

	public void moveCar(IMove move) {
	}

}