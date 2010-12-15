package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

import com.googlecode.waruma.rushhour.framework.Orientation;

public class DesignerPlayerCarMouseListener extends DesignerCarMouseListener {
	private RushHour mainWindow;
	private Point fieldPoint;

	public DesignerPlayerCarMouseListener(RushHour rushHour,
			CarWidget observedCar) {
		super(rushHour, observedCar);
		this.mainWindow = rushHour;
		this.mainWindow.abstractDesignerWidget.removePlayerCarToCombobox();
		this.fieldPoint = null;
	}

	@Override
	public void mouseUp(MouseEvent e) {
		observedCar.removeMouseMoveListener(mouseMoveListener);
		
		Point positionOnGameBoard = observedCar.getPositionOnGameBoard();
		Orientation orientation = observedCar.getOrientation();

		int carX = observedCar.getLocation().x
				+ e.x
				- (mainWindow.abstractGameBoardWidget.getCurrentFieldSize().x / 2);
		int boardBorder = mainWindow.abstractGameBoardWidget.getBounds().x
				+ mainWindow.abstractGameBoardWidget.getBounds().width;

		moveSuccessful = false;
		if (observedCar.knownInController) {
			moveSuccessful = observedCar.moveToPositionController();
		}

		if (carX < boardBorder) {
			mainWindow.abstractGameBoardWidget.repositionCarOnBoard(observedCar);

			if (moveSuccessful || !observedCar.knownInController) {
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
			}

			mainWindow.abstractGameBoardWidget.setHighlight(fieldPoint);
			mainWindow.abstractGameBoardWidget.setGoalField(fieldPoint);
			mainWindow.boardCreationControler.changeDestination(fieldPoint);

		} else {
			observedCar.removeFromGameBoard();
			mainWindow.abstractDesignerWidget.addPlayerCarToCombobox();
			return;
		}

		if (!observedCar.knownInController) {
			observedCar.addToBoardControler();
			observedCar.knownInController = true;
		}
	}



	@Override
	public void mouseDown(MouseEvent e) {
		super.mouseDown(e);
		Point goalField = mainWindow.abstractGameBoardWidget.getGoalField();
		if (goalField != null && observedCar.getPositionOnGameBoard() != null) {
			mainWindow.abstractGameBoardWidget.removeHighlight(goalField);
		}

	}

}
