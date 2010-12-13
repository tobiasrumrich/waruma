package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.FileSystemObjectStorage;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IObjectStorage;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * 
 * @author fabian & tobias
 * 
 */

public class RushHourBoardCreationControler {
	private GameBoard gameBoard;
	private CollisionDetector collisionDetector;
	private boolean hasPlayer;

	public RushHourBoardCreationControler() {
		this.collisionDetector = new CollisionDetector(6);
		this.gameBoard = new GameBoard(this.collisionDetector);
		this.hasPlayer = false;
	}

	public IGameBoardObject createCar(Point position, Orientation orientation,
			Boolean steeringLock) throws IllegalBoardPositionException {

		IGameBoardObject car = new StandardCar(new Boolean[][] { { true },
				{ true } }, position, orientation);
		
		if (steeringLock) {
			car = new SteeringLock((AbstractMoveable) car);
		}

		gameBoard.addGameBoardObject(car);

		return car;
	}

	public IGameBoardObject createTruck(Point position, Orientation orientation,
			Boolean steeringLock) throws IllegalBoardPositionException {
		IGameBoardObject truck = new StandardCar(new Boolean[][] { { true },
				{ true }, { true } }, position, orientation);
		
		if (steeringLock) {
			truck = new SteeringLock((AbstractMoveable) truck);
		}

		gameBoard.addGameBoardObject(truck);

		return truck;
	}

	public IGameBoardObject createPlayerCar(Point position, Point destination,
			Orientation orientation) throws IllegalBoardPositionException {
		if (hasPlayer) {
			throw new IllegalBoardPositionException(
					"Es ist nur ein Spielerauto zulässig!");
		}
		PlayerCar car = new PlayerCar(new Boolean[][] { { true, true } },
				position, orientation, collisionDetector);
		car.setDestination(destination);
		// throws IllegalBoardPositionException
		gameBoard.addGameBoardObject(car);
		
		return car;
	}
	
	public void changeCarPosition(IGameBoardObject gameBoardObject, Point position) throws IllegalBoardPositionException{
		gameBoard.repositionGameBoardObject(gameBoardObject, position);
	}

	public boolean validTile(Point position) {
		return collisionDetector.validTile(position);
	}

	public void loadGameBoard(String location) throws IOException,
			ClassNotFoundException {
		FileSystemObjectStorage fileSystemObjectStorage = new FileSystemObjectStorage();
		gameBoard = (GameBoard) fileSystemObjectStorage.deserialize(location);
	}

	public void saveGameBoard(String location) throws IOException {
		FileSystemObjectStorage fileSystemObjectStorage = new FileSystemObjectStorage();
		fileSystemObjectStorage.serialize(gameBoard, location);
	}
	
	public Object getCurrentState(){
		return gameBoard;
	}

}