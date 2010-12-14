package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;

import com.googlecode.waruma.rushhour.framework.Orientation;

class CarMouseListener implements MouseListener {

    final RushHour mainWindow;
	AbstractCarWidget observedCar;


	public CarMouseListener(RushHour rushHour, AbstractCarWidget observedCar) {
		super();
		mainWindow = rushHour;
		this.observedCar = observedCar;
	}

	MouseMoveListener mouseMoveListener = new MouseMoveListener() {
		public void mouseMove(MouseEvent arg0) {
			// BEGIN CageControl
			int neuesX = observedCar.getLocation().x + arg0.x - clickX;
			int neuesY = observedCar.getLocation().y + arg0.y - clickY;

			int boardWidth = CarMouseListener.this.mainWindow.abstractGameBoardWidget.getBounds().width;
			int boardHeight = CarMouseListener.this.mainWindow.abstractGameBoardWidget.getBounds().height;
			int boardX = CarMouseListener.this.mainWindow.abstractGameBoardWidget.getLocation().x
					+ CarMouseListener.this.mainWindow.mainComposite.getLocation().x;
			int boardY = CarMouseListener.this.mainWindow.abstractGameBoardWidget.getLocation().y
					+ CarMouseListener.this.mainWindow.mainComposite.getLocation().y;

			if (observedCar.isLockedInCage()) {
				if (neuesX > (boardWidth + boardX - observedCar.getBounds().width))
					neuesX = boardWidth + boardX
							- observedCar.getBounds().width;

				if (neuesX <= boardX)
					neuesX = boardX;

				if (neuesY > (boardY + boardHeight - observedCar
						.getBounds().height))
					neuesY = (boardY + boardHeight - observedCar
							.getBounds().height);

				if (neuesY <= boardY)
					neuesY = boardY;

				if (observedCar.isLockX())
					observedCar.setLocation(observedCar.getLocation().x,
							neuesY);
				else if (observedCar.isLockY())
					observedCar.setLocation(neuesX,
							observedCar.getLocation().y);
				else
					observedCar.setLocation(neuesX, neuesY);
				// END CageControl
			} else {
				if (neuesX <= boardX)
					neuesX = boardX;

				if (neuesX > (CarMouseListener.this.mainWindow.shell.getBounds().width
						- observedCar.getBounds().width - 30))
					neuesX = CarMouseListener.this.mainWindow.shell.getBounds().width
							- observedCar.getBounds().width - 30;

				if (neuesY <= boardY)
					neuesY = boardY;

				if (neuesY > (boardY + boardHeight - observedCar
						.getBounds().height))
					neuesY = (boardY + boardHeight - observedCar
							.getBounds().height);
				observedCar.setLocation(neuesX, neuesY);
			}

			// BEGIN FieldControl
			int currentX = observedCar.getLocation().x;
			int currentY = observedCar.getLocation().y;
			int gameBoardX = CarMouseListener.this.mainWindow.abstractGameBoardWidget.getLocation().x
					+ CarMouseListener.this.mainWindow.mainComposite.getLocation().x;

			int gameBoardY = CarMouseListener.this.mainWindow.abstractGameBoardWidget.getLocation().y
					+ CarMouseListener.this.mainWindow.mainComposite.getLocation().y;

			int fieldSizeWidth = CarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().x;
			int fieldSizeHeight = CarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().y;
			int posX = (currentX - gameBoardX + (CarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().x / 2)) / fieldSizeWidth;
			int posY = (currentY - gameBoardY + (CarMouseListener.this.mainWindow.abstractGameBoardWidget
					.getCurrentFieldSize().y / 2)) / fieldSizeHeight;
			observedCar.setPositionOnGameBoard(new Point(posX, posY));
			CarMouseListener.this.mainWindow.lblDebug.setText(posX + ":" + posY);
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
		
		if (neuesX < boardX + mainWindow.abstractGameBoardWidget.getBounds().width
				- (mainWindow.abstractGameBoardWidget.getCurrentFieldSize().x / 2)) {
			mainWindow.abstractGameBoardWidget.repositionCarOnBoard(observedCar);
			
			if(observedCar.knownInController){
				observedCar.moveToPositionControler();
			} else {
				observedCar.addToBoardControler();		
				observedCar.knownInController = true;
			}
			
			
		} else {
			System.out.println("TIME TO DISPOSE");
			observedCar.dispose();
			mainWindow.carPool.remove(observedCar);
			// Remove
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

		if (arg0.button == 3) {
			switch (observedCar.getOrientation()) {
			case NORTH:
				observedCar.changeOrientation(Orientation.EAST,
						mainWindow.abstractGameBoardWidget.getCurrentFieldSize());
				break;
			case EAST:
				observedCar.changeOrientation(Orientation.SOUTH,
						mainWindow.abstractGameBoardWidget.getCurrentFieldSize());
				break;
			case SOUTH:
				observedCar.changeOrientation(Orientation.WEST,
						mainWindow.abstractGameBoardWidget.getCurrentFieldSize());
				break;
			case WEST:
				observedCar.changeOrientation(Orientation.NORTH,
						mainWindow.abstractGameBoardWidget.getCurrentFieldSize());
				break;
			}
		}
		
	
		observedCar.moveAbove(mainWindow.mainComposite);
		for (AbstractCarWidget car : mainWindow.carPool) {
			if(!car.equals(observedCar))
				car.moveBelow(observedCar);
		}
		

	}
}