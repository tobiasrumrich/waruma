package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

import com.googlecode.waruma.rushhour.framework.Orientation;

public class PlayerCarMouseListener extends CarMouseListener {

	public PlayerCarMouseListener(RushHour rushHour,
			AbstractCarWidget observedCar) {
		super(rushHour, observedCar);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		
		Point goalField = mainWindow.abstractGameBoardWidget.getGoalField();
		Point positionOnGameBoard = observedCar.getPositionOnGameBoard();
		Orientation orientation = observedCar.getOrientation();
		Point fieldPoint = null;
		
		int carX = observedCar.getLocation().x + e.x - (mainWindow.abstractGameBoardWidget.getCurrentFieldSize().x / 2);
		int boardBorder = mainWindow.abstractGameBoardWidget.getBounds().x + mainWindow.abstractGameBoardWidget.getBounds().width;
		
		if (carX < boardBorder) {
			
			
			switch (orientation) {
			case EAST:
				fieldPoint = new Point(
						mainWindow.abstractGameBoardWidget.getBreite() - 1,
						positionOnGameBoard.y);
				break;
			case WEST:
				fieldPoint = new Point(0, positionOnGameBoard.y);
				break;
			case NORTH:
				fieldPoint = new Point(positionOnGameBoard.x, 0);
				break;
			case SOUTH:
				fieldPoint = new Point(positionOnGameBoard.x,
						mainWindow.abstractGameBoardWidget.getHoehe() - 1);
				break;
			}

			mainWindow.abstractGameBoardWidget.setHighlight(fieldPoint);
			mainWindow.abstractGameBoardWidget.setGoalField(fieldPoint);
		}
		
		super.mouseUp(e);
	}

	@Override
	public void mouseDown(MouseEvent e) {
		super.mouseDown(e);
		Point goalField = mainWindow.abstractGameBoardWidget.getGoalField();
		if (goalField != null) {
			mainWindow.abstractGameBoardWidget.removeHighlight(goalField);
		}
	}

}
