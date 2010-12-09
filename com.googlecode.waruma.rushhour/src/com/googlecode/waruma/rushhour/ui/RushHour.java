package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.googlecode.waruma.rushhour.framework.Orientation;
import com.swtdesigner.SWTResourceManager;

public class RushHour {

	protected Shell shell;
	private Label lblTime;
	private Label lblZeit;
	private AbstractGameBoardWidget abstractGameBoardWidget;
	private Label lblDebug;
	private Label lblDebug2;
	private TabFolder tabFolder;
	private AbstractCarWidget abstractCarWidget;

	/**
	 * Launch the application.
	 * 
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

		shell.setSize(926, 549);
		shell.setText("RushHour by WARUMa");
		shell.setLayout(null);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmStart = new MenuItem(menu, SWT.CASCADE);
		mntmStart.setText("Datei");

		Menu menu_1 = new Menu(mntmStart);
		mntmStart.setMenu(menu_1);

		MenuItem mntmSpielLaden = new MenuItem(menu_1, SWT.NONE);
		mntmSpielLaden.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setText("Open RushHour Game");
				// fileDialog.setFilterPath("C:/");
				String[] filterExt = { "*.ser", "*.rushhour" };
				fileDialog.setFilterExtensions(filterExt);
				String selected = fileDialog.open();
				System.out.println(selected);
			}
		});
		mntmSpielLaden.setText("Spiel laden");

		MenuItem mntmSpielSpeichern = new MenuItem(menu_1, SWT.NONE);
		mntmSpielSpeichern.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
				fileDialog.setText("Save RushHour Game");
				// fileDialog.setFilterPath("C:/");
				String[] filterExt = { "*.ser", "*.rushhour" };
				fileDialog.setFilterExtensions(filterExt);
				String selected = fileDialog.open();
				System.out.println(selected);
			}
		});
		mntmSpielSpeichern.setText("Spiel speichern");

		MenuItem mntmProgrammBeenden = new MenuItem(menu_1, SWT.NONE);
		mntmProgrammBeenden.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shell, SWT.YES | SWT.NO
						| SWT.ICON_QUESTION);
				messageBox
						.setMessage("Möchten Sie diese Anwendung wirklich beenden?");
				int open = messageBox.open();
				if (open == SWT.YES) {
					shell.dispose();
				}
			}
		});
		mntmProgrammBeenden.setText("Programm beenden");

		MenuItem mntmber = new MenuItem(menu, SWT.CASCADE);
		mntmber.setText("?");

		Menu menu_2 = new Menu(mntmber);
		mntmber.setMenu(menu_2);

		MenuItem mntmberDasProgramm = new MenuItem(menu_2, SWT.NONE);
		mntmberDasProgramm.setText("\u00DCber");

		abstractCarWidget = new AbstractCarWidget(shell, SWT.NONE);
		abstractCarWidget.setLocation(763, 276);
		abstractCarWidget.addMouseListener(new MouseListener() {
			MouseMoveListener mouseMoveListener = new MouseMoveListener() {
				public void mouseMove(MouseEvent arg0) {
					// TODO Auto-generated method stub
					// Point displayPoint =
					// abstractCarWidget.toDisplay(arg0.x,arg0.y);

					int neuesX = abstractCarWidget.getLocation().x + arg0.x
							- clickX;
					int neuesY = abstractCarWidget.getLocation().y + arg0.y
							- clickY;

					if (neuesX > (abstractGameBoardWidget.getBounds().width - abstractCarWidget
							.getBounds().width)) {
						neuesX = abstractGameBoardWidget.getBounds().width
								- abstractCarWidget.getBounds().width;
					}
					if (neuesX < abstractGameBoardWidget.getBounds().x) {
						neuesX = abstractGameBoardWidget.getBounds().x;
					}

					if (neuesY > (abstractGameBoardWidget.getBounds().height - abstractCarWidget
							.getBounds().height)) {
						neuesY = abstractGameBoardWidget.getBounds().height
								- abstractCarWidget.getBounds().height;
					}
					if (neuesY < abstractGameBoardWidget.getBounds().y) {
						neuesY = abstractGameBoardWidget.getBounds().y;
					}

					abstractCarWidget.setLocation(neuesX, neuesY);

					// System.out.println("MouseMove Coordinates: X=" +
					// displayPoint.x + ";Y="+ displayPoint.y);
					// System.out.println("Unmodified Coordinates: X=" + arg0.x
					// + ";Y="+ arg0.y);

				}

			};

			int clickX;
			int clickY;
			int xAlt = 0;
			int yAlt = 0;

			@Override
			public void mouseUp(MouseEvent e) {
				abstractCarWidget.removeMouseMoveListener(mouseMoveListener);
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				clickX = arg0.x;
				clickY = arg0.y;
				//System.out.println("MouseDown Coordinates: X=" + clickX + ";Y="+ clickY);
				abstractCarWidget.addMouseMoveListener(mouseMoveListener);

				if (arg0.button == 3) {
					System.out.println("ALTE ORIENTATION = "
							+ abstractCarWidget.getOrientation());
					switch (abstractCarWidget.getOrientation()) {
					case NORTH:
						abstractCarWidget.changeOrientation(Orientation.EAST,abstractGameBoardWidget.getCurrentFieldSize());
						break;
					case EAST:
						abstractCarWidget.changeOrientation(Orientation.SOUTH,abstractGameBoardWidget.getCurrentFieldSize());
						break;
					case SOUTH:
						abstractCarWidget.changeOrientation(Orientation.WEST,abstractGameBoardWidget.getCurrentFieldSize());
						break;
					case WEST:
						abstractCarWidget.changeOrientation(Orientation.NORTH,abstractGameBoardWidget.getCurrentFieldSize());
						break;
					}

				}

			}
		});

		tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// System.out.println(tabFolder.getSelectionIndex());
				if (tabFolder.getSelectionIndex() == 1) {
					abstractCarWidget.setVisible(true);
				} else {
					abstractCarWidget.setVisible(false);
				}
			}
		});

		tabFolder.setBounds(0, 0, 812, 503);

		TabItem tbtmDesigner = new TabItem(tabFolder, SWT.NONE);
		tbtmDesigner.setText("Designer");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmDesigner.setControl(composite);
		composite.setLayout(new GridLayout(2, false));

		Group group_2 = new Group(composite, SWT.NONE);
		group_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Group group_3 = new Group(composite, SWT.NONE);
		group_3.setLayout(new GridLayout(2, false));
		group_3.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true, 1,
				1));
		new Label(group_3, SWT.NONE);
		new Label(group_3, SWT.NONE);

		Label lblLnge = new Label(group_3, SWT.NONE);
		lblLnge.setAlignment(SWT.RIGHT);
		lblLnge.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblLnge.setText("L\u00E4nge");

		Combo combo = new Combo(group_3, SWT.READ_ONLY);
		combo.setItems(new String[] { "Auto", "Truck" });
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));

		Label lblFarbe = new Label(group_3, SWT.NONE);
		lblFarbe.setAlignment(SWT.RIGHT);
		lblFarbe.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblFarbe.setText("Farbe");

		Combo combo_1 = new Combo(group_3, SWT.READ_ONLY);
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblOrientierung = new Label(group_3, SWT.NONE);
		lblOrientierung.setAlignment(SWT.RIGHT);
		lblOrientierung.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblOrientierung.setText("Orientierung");

		Combo combo_2 = new Combo(group_3, SWT.READ_ONLY);
		combo_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblLenkradschloss = new Label(group_3, SWT.NONE);
		lblLenkradschloss.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblLenkradschloss.setText("Lenkradschloss");

		Button btnLenkradschloss = new Button(group_3, SWT.CHECK);

		Label lblSpieler = new Label(group_3, SWT.NONE);
		lblSpieler.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblSpieler.setText("Spielerauto");

		Button btnSpieler = new Button(group_3, SWT.CHECK);

		TabItem tbtmSpielen = new TabItem(tabFolder, SWT.NONE);
		tbtmSpielen.setText("Spielen");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmSpielen.setControl(composite_1);
		composite_1.setLayout(new GridLayout(2, false));

		Group grpPlayGameBoard = new Group(composite_1, SWT.NONE);
		grpPlayGameBoard.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpPlayGameBoard = new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1);

		grpPlayGameBoard.setLayoutData(gd_grpPlayGameBoard);

		abstractGameBoardWidget = new AbstractGameBoardWidget(grpPlayGameBoard,
				SWT.NONE, 9, 6);

		abstractGameBoardWidget.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				// lblDebug.setText(
				// abstractGameBoardWidget.toDisplay(0,abstractGameBoardWidget.getSize().y).toString());
				// lblDebug2.setText(abstractGameBoardWidget.toDisplay(0,abstractGameBoardWidget.getSize().x).toString());
				// lblDebug2.setText(abstractGameBoardWidget.getCurrentFieldSize().toString());
			}
		});
		gd_grpPlayGameBoard.minimumHeight = abstractGameBoardWidget
				.getMinHeight();
		gd_grpPlayGameBoard.minimumWidth = abstractGameBoardWidget
				.getMinWidth();

		Group grpSpielkontrolle = new Group(composite_1, SWT.NONE);
		grpSpielkontrolle.setLayout(new GridLayout(2, false));
		GridData gd_grpSpielkontrolle = new GridData(SWT.LEFT, SWT.FILL, false,
				true, 1, 1);
		grpSpielkontrolle.setLayoutData(gd_grpSpielkontrolle);
		gd_grpSpielkontrolle.minimumWidth = 300;

		Label lblSpieldaten = new Label(grpSpielkontrolle, SWT.NONE);
		GridData gd_lblSpieldaten = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1);
		gd_lblSpieldaten.widthHint = 138;
		lblSpieldaten.setLayoutData(gd_lblSpieldaten);
		lblSpieldaten.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblSpieldaten.setText("Spieldaten");

		lblZeit = new Label(grpSpielkontrolle, SWT.NONE);
		lblZeit.setText("Zeit");

		lblTime = new Label(grpSpielkontrolle, SWT.NONE);
		lblTime.setText("02:35");

		Label lblZge = new Label(grpSpielkontrolle, SWT.NONE);
		GridData gd_lblZge = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_lblZge.heightHint = 13;
		lblZge.setLayoutData(gd_lblZge);
		lblZge.setText("Z\u00FCge");

		Label lblMoves = new Label(grpSpielkontrolle, SWT.NONE);
		lblMoves.setText("15");

		lblDebug = new Label(grpSpielkontrolle, SWT.NONE);
		GridData gd_lblDebug = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				2, 1);
		gd_lblDebug.heightHint = 15;
		gd_lblDebug.widthHint = 142;
		lblDebug.setLayoutData(gd_lblDebug);
		lblDebug.setText("New Label");

		lblDebug2 = new Label(grpSpielkontrolle, SWT.NONE);
		GridData gd_lblDebug2 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1);
		gd_lblDebug2.widthHint = 143;
		lblDebug2.setLayoutData(gd_lblDebug2);
		lblDebug2.setText("New Label");
		new Label(grpSpielkontrolle, SWT.NONE);
		new Label(grpSpielkontrolle, SWT.NONE);

		Button btnLsen = new Button(grpSpielkontrolle, SWT.NONE);
		btnLsen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				2, 1));
		btnLsen.setText("L\u00F6sung");

		Button button = new Button(grpSpielkontrolle, SWT.NONE);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		button.setText("<<");

		Button button_1 = new Button(grpSpielkontrolle, SWT.NONE);
		button_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		button_1.setEnabled(false);
		button_1.setText(">>");
		new Label(grpSpielkontrolle, SWT.NONE);
		new Label(grpSpielkontrolle, SWT.NONE);

		new Label(grpSpielkontrolle, SWT.NONE);

		Point point = new Point(abstractGameBoardWidget.getMinWidth()
				+ gd_grpSpielkontrolle.minimumWidth + 30,
				abstractGameBoardWidget.getMinHeight() + 186);
		shell.setMinimumSize(point);

		shell.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				if (tabFolder != null) {
					// TabFolder resizen
					tabFolder.setBounds(tabFolder.getBounds().x,
							tabFolder.getBounds().y,
							shell.getBounds().width - 10,
							shell.getBounds().height - 50);

					// Car resizen
					// int x = abstractGameBoardWidget.getCurrentFieldSize().x;
					// int y = 2 *
					// abstractGameBoardWidget.getCurrentFieldSize().y;
					// System.out.println("Proposed Car Size (resize of window): X="+x+";Y="+y);
					if (abstractGameBoardWidget.getCurrentFieldSize().x > 0
							&& abstractGameBoardWidget.getCurrentFieldSize().y > 0)
						abstractCarWidget.setSize(abstractGameBoardWidget
								.getCurrentFieldSize());


				}
			}
		});

	}
}
