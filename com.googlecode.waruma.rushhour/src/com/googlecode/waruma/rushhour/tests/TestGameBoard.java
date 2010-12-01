package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;
import java.util.Set;
import java.util.Stack;

import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.PlayerCar;
import com.googlecode.waruma.rushhour.game.RushHourCollisionDetector;
import com.googlecode.waruma.rushhour.game.StandardCar;
import com.googlecode.waruma.rushhour.game.SteeringLock;

import junit.framework.TestCase;

public class TestGameBoard extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		
	}

	/**
	 * Basic GameBoard initialization
	 */
	public void testGameBoard() {
		GameBoard gameBoard = new GameBoard(new RushHourCollisionDetector(7));
		assertTrue(gameBoard.getGameBoardObjects() instanceof Set<?>);
		assertTrue(gameBoard.getMoveHistory() instanceof Stack<?>);
	}

	public void testAddGameBoardObjects() {
		GameBoard gameBoard = new GameBoard(new RushHourCollisionDetector(6));
		Boolean[][] collisionMap = {{true,true}};
		
		StandardCar car1 = new StandardCar(collisionMap, new Point(1,1), Orientation.SOUTH);
		PlayerCar car2 = new PlayerCar(collisionMap, new Point(3,1), Orientation.EAST);
		SteeringLock car3 = new SteeringLock(new StandardCar(collisionMap, new Point (4,4), Orientation.WEST));
		
		gameBoard.addGameBoardObject(car1);
		gameBoard.addGameBoardObject(car2);
		gameBoard.addGameBoardObject(car3);
		
		Set<IGameBoardObject> gameBoardObjects = gameBoard.getGameBoardObjects();
		
		assertEquals(3, gameBoardObjects.size());
		assertTrue(gameBoardObjects.contains(car1));
		assertTrue(gameBoardObjects.contains(car2));
		assertTrue(gameBoardObjects.contains(car3));
	}

	public void testAddGameBoardsErrorCheck() {
		GameBoard gameBoard = new GameBoard(new RushHourCollisionDetector(6));
		Boolean[][] collisionMap = {{true,true}};
		
		// Kollidieren
		StandardCar car1 = new StandardCar(collisionMap, new Point(1,1), Orientation.SOUTH);
		PlayerCar car2 = new PlayerCar(collisionMap, new Point(1,2), Orientation.EAST);
		
		// Reicht über ein 6x6 Feld hinaus
		StandardCar car3 = new StandardCar(collisionMap, new Point (5,6), Orientation.WEST);
		 
		gameBoard.addGameBoardObject(car1);
		try
		
		
	}
	
	public void testGetMoveHistory() {
		fail("Not yet implemented");
	}

	public void testSetCollisionDetector() {
		fail("Not yet implemented");
	}

	public void testMove() {
		fail("Not yet implemented");
	}

	public void testAddGameBoardObject() {
		fail("Not yet implemented");
	}

}
