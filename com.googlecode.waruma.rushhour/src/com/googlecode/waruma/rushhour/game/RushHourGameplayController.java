package com.googlecode.waruma.rushhour.game;

import java.util.List;

import com.googlecode.waruma.rushhour.framework.Game;
import com.googlecode.waruma.rushhour.framework.IGameWonObserver;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;

public class RushHourGameplayController implements IGameWonObserver {

	private Game game;

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