package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import com.swtdesigner.SWTResourceManager;

public class RushHour {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RushHour window = new RushHour();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(727, 549);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmStart = new MenuItem(menu, SWT.CASCADE);
		mntmStart.setText("Datei");
		
		Menu menu_1 = new Menu(mntmStart);
		mntmStart.setMenu(menu_1);
		
		MenuItem mntmSpielLaden = new MenuItem(menu_1, SWT.NONE);
		mntmSpielLaden.setText("Spiel laden");
		
		MenuItem mntmSpielSpeichern = new MenuItem(menu_1, SWT.NONE);
		mntmSpielSpeichern.setText("Spiel speichern");
		
		MenuItem mntmProgrammBeenden = new MenuItem(menu_1, SWT.NONE);
		mntmProgrammBeenden.setText("Programm beenden");
		
		MenuItem mntmber = new MenuItem(menu, SWT.CASCADE);
		mntmber.setText("?");
		
		Menu menu_2 = new Menu(mntmber);
		mntmber.setMenu(menu_2);
		
		MenuItem mntmberDasProgramm = new MenuItem(menu_2, SWT.NONE);
		mntmberDasProgramm.setText("\u00DCber");
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		
		TabItem tbtmDesigner = new TabItem(tabFolder, SWT.NONE);
		tbtmDesigner.setText("Designer");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmDesigner.setControl(composite);
		composite.setLayout(new GridLayout(2, false));
		
		Group group_2 = new Group(composite, SWT.NONE);
		group_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group group_3 = new Group(composite, SWT.NONE);
		group_3.setLayout(new GridLayout(2, false));
		group_3.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true, 1, 1));
		new Label(group_3, SWT.NONE);
		new Label(group_3, SWT.NONE);
		
		Label lblLnge = new Label(group_3, SWT.NONE);
		lblLnge.setAlignment(SWT.RIGHT);
		lblLnge.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLnge.setText("L\u00E4nge");
		
		Combo combo = new Combo(group_3, SWT.NONE);
		combo.setItems(new String[] {"Auto", "Truck"});
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblFarbe = new Label(group_3, SWT.NONE);
		lblFarbe.setAlignment(SWT.RIGHT);
		lblFarbe.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFarbe.setText("Farbe");
		
		Combo combo_1 = new Combo(group_3, SWT.NONE);
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOrientierung = new Label(group_3, SWT.NONE);
		lblOrientierung.setAlignment(SWT.RIGHT);
		lblOrientierung.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOrientierung.setText("Orientierung");
		
		Combo combo_2 = new Combo(group_3, SWT.NONE);
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblLenkradschloss = new Label(group_3, SWT.NONE);
		lblLenkradschloss.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLenkradschloss.setText("Lenkradschloss");
		
		Button btnLenkradschloss = new Button(group_3, SWT.CHECK);
		
		Label lblSpieler = new Label(group_3, SWT.NONE);
		lblSpieler.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSpieler.setText("Spielerauto");
		
		Button btnSpieler = new Button(group_3, SWT.CHECK);
		
		TabItem tbtmSpielen = new TabItem(tabFolder, SWT.NONE);
		tbtmSpielen.setText("Spielen");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmSpielen.setControl(composite_1);
		composite_1.setLayout(new GridLayout(2, false));
		
		Group group = new Group(composite_1, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		AbstractGameBoardWidget abstractGameBoardWidget = new AbstractGameBoardWidget(group, SWT.NONE);
		abstractGameBoardWidget.setBounds(113, 94, 365, 45);
		
		Group group_1 = new Group(composite_1, SWT.NONE);
		group_1.setLayout(new GridLayout(2, false));
		group_1.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
		
		Label lblSpieldaten = new Label(group_1, SWT.NONE);
		GridData gd_lblSpieldaten = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_lblSpieldaten.widthHint = 138;
		lblSpieldaten.setLayoutData(gd_lblSpieldaten);
		lblSpieldaten.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblSpieldaten.setText("Spieldaten");
		
		Label lblZeit = new Label(group_1, SWT.NONE);
		lblZeit.setText("Zeit");
		
		Label label = new Label(group_1, SWT.NONE);
		label.setText("02:35");
		
		Label lblZge = new Label(group_1, SWT.NONE);
		lblZge.setText("Z\u00FCge");
		
		Label label_1 = new Label(group_1, SWT.NONE);
		label_1.setText("15");
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		
		Button btnLsen = new Button(group_1, SWT.NONE);
		btnLsen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnLsen.setText("L\u00F6sung");
		
		Button button = new Button(group_1, SWT.NONE);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		button.setText("<<");
		
		Button button_1 = new Button(group_1, SWT.NONE);
		button_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		button_1.setEnabled(false);
		button_1.setText(">>");

	}
}
