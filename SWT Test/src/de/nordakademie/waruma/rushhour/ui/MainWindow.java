package de.nordakademie.waruma.rushhour.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import swing2swt.layout.BorderLayout;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MainWindow {

	private final class Button1Click extends MouseAdapter {
		@Override
		public void mouseUp(MouseEvent e) {
			mntmSpielSpeichern.setText("bla");
		}
	}

	protected Shell shlRushHour;
	private MenuItem mntmSpielSpeichern;

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
		menu.setLocation(new Point(0, 0));
		shlRushHour.setMenuBar(menu);
		
		MenuItem mntmRushhour = new MenuItem(menu, SWT.CASCADE);
		mntmRushhour.setText("Datei");
		
		Menu menu_1 = new Menu(mntmRushhour);
		menu_1.setLocation(new Point(0, 0));
		mntmRushhour.setMenu(menu_1);
		
		MenuItem mntmSpielLaden = new MenuItem(menu_1, SWT.NONE);
		mntmSpielLaden.setText("Spiel Laden");
		
		mntmSpielSpeichern = new MenuItem(menu_1, SWT.NONE);
		mntmSpielSpeichern.setText("Spiel Speichern");
		
		TabFolder tabFolder = new TabFolder(shlRushHour, SWT.NONE);
		
		TabItem tbtmSpielErstellen = new TabItem(tabFolder, SWT.NONE);
		tbtmSpielErstellen.setText("Spiel erstellen");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmSpielErstellen.setControl(composite);
		composite.setLayout(new BorderLayout(0, 0));
		
		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(BorderLayout.CENTER);
		label.setText("New Label");
		
		Group grpAutoErstellen = new Group(composite, SWT.NONE);
		grpAutoErstellen.setText("Auto Erstellen");
		grpAutoErstellen.setLayoutData(BorderLayout.EAST);
		
		Combo button = new Combo(grpAutoErstellen, SWT.CHECK);
		button.setBounds(10, 26, 144, 23);
		button.setText("New Button");
		
		Button button_1 = new Button(grpAutoErstellen, SWT.NONE);
		button_1.addMouseListener(new Button1Click());
		button_1.setBounds(22, 89, 75, 25);
		button_1.setText("New Button");
		
		TabItem tbtmSpielen = new TabItem(tabFolder, SWT.NONE);
		tbtmSpielen.setText("Spielen");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmSpielen.setControl(composite_1);
		composite_1.setLayout(new BoxLayout(BoxLayout.X_AXIS));

	}
}
