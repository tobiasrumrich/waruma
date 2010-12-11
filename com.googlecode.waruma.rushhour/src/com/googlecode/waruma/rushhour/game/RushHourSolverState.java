package com.googlecode.waruma.rushhour.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.rits.cloning.Cloner;

public class RushHourSolverState {


	private Boolean[][] collisionMap;
	private Set<IGameBoardObject> moveableGameBoardObjects;
	private Set<IPlayer> players;
	private RushHourSolverState previousState;

	public RushHourSolverState(GameBoard gameBoard) {
		this.collisionMap = gameBoard.getCollisionDetector().getCollisionMap();
		this.moveableGameBoardObjects = new HashSet<IGameBoardObject>();
		this.players = new HashSet<IPlayer>();
		this.previousState = null;
		// Bewegbare Spielbrettobjekte vom Spielbrett extrahieren
		for (IGameBoardObject gameBoardObject : gameBoard.getGameBoardObjects()) {
			if (gameBoardObject instanceof IMoveable) {
				this.moveableGameBoardObjects.add(gameBoardObject);
				if (gameBoardObject instanceof IPlayer) {
					IPlayer player = (IPlayer) gameBoardObject;
					player.unregisterAllObservers();
					this.players.add(player);
				}
			}
		}
	}

	public RushHourSolverState(RushHourSolverState previousState,
			Boolean[][] collisionMap,
			Set<IGameBoardObject> moveableGameBoardObjects,
			Set<IPlayer> players) {
		this.previousState = previousState;
		this.collisionMap = collisionMap;
		this.moveableGameBoardObjects = moveableGameBoardObjects;
		this.players = players;
	}

	public List<IMove> GetValidMoves() {
		ICollisionDetector collisionDetector = new RushHourCollisionDetector(collisionMap);
		List<IMove> moveList = new ArrayList<IMove>();

		for (IGameBoardObject gameBoardObject : moveableGameBoardObjects) {
			moveList.addAll(collisionDetector.getValidMoves(gameBoardObject));
		}

		return moveList;
	}
	
	public boolean doMove(IMove move){
		Cloner cloner = new Cloner();
		IMoveable moveable = cloner.deepClone(move.getMoveable());
		if(moveableGameBoardObjects.remove(moveable)){
			// Zug auf gameBoardObject übertragen und Kollisionskarte aktualisieren
			try {
				moveable.move(move.getDistance());
			
				RushHourCollisionDetector collisionDetector = new RushHourCollisionDetector(collisionMap);
				collisionDetector.doMoveWithoutCheck(move);
				this.collisionMap = collisionDetector.getCollisionMap();
			} catch (IllegalMoveException e) {
				return false;
			}
			
			moveableGameBoardObjects.add((IGameBoardObject)moveable);
			
			if(players.remove(move.getMoveable())){
				players.add((IPlayer)moveable);			
			}
			
			return true;
		}
		
		return false;		
	}

	public boolean isSolutionState(){
		for (IPlayer player : players) {
			if(!player.reachedDestination()){
				return false;
			}
		}
		return true;
	}
	
	public RushHourSolverState getPreviousState() {
		return previousState;
	}

	public Boolean[][] getCollisionMap() {
		return collisionMap;
	}

	public Set<IGameBoardObject> getMoveableGameBoardObjects() {
		return moveableGameBoardObjects;
	}

	public Set<IPlayer> getPlayers() {
		return players;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(collisionMap);
		result = prime
				* result
				+ ((moveableGameBoardObjects == null) ? 0
						: moveableGameBoardObjects.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RushHourSolverState other = (RushHourSolverState) obj;
		if (!Arrays.deepEquals(collisionMap, other.collisionMap))
			return false;
		if (moveableGameBoardObjects == null) {
			if (other.moveableGameBoardObjects != null)
				return false;
		} else if (!moveableGameBoardObjects
				.equals(other.moveableGameBoardObjects))
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		return true;
	}

}