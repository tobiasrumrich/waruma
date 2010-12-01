package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;
import java.util.Set;
import java.util.Stack;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.Move;
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

	public void testGameBoardInitialization() {
		GameBoard gameBoard = new GameBoard(new RushHourCollisionDetector(7));
		assertTrue(gameBoard.getGameBoardObjects() instanceof Set<?>);
		assertTrue(gameBoard.getMoveHistory() instanceof Stack<?>);
	}

	public void testAddGameBoardObjects() throws IllegalBoardPositionException {
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

	public void testAddGameBoardCollision() throws IllegalBoardPositionException {
		GameBoard gameBoard = new GameBoard(new RushHourCollisionDetector(6));
		Boolean[][] collisionMap = {{true,true}};
		
		// Kollidieren
		StandardCar car1 = new StandardCar(collisionMap, new Point(1,1), Orientation.SOUTH);
		PlayerCar car2 = new PlayerCar(collisionMap, new Point(1,2), Orientation.EAST);
		
		Boolean exceptionThrown = false;		
		gameBoard.addGameBoardObject(car1);
		
		try{
			gameBoard.addGameBoardObject(car2);
		} catch (IllegalBoardPositionException e){
			exceptionThrown = true;
		}
	
		assertTrue(exceptionThrown);		
	}
	
	public void testAddGameBoardOutsideOfBoard() {
		GameBoard gameBoard = new GameBoard(new RushHourCollisionDetector(6));
		Boolean[][] collisionMap = {{true,true}};
		// Reicht über ein 6x6 Feld hinaus
		StandardCar car = new StandardCar(collisionMap, new Point (5,6), Orientation.WEST);
		
		Boolean exceptionThrown = false;		
		try{
			gameBoard.addGameBoardObject(car);
		} catch (IllegalBoardPositionException e){
			exceptionThrown = true;
		}
	
		assertTrue(exceptionThrown);
	}
	
	public void testGetMoveHistory() {
		fail("Not yet implemented");
	}

	public void testMoveOutsideOfBoard() throws IllegalBoardPositionException {
		GameBoard gameBoard = new GameBoard(new RushHourCollisionDetector(6));
		Boolean[][] collisionMap = {{true,true}};
		
		// 1 1 2 2 X X //
		AbstractMoveable moveable1 = new StandardCar(collisionMap, new Point(2,2), Orientation.WEST);
		AbstractMoveable moveable2 = new StandardCar(collisionMap, new Point(0,2), Orientation.EAST);
		
		gameBoard.addGameBoardObject(moveable1);
		gameBoard.addGameBoardObject(moveable2);
		
		assertTrue(MoveThrowsIllegalMoveException(gameBoard, moveable2, -1));
		assertTrue(MoveThrowsIllegalMoveException(gameBoard, moveable2, -2));
		assertTrue(MoveThrowsIllegalMoveException(gameBoard, moveable2, -1000));
		assertTrue(MoveThrowsIllegalMoveException(gameBoard, moveable2, 5));
		assertTrue(MoveThrowsIllegalMoveException(gameBoard, moveable2, 6));
		assertTrue(MoveThrowsIllegalMoveException(gameBoard, moveable2, 1000));
	}
	
	public void testMoveCollision() throws IllegalBoardPositionException {
		GameBoard gameBoard = new GameBoard(new RushHourCollisionDetector(6));
		Boolean[][] collisionMap = {{true,true}};
		
		// 1 1 2 2 X X //
		AbstractMoveable moveable1 = new StandardCar(collisionMap, new Point(2,2), Orientation.WEST);
		AbstractMoveable moveable2 = new StandardCar(collisionMap, new Point(0,2), Orientation.EAST);
		
		gameBoard.addGameBoardObject(moveable1);
		gameBoard.addGameBoardObject(moveable2);
		
		assertTrue(MoveThrowsIllegalMoveException(gameBoard, moveable2, 1));
		assertTrue(MoveThrowsIllegalMoveException(gameBoard, moveable2, 2));
		assertTrue(MoveThrowsIllegalMoveException(gameBoard, moveable2, 3));
		assertTrue(MoveThrowsIllegalMoveException(gameBoard, moveable2, 4));
		
	}
	
	private boolean MoveThrowsIllegalMoveException(GameBoard board, IMoveable moveable, int distance){
		IMove move = new Move(moveable, distance);
		try{
			board.move(move);
		} catch (IllegalMoveException e) {
			return true;
		}
		return false;
	}

}
