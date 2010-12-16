package com.googlecode.waruma.rushhour.ui;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.googlecode.waruma.rushhour.game.RushHourGameplayControler;

/**
 * Thread that periodically updates the carStorage of an
 * IUpdateableCarStorageOwner object by calling the car2go web interface
 * 
 * @author Moehring, Rumrich
 * 
 */
public class TimeUpdaterThread extends Thread {
	public TimeUpdaterThread() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (true) {
				try {
						System.out.println(Display.getDefault());
						//Display.getDefault().notifyAll();
						sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

		}
	}
}
