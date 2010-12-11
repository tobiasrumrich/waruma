package com.googlecode.waruma.rushhour.framework;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public interface IMoveable {
	public void move(int distance) throws IllegalMoveException;
	public void checkMove(int distance) throws IllegalMoveException;
}
