package com.googlecode.waruma.rushhour.ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.googlecode.waruma.rushhour.framework.Orientation;
import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;

public class RushHour {

	protected Shell shell;
	private Label lblTime;
	private Label lblZeit;
	private AbstractGameBoardWidget abstractGameBoardWidget;
	private Label lblDebug;
	private Label lblDebug2;
	private TabFolder tabFolder;
	private Composite cmpSpiel;
	private TabItem tabSpielen;
	private Menu menu;

	private List<AbstractCarWidget> carPool = new ArrayList<AbstractCarWidget>();
	private Composite cmpContainer;
	private Combo selAussehen;
	private AbstractCarWidget designerPreviewCar;
	private String[] data;

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

		AbstractCarWidget newCar2 = new AbstractCarWidget(shell, 1, 3,
				"/com/googlecode/waruma/rushhour/ui/images/n_truck_red.png");
		newCar2.setLocation(100, 176);
		newCar2.addMouseListener(new RushHourCarMouseListener(newCar2));
		newCar2.setVisible(false);
		carPool.add(newCar2);

		newCar2 = new AbstractCarWidget(shell, 1, 3,
				"/com/googlecode/waruma/rushhour/ui/images/n_truck_red.png");
		newCar2.setLocation(100, 176);
		newCar2.addMouseListener(new RushHourCarMouseListener(newCar2));
		newCar2.setVisible(false);
		carPool.add(newCar2);

		cmpContainer = new Composite(shell, SWT.NONE);
		cmpContainer.setBounds(10, 10, 898, 466);
		GridLayout gl_cmpContainer = new GridLayout(2, false);
		gl_cmpContainer.horizontalSpacing = 15;
		gl_cmpContainer.verticalSpacing = 0;
		gl_cmpContainer.marginWidth = 0;
		gl_cmpContainer.marginHeight = 0;
		cmpContainer.setLayout(gl_cmpContainer);

		// Point point = new Point(minX, minY);
		// shell.setMinimumSize(point);

		abstractGameBoardWidget = new AbstractGameBoardWidget(cmpContainer,
				SWT.NONE, 9, 6);
		abstractGameBoardWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1));
		GridLayout gridLayout = (GridLayout) abstractGameBoardWidget
				.getLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginTop = 0;
		gridLayout.marginRight = 0;
		gridLayout.marginLeft = 0;
		gridLayout.marginHeight = 0;

		tabFolder = new TabFolder(cmpContainer, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_tabFolder.widthHint = 239;
		tabFolder.setLayoutData(gd_tabFolder);
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// System.out.println(tabFolder.getSelectionIndex());
				if (tabFolder.getSelectionIndex() == 1) {
					for (AbstractCarWidget currentCar : carPool) {
						currentCar.setVisible(true);
					}
				} else {
					for (AbstractCarWidget currentCar : carPool) {
						currentCar.setVisible(false);
					}
				}
			}
		});

		TabItem tbtmDesigner = new TabItem(tabFolder, SWT.NONE);
		tbtmDesigner.setText("Designer");

		Composite cmpDesigner = new Composite(tabFolder, SWT.NONE);
		tbtmDesigner.setControl(cmpDesigner);
		cmpDesigner.setLayout(new GridLayout(2, false));
		
		Composite composite = new Composite(cmpDesigner, SWT.NONE);
		composite.setLayout(null);
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_composite.heightHint = 282;
		composite.setLayoutData(gd_composite);
		
		designerPreviewCar = new AbstractCarWidget(composite, SWT.NONE, 0, "/com/googlecode/waruma/rushhour/ui/images/car_rot_bg_black.png");
		designerPreviewCar.setBounds(68, 22, 100, 200);
		
		Button button_2 = new Button(composite, SWT.NONE);
		button_2.setBounds(10, 236, 31, 31);
		button_2.setText("<-");
		
		Button button_3 = new Button(composite, SWT.NONE);
		button_3.setBounds(188, 236, 31, 31);
		button_3.setText("->");
		
				AbstractCarWidget newCar1 = new AbstractCarWidget(composite, 1, 2,
						"/com/googlecode/waruma/rushhour/ui/images/car_rot_bg_black.png");
				newCar1.setBounds(82, 250, 100, 200);
				newCar1.addMouseListener(new RushHourCarMouseListener(newCar1));
				newCar1.setVisible(false);
				carPool.add(newCar1);
		
		Label lblOrientierung = new Label(cmpDesigner, SWT.NONE);
		lblOrientierung.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOrientierung.setText("Fahrzeugtyp");
		
		Combo combo = new Combo(cmpDesigner, SWT.READ_ONLY);
		combo.setItems(new String[] {"PKW (belegt 2 Felder)", "Lastwagen (belegt 3 Felder)", "Ludolphs Q7 (belegt 4 Felder)"});
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAussehen = new Label(cmpDesigner, SWT.NONE);
		lblAussehen.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAussehen.setText("Aussehen");
		
		
		UICarFactory carFactory = new UICarFactory("./src/com/googlecode/waruma/rushhour/ui/images/");
		selAussehen = new Combo(cmpDesigner, SWT.READ_ONLY);
		selAussehen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				/* switch (selAussehen.getSelectionIndex()){
				case 0:
					designerPreviewCar.changeImage("/com/googlecode/waruma/rushhour/ui/images/car_rot_bg_black.png");
					break;
				case 1:
					designerPreviewCar.changeImage("/com/googlecode/waruma/rushhour/ui/images/n_truck_red.png");
					break;
				}*/
				//System.out.println(selAussehen.getSelectionIndex());
				//System.out.println(data[selAussehen.getSelectionIndex()]);
				designerPreviewCar.changeImage("/com/googlecode/waruma/rushhour/ui/images/"+data[selAussehen.getSelectionIndex()]);
			}
		});
		
		
		ArrayList<ImageBean> availableImages = carFactory.getAvailableImages(2);
		String[] labels = new String[availableImages.size()];
		data = new String[availableImages.size()];
		
		for (int i = 0; i < availableImages.size(); i++) {
			labels[i] = availableImages.get(i).getCarName();
			data[i] = availableImages.get(i).getFilename();
			
		}
		
		selAussehen.setItems(labels);
		
		
		selAussehen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


		Label label_2 = new Label(cmpDesigner, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		

		tabSpielen = new TabItem(tabFolder, SWT.NONE);
		tabSpielen.setText("Spielen");

		cmpSpiel = new Composite(tabFolder, SWT.NONE);
	
		
		Button btnSpielerauto = new Button(cmpDesigner, SWT.CHECK);
		btnSpielerauto.setText("Spielerauto");
		new Label(cmpDesigner, SWT.NONE);
		
		Button btnLenkradschloss = new Button(cmpDesigner, SWT.CHECK);
		btnLenkradschloss.setText("Lenkradschloss");
		Label label = new Label(cmpDesigner, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		new Label(cmpDesigner, SWT.NONE);

		tabSpielen.setControl(cmpSpiel);
		GridLayout gl_cmpSpiel = new GridLayout(1, false);
		gl_cmpSpiel.marginWidth = 3;
		gl_cmpSpiel.marginTop = 3;
		gl_cmpSpiel.marginRight = 3;
		gl_cmpSpiel.marginLeft = 3;
		gl_cmpSpiel.marginHeight = 3;
		gl_cmpSpiel.marginBottom = 3;
		gl_cmpSpiel.horizontalSpacing = 10;
		cmpSpiel.setLayout(gl_cmpSpiel);

		Composite cmpSpielkontrolle = new Composite(cmpSpiel, SWT.NONE);

		cmpSpielkontrolle.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false,
				true, 1, 1));
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

		int minX = abstractGameBoardWidget.getMinWidth()
				+ cmpSpielkontrolle.getBounds().width + 30;
		int minY = abstractGameBoardWidget.getMinHeight()
				+ cmpSpielkontrolle.getBounds().height + 30;
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
	/*tabFolder != null) {
			// TabFolder resizen

			int margin = ((GridLayout) abstractGameBoardWidget.getLayout()).marginHeight
					+ ((GridLayout) abstractGameBoardWidget.getLayout()).marginBottom;
/*
			tabFolder.setBounds(
					tabFolder.getBounds().x,
					tabFolder.getBounds().y,
					shell.getBounds().width - 8,
					// shell.getBounds().height - (tabSpielen.getBounds().height
					// + cmpSpiel.getBounds().y + margin));
					shell.getBounds().height
							- (abstractGameBoardWidget.getLocation().y
									+ cmpSpiel.getLocation().y
									+ tabFolder.getLocation().y + 18));
	}*/
			if (abstractGameBoardWidget.getCurrentFieldSize().x > 0
					&& abstractGameBoardWidget.getCurrentFieldSize().y > 0)
				for (AbstractCarWidget currentCar : carPool) {
					currentCar.setSize(abstractGameBoardWidget
							.getCurrentFieldSize());
				}
			


			// Autos neu positionieren
			for (AbstractCarWidget currentCar : carPool) {
				repositionCarOnBoard(currentCar);
			}
		//}
		
		cmpContainer.setBounds(12, 10, shell.getBounds().width - 30, shell.getBounds().height-65);
	}

	private void repositionCarOnBoard(AbstractCarWidget carWidget) {
		if (carWidget.getPositionOnGameBoard() != null) {
			int boardX = abstractGameBoardWidget.getLocation().x
					+ cmpContainer.getLocation().x;
			int boardY = abstractGameBoardWidget.getLocation().y
					+ cmpContainer.getLocation().y;
			int newCarX = (carWidget.getPositionOnGameBoard().x * abstractGameBoardWidget
					.getCurrentFieldSize().x) + boardX;
			int newCarY = (carWidget.getPositionOnGameBoard().y * abstractGameBoardWidget
					.getCurrentFieldSize().y) + boardY;
			Point location = new Point(newCarX, newCarY);
			carWidget.setLocation(location);
		}
	}

	private class RushHourCarMouseListener implements MouseListener {

		AbstractCarWidget observedCar;

		public RushHourCarMouseListener(AbstractCarWidget observedCar) {
			super();
			this.observedCar = observedCar;
		}

		MouseMoveListener mouseMoveListener = new MouseMoveListener() {
			public void mouseMove(MouseEvent arg0) {
				// BEGIN CageControl
				int neuesX = observedCar.getLocation().x + arg0.x - clickX;
				int neuesY = observedCar.getLocation().y + arg0.y - clickY;

				int boardWidth = abstractGameBoardWidget.getBounds().width;
				int boardHeight = abstractGameBoardWidget.getBounds().height;
				int boardX = abstractGameBoardWidget.getLocation().x
						+ cmpContainer.getLocation().x;
				int boardY = abstractGameBoardWidget.getLocation().y
						+ cmpContainer.getLocation().y;

				if (neuesX > (boardWidth + boardX
						- observedCar.getBounds().width))
					neuesX = boardWidth + boardX
							- observedCar.getBounds().width;

				if (neuesX <= boardX)
					neuesX = boardX;

				if (neuesY > (boardY + boardHeight
						- observedCar.getBounds().height))
					neuesY = (boardY + boardHeight
							- observedCar.getBounds().height);

				if (neuesY <= boardY)
					neuesY = boardY;

				if (observedCar.isLockX())
					observedCar
							.setLocation(observedCar.getLocation().x, neuesY);
				else if (observedCar.isLockY())
					observedCar
							.setLocation(neuesX, observedCar.getLocation().y);
				else
					observedCar.setLocation(neuesX, neuesY);
				// END CageControl

				// BEGIN FieldControl
				int currentX = observedCar.getLocation().x;
				int currentY = observedCar.getLocation().y;
				int gameBoardX = abstractGameBoardWidget.getLocation().x
						+ cmpContainer.getLocation().x;
				
				int gameBoardY = abstractGameBoardWidget.getLocation().y
						+ cmpContainer.getLocation().y;

				int fieldSizeWidth = abstractGameBoardWidget
						.getCurrentFieldSize().x;
				int fieldSizeHeight = abstractGameBoardWidget
						.getCurrentFieldSize().y;
				int posX = (currentX - gameBoardX + (abstractGameBoardWidget
						.getCurrentFieldSize().x / 2)) / fieldSizeWidth;
				int posY = (currentY - gameBoardY + (abstractGameBoardWidget
						.getCurrentFieldSize().y / 2)) / fieldSizeHeight;
				observedCar.setPositionOnGameBoard(new Point(posX, posY));
				lblDebug.setText(posX + ":" + posY);
				// END Field Control

			}

		};

		int clickX;
		int clickY;

		@Override
		public void mouseUp(MouseEvent e) {
			observedCar.removeMouseMoveListener(mouseMoveListener);
			repositionCarOnBoard(observedCar);
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
							abstractGameBoardWidget.getCurrentFieldSize());
					break;
				case EAST:
					observedCar.changeOrientation(Orientation.SOUTH,
							abstractGameBoardWidget.getCurrentFieldSize());
					break;
				case SOUTH:
					observedCar.changeOrientation(Orientation.WEST,
							abstractGameBoardWidget.getCurrentFieldSize());
					break;
				case WEST:
					observedCar.changeOrientation(Orientation.NORTH,
							abstractGameBoardWidget.getCurrentFieldSize());
					break;
				}

			}

		}
	}
}
