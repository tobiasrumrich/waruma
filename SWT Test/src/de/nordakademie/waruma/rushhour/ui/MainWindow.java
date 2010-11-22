package de.nordakademie.waruma.rushhour.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class MainWindow {

	protected Shell shlRushHour;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlRushHour.open();
		shlRushHour.layout();
		while (!shlRushHour.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlRushHour = new Shell();
		shlRushHour.setSize(450, 300);
		shlRushHour.setText("Rush Hour - WaRuMa 2010");
		shlRushHour.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(shlRushHour, SWT.BAR);
		shlRushHour.setMenuBar(menu);
		
		MenuItem mntmRushhour = new MenuItem(menu, SWT.CASCADE);
		mntmRushhour.setText("Datei");
		
		Menu menu_1 = new Menu(mntmRushhour);
		mntmRushhour.setMenu(menu_1);
		
		MenuItem mntmSpielLaden = new MenuItem(menu_1, SWT.NONE);
		mntmSpielLaden.setText("Spiel Laden");
		
		MenuItem mntmSpielSpeichern = new MenuItem(menu_1, SWT.NONE);
		mntmSpielSpeichern.setText("Spiel Speichern");

	}
}
