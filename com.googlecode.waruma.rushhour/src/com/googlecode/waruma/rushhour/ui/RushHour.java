package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
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
	private Composite cmpSpiel;
	private TabItem tabSpielen;
	private Menu menu;

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

		menu = new Menu(shell, SWT.BAR);
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

		abstractCarWidget = new AbstractCarWidget(shell, 1, 2);
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

					int boardWidth = abstractGameBoardWidget.getBounds().width;
					int boardHeight = abstractGameBoardWidget.getBounds().height;
					int boardX = abstractGameBoardWidget.getLocation().x + cmpSpiel.getLocation().x + tabFolder.getLocation().x + 6;
					int boardY = abstractGameBoardWidget.getLocation().y + cmpSpiel.getLocation().y + tabFolder.getLocation().y + 6;

					if (neuesX > boardWidth + boardX) 
						neuesX = boardWidth + boardX;
					
					if (neuesX <= boardX)
						neuesX = boardX;
					
					if (neuesY > (boardY + boardHeight - abstractCarWidget.getBounds().height-12))
						neuesY = (boardY + boardHeight - abstractCarWidget.getBounds().height-12);

					if (neuesY <= boardY )
						neuesY = boardY;
						
					/*
					if (neuesX > 517) {
						neuesX = boardWidth - abstractCarWidget.getBounds().width;
					}
					
					if (neuesX < abstractGameBoardWidget.getBounds().x - boardX) {
						neuesX = abstractGameBoardWidget.getBounds().x;
					}

					if (neuesY > (boardHeight - abstractCarWidget.getBounds().height)) {
						neuesY = boardHeight
								- abstractCarWidget.getBounds().height;
					}
					if (neuesY < abstractGameBoardWidget.getBounds().y + boardY) {
						neuesY = abstractGameBoardWidget.getBounds().y;
					}*/

					if (abstractCarWidget.isLockX())
						abstractCarWidget.setLocation(
								abstractCarWidget.getLocation().x, neuesY);
					else if (abstractCarWidget.isLockY())
						abstractCarWidget.setLocation(neuesX,
								abstractCarWidget.getLocation().y);
					else
						abstractCarWidget.setLocation(neuesX, neuesY);

				}

			};

			int clickX;
			int clickY;

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
				abstractCarWidget.addMouseMoveListener(mouseMoveListener);

				if (arg0.button == 3) {
					switch (abstractCarWidget.getOrientation()) {
					case NORTH:
						abstractCarWidget.changeOrientation(Orientation.EAST,
								abstractGameBoardWidget.getCurrentFieldSize());
						break;
					case EAST:
						abstractCarWidget.changeOrientation(Orientation.SOUTH,
								abstractGameBoardWidget.getCurrentFieldSize());
						break;
					case SOUTH:
						abstractCarWidget.changeOrientation(Orientation.WEST,
								abstractGameBoardWidget.getCurrentFieldSize());
						break;
					case WEST:
						abstractCarWidget.changeOrientation(Orientation.NORTH,
								abstractGameBoardWidget.getCurrentFieldSize());
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
		
		Composite cmpDesigner = new Composite(tabFolder, SWT.NONE);
		tbtmDesigner.setControl(cmpDesigner);
		cmpDesigner.setLayout(null);

		tabSpielen = new TabItem(tabFolder, SWT.NONE);
		tabSpielen.setText("Spielen");

		cmpSpiel = new Composite(tabFolder, SWT.NONE);
		tabSpielen.setControl(cmpSpiel);
		GridLayout gl_cmpSpiel = new GridLayout(2, false);
		gl_cmpSpiel.marginWidth = 3;
		gl_cmpSpiel.marginTop = 3;
		gl_cmpSpiel.marginRight = 3;
		gl_cmpSpiel.marginLeft = 3;
		gl_cmpSpiel.marginHeight = 3;
		gl_cmpSpiel.marginBottom = 3;
		gl_cmpSpiel.horizontalSpacing = 10;
		cmpSpiel.setLayout(gl_cmpSpiel);
		
				abstractGameBoardWidget = new AbstractGameBoardWidget(cmpSpiel,
						SWT.NONE, 9, 6);
				GridLayout gridLayout = (GridLayout) abstractGameBoardWidget.getLayout();
				gridLayout.marginWidth = 3;
				gridLayout.marginTop = 3;
				gridLayout.marginRight = 3;
				gridLayout.marginLeft = 3;
				gridLayout.marginHeight = 3;
				gridLayout.marginBottom = 3;
				abstractGameBoardWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite cmpSpielkontrolle = new Composite(cmpSpiel, SWT.NONE);

		cmpSpielkontrolle.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
		GridLayout gl_cmpSpielkontrolle = new GridLayout(2, false);
		gl_cmpSpielkontrolle.marginWidth = 3;
		gl_cmpSpielkontrolle.marginTop = 3;
		gl_cmpSpielkontrolle.marginRight = 3;
		gl_cmpSpielkontrolle.marginLeft = 3;
		gl_cmpSpielkontrolle.marginHeight = 3;
		gl_cmpSpielkontrolle.marginBottom = 3;
		cmpSpielkontrolle.setLayout(gl_cmpSpielkontrolle);

		Label lblSpieldaten = new Label(cmpSpielkontrolle, SWT.NONE);
		GridData gd_lblSpieldaten = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1);
		gd_lblSpieldaten.widthHint = 138;
		lblSpieldaten.setLayoutData(gd_lblSpieldaten);
		lblSpieldaten.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblSpieldaten.setText("Spieldaten");

		lblZeit = new Label(cmpSpielkontrolle, SWT.NONE);
		lblZeit.setText("Zeit");

		lblTime = new Label(cmpSpielkontrolle, SWT.NONE);
		lblTime.setText("02:35");

		Label lblZge = new Label(cmpSpielkontrolle, SWT.NONE);
		GridData gd_lblZge = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_lblZge.heightHint = 13;
		lblZge.setLayoutData(gd_lblZge);
		lblZge.setText("Z\u00FCge");

		Label lblMoves = new Label(cmpSpielkontrolle, SWT.NONE);
		lblMoves.setText("15");

		lblDebug = new Label(cmpSpielkontrolle, SWT.NONE);
		GridData gd_lblDebug = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				2, 1);
		gd_lblDebug.heightHint = 15;
		gd_lblDebug.widthHint = 142;
		lblDebug.setLayoutData(gd_lblDebug);
		lblDebug.setText("New Label");

		lblDebug2 = new Label(cmpSpielkontrolle, SWT.NONE);
		GridData gd_lblDebug2 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1);
		gd_lblDebug2.widthHint = 143;
		lblDebug2.setLayoutData(gd_lblDebug2);
		lblDebug2.setText("New Label");
		new Label(cmpSpielkontrolle, SWT.NONE);
		new Label(cmpSpielkontrolle, SWT.NONE);

		Button btnLsen = new Button(cmpSpielkontrolle, SWT.NONE);
		btnLsen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				2, 1));
		btnLsen.setText("L\u00F6sung");

		Button button = new Button(cmpSpielkontrolle, SWT.NONE);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		button.setText("<<");

		Button button_1 = new Button(cmpSpielkontrolle, SWT.NONE);
		button_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		button_1.setEnabled(false);
		button_1.setText(">>");
		new Label(cmpSpielkontrolle, SWT.NONE);
		new Label(cmpSpielkontrolle, SWT.NONE);

		Button btnLockX = new Button(cmpSpielkontrolle, SWT.NONE);
		btnLockX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				abstractCarWidget.setLockY(false);
				abstractCarWidget.setLockX(true);
			}
		});
		btnLockX.setText("Lock X");

		Button btnLockY = new Button(cmpSpielkontrolle, SWT.NONE);
		btnLockY.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				abstractCarWidget.setLockY(true);
				abstractCarWidget.setLockX(false);
			}
		});
		btnLockY.setText("Lock Y");
		
		int minX = abstractGameBoardWidget.getMinWidth() + cmpSpielkontrolle.getBounds().width + 30;
		int minY = abstractGameBoardWidget.getMinHeight() + cmpSpielkontrolle.getBounds().height + 30;
		
		Point point = new Point (minX,minY);
		shell.setMinimumSize(point);
		resizeToDefinition();

		shell.addControlListener(new ControlListener() {
			
			
			
			@Override
			public void controlResized(ControlEvent e) {
				resizeToDefinition();
			}

			@Override
			public void controlMoved(ControlEvent arg0) {
				resizeToDefinition();				
			}
		});

	}

	private void resizeToDefinition() {
		if (tabFolder != null) {
			// TabFolder resizen
			
			int margin = ((GridLayout) abstractGameBoardWidget.getLayout()).marginHeight +((GridLayout) abstractGameBoardWidget.getLayout()).marginBottom;
			
			tabFolder.setBounds(tabFolder.getBounds().x,
					tabFolder.getBounds().y,
					shell.getBounds().width - 8,
					shell.getBounds().height - (tabSpielen.getBounds().height + cmpSpiel.getBounds().y + margin));
			
			if (abstractGameBoardWidget.getCurrentFieldSize().x > 0
					&& abstractGameBoardWidget.getCurrentFieldSize().y > 0)
				abstractCarWidget.setSize(abstractGameBoardWidget
						.getCurrentFieldSize());
		}
	}
	

}
