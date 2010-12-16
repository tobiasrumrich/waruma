package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
import java.io.Serializable;

/**
 * Implementiert die Basisfunktionalität zur Realisierung des
 * {@link IGameBoardObject} Interface
 * 
 * @author Tobias Rumrich
 */
public abstract class AbstractGameBoardObject implements IGameBoardObject,
		Serializable {
	private static final long serialVersionUID = -5260463490389698394L;
	private Boolean[][] collisionMap;
	private Point position;
	private Orientation orientation;

	/**
	 * Erstellt ein neues AbstractGameBoardObject und setzt die angegebenen
	 * Variablen
	 * 
	 * @param collisionMap
	 * @param position
	 * @param orientation
	 */
	public AbstractGameBoardObject(Boolean[][] collisionMap, Point position,
			Orientation orientation) {
		this.collisionMap = collisionMap;
		this.position = position;
		this.orientation = orientation;
	}

	/*
	 * Weitestgehend die von Eclipse generierte Equals-Methode mit geringen
	 * Anpassungen (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractGameBoardObject other = (AbstractGameBoardObject) obj;
		if (collisionMap.length != other.collisionMap.length) {
			return false;
		}
		if (orientation != other.orientation) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if ((position.x != other.position.x)
				|| (position.y != other.position.y)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.IGameBoardObject#getCollisionMap
	 * ()
	 */
	@Override
	public Boolean[][] getCollisionMap() {
		return collisionMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.IGameBoardObject#getOrientation
	 * ()
	 */
	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.IGameBoardObject#getPosition()
	 */
	@Override
	public Point getPosition() {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 20533;
		int result = 1;
		result = prime * result + collisionMap.length;
		result = prime * result
				+ ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + ((position == null) ? 0 : position.x);
		result = prime * result + ((position == null) ? 0 : position.y);

		return result;
	}

	public void setCollisionMap(Boolean[][] collisionMap) {
		this.collisionMap = collisionMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.IGameBoardObject#setOrientation
	 * (com.googlecode.waruma.rushhour.framework.Orientation)
	 */
	@Override
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.IGameBoardObject#setPosition
	 * (java.awt.Point)
	 */
	@Override
	public void setPosition(Point position) {
		this.position = position;
	}

}