package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;

import junit.framework.TestCase;

import com.googlecode.waruma.rushhour.framework.FileSystemObjectStorage;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.CollisionDetector;
import com.googlecode.waruma.rushhour.game.PlayerCar;
import com.googlecode.waruma.rushhour.game.StandardCar;
import com.googlecode.waruma.rushhour.game.SteeringLock;

public class TestFileSystemObjectStorage extends TestCase {

	private FileSystemObjectStorage fileSystemObjectStorage;
	private FileSystemObjectStorageMock objectMock;
	private GameBoard gameBoard;

	@Override
	protected void setUp() throws Exception {
		fileSystemObjectStorage = new FileSystemObjectStorage();
		objectMock = new FileSystemObjectStorageMock();

		ICollisionDetector collisionDetector = new CollisionDetector(10, 10);
		gameBoard = new GameBoard(collisionDetector);
		Boolean[][] collisionMap = new Boolean[][] { { true, true } };
		gameBoard.addGameBoardObject(new PlayerCar(collisionMap,
				new Point(3, 8), Orientation.WEST, collisionDetector));
		gameBoard.addGameBoardObject(new StandardCar(collisionMap, new Point(9,
				1), Orientation.SOUTH));
		gameBoard.addGameBoardObject(new SteeringLock(new StandardCar(
				new Boolean[][] { { true, true, true } }, new Point(3, 4),
				Orientation.NORTH)));

	}

	public void testStorageWithGameBoard() {
		String location = "GameBoard.ser";
		try {
			fileSystemObjectStorage.serialize(gameBoard, location);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		Serializable deserialize = null;
		try {
			deserialize = fileSystemObjectStorage.deserialize(location);
		} catch (IOException e) {
			fail("Received IOExeption");
		} catch (ClassNotFoundException e) {
			fail("Received ClassNotFoundException");
		}
		assertEquals(gameBoard, deserialize);
	}

	public void testStorageWithMock() {
		String location = "objectMock.ser";
		try {
			fileSystemObjectStorage.serialize(objectMock, location);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		Serializable deserialize = null;
		try {
			deserialize = fileSystemObjectStorage.deserialize(location);
		} catch (IOException e) {
			fail("Received IOExeption");
		} catch (ClassNotFoundException e) {
			fail("Received ClassNotFoundException");
		}
		assertEquals(objectMock, deserialize);
	}
}
