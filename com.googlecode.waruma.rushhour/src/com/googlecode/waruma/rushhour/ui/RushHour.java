package com.googlecode.waruma.rushhour.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.game.RushHourBoardCreationController;
import com.googlecode.waruma.rushhour.game.RushHourGameplayControler;
import com.swtdesigner.SWTResourceManager;

public class RushHour {
	private static final int BOARDHEIGHT = 6;
	private static final int BOARDWIDTH = 6;
	final public static String IMAGEBASEPATH = "/com/googlecode/waruma/rushhour/ui/images/";
	protected Shell shell;
	private Label lblTime;
	private Label lblZeit;
	Label lblDebug;
	private Label lblDebug2;
	private TabItem tabSpielen;
	private Menu menu;

	protected AbstractCarWidget highestCar;
	protected List<AbstractCarWidget> carPool = new ArrayList<AbstractCarWidget>();
	protected Composite mainComposite;
	protected AbstractGameBoardWidget abstractGameBoardWidget;
	protected TabFolder tabFolder;
	protected Composite cmpSpiel;
	protected boolean gameMode; // True - Design
	protected RushHourBoardCreationController boardCreationControler;
	protected RushHourGameplayControler gameplayControler;

	private UICarFactory carFactory;

	public List<ImageBean> availableCars;
	public List<ImageBean> availableTrucks;
	public List<ImageBean> availablePlayers;
	protected AbstractDesignerWidget abstractDesignerWidget;

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
		gameMode = true;
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

	private void buildWindow() {
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
				try {
					if (selected != null) {
						initializeNewGame(selected);
					}
				} catch (IOException ex) {
					MessageBox messageBox = new MessageBox(shell);
					messageBox.setMessage("Bla falscher Pfad oder so");
					messageBox.setText("gdsgs");
					messageBox.open();
				}
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
				try {
					if (selected != null) {
						if (gameMode) {
							boardCreationControler.saveGameBoard(selected);
						} else {
							gameplayControler.saveGame(selected);
						}
					}
				} catch (IOException ex) {
					MessageBox messageBox = new MessageBox(shell);
					messageBox.setMessage("Bla falscher Pfad oder so");
					messageBox.setText("gdsgs");
					messageBox.open();
				}

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
						.setMessage("M�chten Sie diese Anwendung wirklich beenden?");
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
	}

	protected void initializeNewGame(String fileName) throws IOException {
		if (abstractGameBoardWidget.getGoalField() != null) {
			abstractGameBoardWidget.removeHighlight(abstractGameBoardWidget
					.getGoalField());
		}

		for (AbstractCarWidget carPoolCar : carPool) {
			carPoolCar.dispose();
		}

		carPool.clear();

		Collection<IGameBoardObject> carsFromController = new ArrayList<IGameBoardObject>();
		if (gameMode) {
			// Designer
			boardCreationControler.loadGameBoard(fileName);
			carsFromController = boardCreationControler.getGameBoardObjects();
		} else {
			// GamePlay
			gameplayControler.loadGame(fileName);
			carsFromController = gameplayControler.getCars();
		}

		for (IGameBoardObject boardObject : carsFromController) {
			AbstractCarWidget abstractCarWidget = new AbstractCarWidget(shell,
					this, boardObject);

			if (gameMode) {
				if (boardObject instanceof IPlayer) {
					abstractCarWidget.updateRushHourListener(new DesignerPlayerCarMouseListener(this,
							abstractCarWidget));
				} else {
					abstractCarWidget.updateRushHourListener(new DesignerCarMouseListener(this,
							abstractCarWidget));
				}
			} else {

			}

			carPool.add(abstractCarWidget);
			abstractCarWidget.moveAbove(mainComposite);

		}
	}
	
	private void switchToGameplay(){
		Object gameState = boardCreationControler.getCurrentState();
		this.gameplayControler = new RushHourGameplayControler(gameState);
		for (AbstractCarWidget currentCar : carPool) {
			currentCar.updateRushHourListener(new GameplayCarMouseListener(this, currentCar));
			currentCar.setLock();
		}
	}

	protected void switchToDesigner() {
			
	}
	
	private void initializeAvailableCarImages() {
		carFactory = new UICarFactory();
		carFactory.scanDirectory("./src" + IMAGEBASEPATH);
		availableCars = carFactory.getAvailableImages(CarType.CAR);
		availableTrucks = carFactory.getAvailableImages(CarType.TRUCK);
		availablePlayers = carFactory.getAvailableImages(CarType.PLAYER);
	}

	private void initializeBoardCreationController() {
		this.boardCreationControler = new RushHourBoardCreationController(
				BOARDWIDTH, BOARDHEIGHT);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		buildWindow();
		initializeAvailableCarImages();
		initializeBoardCreationController();

		mainComposite = new Composite(shell, SWT.NONE);
		mainComposite.setBounds(10, 10, 898, 466);
		GridLayout gl_cmpContainer = new GridLayout(2, false);
		gl_cmpContainer.horizontalSpacing = 15;
		gl_cmpContainer.verticalSpacing = 0;
		gl_cmpContainer.marginWidth = 0;
		gl_cmpContainer.marginHeight = 0;
		mainComposite.setLayout(gl_cmpContainer);

		abstractGameBoardWidget = new AbstractGameBoardWidget(mainComposite,
				SWT.NONE, BOARDWIDTH, BOARDHEIGHT);
		abstractGameBoardWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1));
		GridLayout gridLayout = (GridLayout) abstractGameBoardWidget
				.getLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginTop = 0;
		gridLayout.marginRight = 0;
		gridLayout.marginLeft = 0;
		gridLayout.marginHeight = 0;

		tabFolder = new TabFolder(mainComposite, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1);
		gd_tabFolder.widthHint = 239;
		tabFolder.setLayoutData(gd_tabFolder);
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// System.out.println(tabFolder.getSelectionIndex());
				if (tabFolder.getSelectionIndex() == 1) {
					switchToGameplay();
				} else {
					switchToDesigner();
				}
			}
		});

		TabItem tbtmDesigner = new TabItem(tabFolder, SWT.NONE);
		tbtmDesigner.setText("Designer");

		abstractDesignerWidget = new AbstractDesignerWidget(this, tabFolder,
				SWT.NONE);
		tbtmDesigner.setControl(abstractDesignerWidget);

		tabSpielen = new TabItem(tabFolder, SWT.NONE);
		tabSpielen.setText("Spielen");

		cmpSpiel = new Composite(tabFolder, SWT.NONE);

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

		if (abstractGameBoardWidget.getCurrentFieldSize().x > 0
				&& abstractGameBoardWidget.getCurrentFieldSize().y > 0)
			for (AbstractCarWidget currentCar : carPool) {
				currentCar.setSize(abstractGameBoardWidget
						.getCurrentFieldSize());
			}

		// Autos neu positionieren
		for (AbstractCarWidget currentCar : carPool) {
			abstractGameBoardWidget.repositionCarOnBoard(currentCar);
		}
		// }

		mainComposite.setBounds(12, 10, shell.getBounds().width - 30,
				shell.getBounds().height - 65);
	}
}
