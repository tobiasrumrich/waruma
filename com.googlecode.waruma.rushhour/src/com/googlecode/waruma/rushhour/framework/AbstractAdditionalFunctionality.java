package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
import java.io.Serializable;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

/**
 * Implementiert die Decorator-Durchreichung für Zusatzfunktionen von
 * beweglichen Spielbrettobjekten
 * 
 * @author Fabian Malinowski
 */
public abstract class AbstractAdditionalFunctionality extends AbstractMoveable
		implements Serializable {
	private static final long serialVersionUID = 2028991516326549760L;
	public AbstractMoveable abstractMoveable;

	/**
	 * Erstellt einen neuen AdditionalFunctionality Decorator
	 * 
	 * @param abstractMoveable
	 */
	public AbstractAdditionalFunctionality(AbstractMoveable abstractMoveable) {
		super(new Boolean[0][0], new Point(0, 0), Orientation.NORTH);
		this.abstractMoveable = abstractMoveable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.AbstractMoveable#checkMove(int)
	 */
	@Override
	public void checkMove(int distance) throws IllegalMoveException {
		abstractMoveable.checkMove(distance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject#equals
	 * (java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractAdditionalFunctionality other = (AbstractAdditionalFunctionality) obj;
		if (abstractMoveable == null) {
			if (other.abstractMoveable != null) {
				return false;
			}
		} else if (!abstractMoveable.equals(other.abstractMoveable)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject#
	 * getCollisionMap()
	 */
	@Override
	public Boolean[][] getCollisionMap() {
		return abstractMoveable.getCollisionMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject#
	 * getOrientation()
	 */
	@Override
	public Orientation getOrientation() {
		return abstractMoveable.getOrientation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject#getPosition
	 * ()
	 */
	@Override
	public Point getPosition() {
		return abstractMoveable.getPosition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject#hashCode
	 * ()
	 */
	@Override
	public int hashCode() {
		final int prime = 33889;
		int result = super.hashCode();
		result = prime
				* result
				+ ((abstractMoveable == null) ? 0 : abstractMoveable.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.waruma.rushhour.framework.AbstractMoveable#move(int)
	 */
	@Override
	public void move(int distance) throws IllegalMoveException {
		abstractMoveable.move(distance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject#
	 * setCollisionMap(java.lang.Boolean[][])
	 */
	@Override
	public void setCollisionMap(Boolean[][] collisionMap) {
		abstractMoveable.setCollisionMap(collisionMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject#
	 * setOrientation(com.googlecode.waruma.rushhour.framework.Orientation)
	 */
	@Override
	public void setOrientation(Orientation orientation) {
		abstractMoveable.setOrientation(orientation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject#setPosition
	 * (java.awt.Point)
	 */
	@Override
	public void setPosition(Point position) {
		abstractMoveable.setPosition(position);
	}
}