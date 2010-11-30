package com.googlecode.waruma.rushhour.framework;

import java.util.List;
import java.util.Stack;

public class GameBoard {

  private ICollisionDetector collisionDetector;

  private List<IGameBoardObject> gameBoardObjects;

  private Stack<IMove> moveHistory;

    public IGameBoardObject myIGameBoardObject;
    public IMoveable myIMoveable;
      public IMove myIMove;
          public ICollisionDetector myICollisionDetector;

  public void move(IMove move) {
  }

  public void addGameBoardObject(IGameBoardObject gameBoardObject) {
  }

  public void newOperation() {
  }

}