package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;

class DesignerCarMouseListener implements MouseListener {

	final RushHour mainWindow;
	CarWidget observedCar;
	boolean moveSuccessful;

	public DesignerCarMouseListener(RushHour rushHour, CarWidget observedCar) {
		super();
		mainWindow = rushHour;
		this.observedCar = observedCar;
	}

	MouseMoveListener mouseMoveListener = new MouseMoveListener() {
		@Override
		public void mouseMove(MouseEvent arg0) {
			// BEGIN CageControl
			int neuesX = observedCar.getLocation().x + arg0.x - clickX;
			int neuesY = observedCar.getLocation().y + arg0.y - clickY;

			int boardWidth = DesignerCarMouseListener.this.mainWindow.
			abstractGameBoardWidget
					.getBounds().width;
			int boardHeight = DesignerCarMouseListener.this.mainWindow.
			abstractGameBoardWidget
					.getBounds().height;
			int boardX = DesignerCarMouseListener.this.mainWindow.
			abstractGameBoardWidget
					.getLocation().x
					+ DesignerCarMouseListener.this.mainWindow.
					mainComposite
							.getLocation().x;
			int boardY = DesignerCarMouseListener.this.mainWindow.
			abstractGameBoardWidget
					.getLocation().y
					+ DesignerCarMouseListener.this.mainWindow.mainComposite
							.getLocation().y;

			if (observedCar.isLockedInCage()) {
				if (neuesX > (boardWidth + boardX - 
						observedCar.getBounds().width))
					neuesX = boardWidth + boardX
							- observedCar.getBounds().width;

				if (neuesX <= boardX)
					neuesX = boardX;

				if (neuesY > (boardY + boardHeight - 
						observedCar.getBounds().height))
					neuesY = (boardY + boardHeight - 
							observedCar.getBounds().height);

				if (neuesY <= boardY)
					neuesY = boardY;

				if (observedCar.isLockX())
					observedCar
							.setLocation(observedCar.getLocation().x, neuesY);
				else if (observedCar.isLockY())
					observedCar
							.setLocation(neuesX, observedCar.getLocation().y);
				else
					observedCar.setLocation(neuesX, neuesY);
				// END CageControl
			} else {
				if (neuesX <= boardX)
					neuesX = boardX;

				if (neuesX > (DesignerCarMouseListener.this.mainWindow.shell
						.getBounds().width - observedCar.getBounds().
						width - 30))
					neuesX = DesignerCarMouseListener.this.mainWindow.shell.
					getBounds().width
							- observedCar.getBounds().width - 30;

				if (neuesY <= boardY)
					neuesY = boardY;

				if (neuesY > (boardY + boardHeight - 
						observedCar.getBounds().height))
					neuesY = (boardY + boardHeight - 
							observedCar.getBounds().height);
				observedCar.setLocation(neuesX, neuesY);
			}

			// BEGIN FieldControl
			int currentX = observedCar.getLocation().x;
			int currentY = observedCar.getLocation().y;
			int gameBoardX = DesignerCarMouseListener.this.mainWindow.
			abstractGameBoardWidget
					.getLocation().x
					+ DesignerCarMouseListener.this.mainWindow.mainComposite
							.getLocation().x;

			int gameBoardY = DesignerCarMouseListener.this.mainWindow.
			abstractGameBoardWidget
					.getLocation().y
					+ DesignerCarMouseListener.this.mainWindow.mainComposite
							.getLocation().y;

			int fieldSizeWidth = DesignerCarMouseListener.this.mainWindow.
			abstractGameBoardWidget
					.getCurrentFieldSize().x;
			int fieldSizeHeight = DesignerCarMouseListener.this.mainWindow.
			abstractGameBoardWidget
					.getCurrentFieldSize().y;
			int posX = (currentX - gameBoardX + (DesignerCarMouseListener.this.
					mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().x / 2)) / fieldSizeWidth;
			int posY = (currentY - gameBoardY + (DesignerCarMouseListener.this.
					mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().y / 2)) / fieldSizeHeight;
			observedCar.setPositionOnGameBoard(new Point(posX, posY));
 
			// END Field Control

		}

	};

	int clickX;
	int clickY;

	@Override
	public void mouseUp(MouseEvent e) {
		observedCar.removeMouseMoveListener(mouseMoveListener);
		int boardX = mainWindow.abstractGameBoardWidget.getLocation().x
				+ mainWindow.mainComposite.getLocation().x;
		int neuesX = observedCar.getLocation().x + e.x - clickX;

		if (neuesX < boardX
				+ mainWindow.abstractGameBoardWidget.getBounds().width
				- (mainWindow.abstractGameBoardWidget.getCurrentFieldSize().x 
						/ 2)) {
			mainWindow.abstractGameBoardWidget
					.repositionCarOnBoard(observedCar);

			if (observedCar.knownInController) {
				moveSuccessful = observedCar.moveToPositionController();
			} else {
				observedCar.addToBoardControler();
				observedCar.knownInController = true;
			}
		} else {
			// Remove
			observedCar.removeFromGameBoard();
		}

	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		clickX = arg0.x;
		clickY = arg0.y;

		observedCar.addMouseMoveListener(mouseMoveListener);

		if (observedCar.getPositionOnGameBoard() != null) {
			if (arg0.button == 3) {
				observedCar.rotateToOrientation();
			}
		}

		// observedCar.moveAbove(mainWindow.mainComposite);
		for (CarWidget car : mainWindow.carPool) {
			if (!car.equals(observedCar))
				car.moveBelow(observedCar);
		}

	}
}