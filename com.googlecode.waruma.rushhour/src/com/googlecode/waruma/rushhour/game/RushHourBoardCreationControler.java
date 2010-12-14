package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.IOException;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.FileSystemObjectStorage;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
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

	public IGameBoardObject createCar(org.eclipse.swt.graphics.Point position, Orientation orientation,
			boolean steeringLock) throws IllegalBoardPositionException {

		IGameBoardObject car = new StandardCar(new Boolean[][] { { true },
				{ true } }, swtToAwtPoint(position), orientation);
		
		if (steeringLock) {
			car = new SteeringLock((AbstractMoveable) car);
		}

		gameBoard.addGameBoardObject(car);

		return car;
	}

	public IGameBoardObject createTruck(org.eclipse.swt.graphics.Point position, Orientation orientation,
			boolean steeringLock) throws IllegalBoardPositionException {
		IGameBoardObject truck = new StandardCar(new Boolean[][] { { true },
				{ true }, { true } }, swtToAwtPoint(position), orientation);
		
		if (steeringLock) {
			truck = new SteeringLock((AbstractMoveable) truck);
		}

		gameBoard.addGameBoardObject(truck);

		return truck;
	}

	public IGameBoardObject createPlayerCar(org.eclipse.swt.graphics.Point position, org.eclipse.swt.graphics.Point destination,
			Orientation orientation) throws IllegalBoardPositionException {
		if (hasPlayer) {
			throw new IllegalBoardPositionException(
					"Es ist nur ein Spielerauto zulässig!");
		}
		PlayerCar car = new PlayerCar(new Boolean[][] { { true, true } },
				swtToAwtPoint(position), orientation, collisionDetector);
		car.setDestination(swtToAwtPoint(destination));
		// throws IllegalBoardPositionException
		gameBoard.addGameBoardObject(car);
		
		return car;
	}
	
	public void changeCarPosition(IGameBoardObject gameBoardObject, org.eclipse.swt.graphics.Point position) throws IllegalBoardPositionException{
		gameBoard.repositionGameBoardObject(gameBoardObject, swtToAwtPoint(position));
	}

	public boolean validTile(org.eclipse.swt.graphics.Point position) {
		return collisionDetector.validTile(swtToAwtPoint(position));
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
	
	private Point swtToAwtPoint(org.eclipse.swt.graphics.Point swtPoint){
		if(swtPoint == null)
			throw new IllegalArgumentException("Punkt nicht gesetzt");
		
		return new Point(swtPoint.x, swtPoint.y);
	}

}