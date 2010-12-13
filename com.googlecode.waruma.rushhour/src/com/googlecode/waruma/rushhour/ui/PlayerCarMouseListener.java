package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

public class PlayerCarMouseListener extends CarMouseListener {

	
	
	public PlayerCarMouseListener(RushHour rushHour,
			AbstractCarWidget observedCar) {
		super(rushHour, observedCar);	
	}
	
	@Override
	public void mouseUp(MouseEvent e) {
		super.mouseUp(e);
		Point goalField = mainWindow.abstractGameBoardWidget.getGoalField();
		observedCar;
		
		if(goalField != null){
			mainWindow.abstractGameBoardWidget.setHighlight(goalField);
		}
	}
	
	@Override
	public void mouseDown(MouseEvent e) {
		super.mouseDown(e);	
		Point goalField = mainWindow.abstractGameBoardWidget.getGoalField();
		if(goalField != null){
			mainWindow.abstractGameBoardWidget.removeHighlight(goalField);
		}
	}

}
