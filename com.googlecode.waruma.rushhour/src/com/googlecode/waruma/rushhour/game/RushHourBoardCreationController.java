package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.IOException;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.FileSystemObjectStorage;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IObjectStorage;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * 
 * @author fabian & tobias
 * 
 */

public class RushHourBoardCreationController {
	private GameBoard gameBoard;
	private CollisionDetector collisionDetector;
	private boolean hasPlayer;
	
	public RushHourBoardCreationController(){
		this.collisionDetector = new CollisionDetector(6);
		this.gameBoard = new GameBoard(this.collisionDetector);
		this.hasPlayer = false;
	}

	public void createCar(Point position, Orientation orientation,
			Boolean steeringLock) throws IllegalBoardPositionException {

		if (steeringLock == false) {
			StandardCar car = new StandardCar(
					new Boolean[][] { { true, true } }, position, orientation);
			gameBoard.addGameBoardObject(car);
		} else {
			SteeringLock car = new SteeringLock(new StandardCar(
					new Boolean[][] { { true, true } }, position, orientation));
			gameBoard.addGameBoardObject(car);
		}
	}

	public void createTruck(Point position, Orientation orientation,
			Boolean steeringLock) throws IllegalBoardPositionException {
		
		if (!steeringLock) {
			StandardCar car = new StandardCar(new Boolean[][] { { true, true,
					true } }, position, orientation);
			gameBoard.addGameBoardObject(car);
		} else {
			SteeringLock car = new SteeringLock(new StandardCar(
					new Boolean[][] { { true, true, true } }, position,
					orientation));
			gameBoard.addGameBoardObject(car);
		}
	}

	public void createPlayerCar(Point position, Point destination,
			Orientation orientation) throws IllegalBoardPositionException {
		if(hasPlayer){
			throw new IllegalBoardPositionException("Es ist nur ein Spielerauto zulässig!");
		}
		PlayerCar car = new PlayerCar(new Boolean[][] { { true, true } },
				position, orientation, collisionDetector);
		car.setDestination(destination);
		// throws IllegalBoardPositionException
		gameBoard.addGameBoardObject(car);
		hasPlayer = true;
	}
	
	public boolean validTile(Point position){
		return collisionDetector.validTile(position);
	}

	public void loadGameBoard(String location) throws IOException, ClassNotFoundException {
		FileSystemObjectStorage fileSystemObjectStorage = new FileSystemObjectStorage();
		gameBoard = (GameBoard) fileSystemObjectStorage.deserialize(location);
	}
	
	public void saveGameBoard(String location) throws IOException {
		FileSystemObjectStorage fileSystemObjectStorage = new FileSystemObjectStorage();
		fileSystemObjectStorage.serialize(gameBoard, location);
	}

}