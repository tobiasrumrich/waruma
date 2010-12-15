package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.googlecode.waruma.rushhour.framework.Orientation;

class GameplayCarMouseListener implements MouseListener {

	final RushHour mainWindow;
	CarWidget observedCar;
	boolean moveSuccessful;

	public GameplayCarMouseListener(RushHour rushHour,
			CarWidget observedCar) {
		super();
		mainWindow = rushHour;
		this.observedCar = observedCar;
	}

	MouseMoveListener mouseMoveListener = new MouseMoveListener() {
		public void mouseMove(MouseEvent arg0) {
			// BEGIN CageControl
			int neuesX = observedCar.getLocation().x + arg0.x - clickX;
			int neuesY = observedCar.getLocation().y + arg0.y - clickY;

			int boardWidth = mainWindow.abstractGameBoardWidget.getBounds().width;
			int boardHeight = mainWindow.abstractGameBoardWidget.getBounds().height;
			int boardX = mainWindow.abstractGameBoardWidget.getLocation().x
					+ mainWindow.mainComposite.getLocation().x;
			int boardY = mainWindow.abstractGameBoardWidget.getLocation().y
					+ mainWindow.mainComposite.getLocation().y;

			if (neuesX > (boardWidth + boardX - observedCar.getBounds().width))
				neuesX = boardWidth + boardX - observedCar.getBounds().width;

			if (neuesX < minValue) {
				neuesX = minValue;
			}
			
			if(neuesY < minValue){
				neuesY = minValue;
			}

			if (neuesY > (boardY + boardHeight - observedCar.getBounds().height))
				neuesY = (boardY + boardHeight - observedCar.getBounds().height);

			if (neuesX > maxValue) {
				neuesX = maxValue;
			}
			
			if(neuesY > maxValue){
				neuesY = maxValue;
			}
			
			
			if(observedCar.isLocked)
				observedCar.setLocation(observedCar.getLocation());
			else if (observedCar.isLockX())
				observedCar.setLocation(observedCar.getLocation().x, neuesY);
			else if (observedCar.isLockY())
				observedCar.setLocation(neuesX, observedCar.getLocation().y);
			else
				observedCar.setLocation(neuesX, neuesY);
			
			

			// BEGIN FieldControl
			int currentX = observedCar.getLocation().x;
			int currentY = observedCar.getLocation().y;
			int gameBoardX = mainWindow.abstractGameBoardWidget.getLocation().x
					+ mainWindow.mainComposite.getLocation().x;

			int gameBoardY = mainWindow.abstractGameBoardWidget.getLocation().y
					+ mainWindow.mainComposite.getLocation().y;

			int fieldSizeWidth = mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().x;
			int fieldSizeHeight = mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().y;
			int posX = (currentX - gameBoardX + (mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().x / 2)) / fieldSizeWidth;
			int posY = (currentY - gameBoardY + (mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().y / 2)) / fieldSizeHeight;
			observedCar.setPositionOnGameBoard(new Point(posX, posY));
			// END Field Control

		}

	};

	int clickX;
	int clickY;

	private int minValue;
	private int maxValue;

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

		Rectangle moveBoundries = mainWindow.gameplayControler
				.getMoveRange(observedCar.gameObject);

		int boardX = mainWindow.abstractGameBoardWidget.getLocation().x
				+ mainWindow.mainComposite.getLocation().x;
		int boardY = mainWindow.abstractGameBoardWidget.getLocation().y
				+ mainWindow.mainComposite.getLocation().y;

		if (observedCar.getOrientation() == Orientation.NORTH) {
			maxValue = boardY
					+ moveBoundries.y
					* mainWindow.abstractGameBoardWidget.getCurrentFieldSize().y;
			minValue = maxValue
					- (moveBoundries.width * mainWindow.abstractGameBoardWidget
							.getCurrentFieldSize().y);
			maxValue -= mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().y;
		}

		if (observedCar.getOrientation() == Orientation.SOUTH) {
			minValue = boardY
					+ moveBoundries.y
					* mainWindow.abstractGameBoardWidget.getCurrentFieldSize().y;
			maxValue = minValue
					+ (moveBoundries.width * mainWindow.abstractGameBoardWidget
							.getCurrentFieldSize().y);
			maxValue -= mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().y;
		}

		if (observedCar.getOrientation() == Orientation.EAST) {
			minValue = boardX
					+ moveBoundries.x
					* mainWindow.abstractGameBoardWidget.getCurrentFieldSize().x;
			maxValue = minValue
					+ (moveBoundries.width * mainWindow.abstractGameBoardWidget
							.getCurrentFieldSize().x);
			maxValue -= mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().x;
		}

		if (observedCar.getOrientation() == Orientation.WEST) {
			maxValue = boardX
					+ moveBoundries.x
					* mainWindow.abstractGameBoardWidget.getCurrentFieldSize().x;
			minValue = maxValue
					- (moveBoundries.width * mainWindow.abstractGameBoardWidget
							.getCurrentFieldSize().x);
			maxValue -= mainWindow.abstractGameBoardWidget
			.getCurrentFieldSize().x;
		}

		observedCar.addMouseMoveListener(mouseMoveListener);

		for (CarWidget car : mainWindow.carPool) {
			if (!car.equals(observedCar))
				car.moveBelow(observedCar);
		}

	}
}