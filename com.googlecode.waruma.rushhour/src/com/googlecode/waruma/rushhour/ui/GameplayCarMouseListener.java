package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;

import com.googlecode.waruma.rushhour.framework.Orientation;

class GameplayCarMouseListener implements MouseListener {

	final RushHour mainWindow;
	AbstractCarWidget observedCar;
	boolean moveSuccessful;

	public GameplayCarMouseListener(RushHour rushHour,
			AbstractCarWidget observedCar) {
		super();
		mainWindow = rushHour;
		this.observedCar = observedCar;
	}

	MouseMoveListener mouseMoveListener = new MouseMoveListener() {
		public void mouseMove(MouseEvent arg0) {
			// BEGIN CageControl
			int neuesX = observedCar.getLocation().x + arg0.x - clickX;
			int neuesY = observedCar.getLocation().y + arg0.y - clickY;

			int boardWidth = GameplayCarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getBounds().width;
			int boardHeight = GameplayCarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getBounds().height;
			int boardX = GameplayCarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getLocation().x
					+ GameplayCarMouseListener.this.mainWindow.mainComposite
							.getLocation().x;
			int boardY = GameplayCarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getLocation().y
					+ GameplayCarMouseListener.this.mainWindow.mainComposite
							.getLocation().y;

			if (neuesX > (boardWidth + boardX - observedCar.getBounds().width))
				neuesX = boardWidth + boardX - observedCar.getBounds().width;

			if (neuesX <= boardX)
				neuesX = boardX;

			if (neuesY > (boardY + boardHeight - observedCar.getBounds().height))
				neuesY = (boardY + boardHeight - observedCar.getBounds().height);

			if (neuesY <= boardY)
				neuesY = boardY;

			if (observedCar.isLockX())
				observedCar.setLocation(observedCar.getLocation().x, neuesY);
			else if (observedCar.isLockY())
				observedCar.setLocation(neuesX, observedCar.getLocation().y);
			else
				observedCar.setLocation(neuesX, neuesY);

			// BEGIN FieldControl
			int currentX = observedCar.getLocation().x;
			int currentY = observedCar.getLocation().y;
			int gameBoardX = GameplayCarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getLocation().x
					+ GameplayCarMouseListener.this.mainWindow.mainComposite
							.getLocation().x;

			int gameBoardY = GameplayCarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getLocation().y
					+ GameplayCarMouseListener.this.mainWindow.mainComposite
							.getLocation().y;

			int fieldSizeWidth = GameplayCarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().x;
			int fieldSizeHeight = GameplayCarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().y;
			int posX = (currentX - gameBoardX + (GameplayCarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().x / 2)) / fieldSizeWidth;
			int posY = (currentY - gameBoardY + (GameplayCarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().y / 2)) / fieldSizeHeight;
			observedCar.setPositionOnGameBoard(new Point(posX, posY));
			GameplayCarMouseListener.this.mainWindow.lblDebug.setText(posX
					+ ":" + posY);
			// END Field Control

		}

	};

	int clickX;
	int clickY;

	@Override
	public void mouseUp(MouseEvent e) {
		observedCar.removeMouseMoveListener(mouseMoveListener);

		mainWindow.abstractGameBoardWidget.repositionCarOnBoard(observedCar);

		observedCar.moveCar();

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

		// observedCar.moveAbove(mainWindow.mainComposite);
		for (AbstractCarWidget car : mainWindow.carPool) {
			if (!car.equals(observedCar))
				car.moveBelow(observedCar);
		}

	}
}