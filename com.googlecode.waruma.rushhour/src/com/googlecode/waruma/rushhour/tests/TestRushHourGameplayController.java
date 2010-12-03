package com.googlecode.waruma.rushhour.tests;

import junit.framework.TestCase;

import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IObjectStorage;
import com.googlecode.waruma.rushhour.game.RushHourGameplayController;

/**
 * Testet das Verhalten des RushHourGamePlayController
 * @author dep18237
 *
 */
public class TestRushHourGameplayController extends TestCase {

	private RushHourGameplayController controller;

	protected void setUp() throws Exception {
		super.setUp();
		controller = new RushHourGameplayController();
	}

	private class MockGameBoardStorage implements IObjectStorage{
		private Boolean calledLoad = false;
		private Boolean calledSave = false;
		private String location;
		private GameBoard gameBoard;

		
		public GameBoard loadGameBoard(String location) {
			this.location = location;
			calledLoad = true;
			return null;
		}

		public void saveGameBoard(GameBoard gameBoard, String location) {
			this.gameBoard = gameBoard;
			this.location = location;
			calledSave = true;
		}
	}
	
	public void testLoadGame() {
		fail("Not yet implemented");
	}

	public void testSaveGame() {
		fail("Not yet implemented");
	}

	public void testSolveGame() {
		fail("Not yet implemented");
	}

	public void testGetCars() {
		fail("Not yet implemented");
	}

	public void testUpdateGameWon() {
		fail("Not yet implemented");
	}

	public void testMoveCar() {
		fail("Not yet implemented");
	}

}