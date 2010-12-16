package com.googlecode.waruma.rushhour.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IGameWonObserver;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.game.RushHourBoardCreationController;
import com.googlecode.waruma.rushhour.game.RushHourGameplayControler;
import com.swtdesigner.SWTResourceManager;

public class RushHour implements IGameWonObserver {
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

	protected CarWidget highestCar;
	protected List<CarWidget> carPool = new ArrayList<CarWidget>();
	protected Composite mainComposite;
	protected GameBoardWidget abstractGameBoardWidget;
	protected TabFolder tabFolder;
	protected Composite cmpSpiel;
	protected boolean gameMode; // True - Design
	protected RushHourBoardCreationController boardCreationControler;
	protected RushHourGameplayControler gameplayControler;

	private UICarFactory carFactory;

	public List<ImageBean> availableCars;
	public List<ImageBean> availableTrucks;
	public List<ImageBean> availablePlayers;
	protected DesignerWidget abstractDesignerWidget;
	protected GameplayWidget gamePlayWidget;
	private Thread timeUpdaterThread;
	private Display display;
	private Queue<IMove> moveQueue;

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
		display = Display.getDefault();
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
				messageBox.setMessage("Möchten Sie RushHour wirklich beenden?");
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

	public void undoLatestMove() {
		if (gameplayControler != null) {
			IGameBoardObject boardObject = gameplayControler.undoLatestMove();
			for (CarWidget car : carPool) {
				if (car.gameObject.equals(boardObject)) {
					car.moveCarInUi();
				}
			}
			if (!gameplayControler.hasMoveInHistory()) {
				gamePlayWidget.showBackButton(false);
			}
			gamePlayWidget.showForthButton(false);
			moveQueue = null;
		}
	}

	protected void initializeNewGame(String fileName) throws IOException {
		if (abstractGameBoardWidget.getGoalField() != null) {
			abstractGameBoardWidget.removeHighlight(abstractGameBoardWidget
					.getGoalField());
		}

		for (CarWidget carPoolCar : carPool) {
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
			CarWidget abstractCarWidget = new CarWidget(shell, this,
					boardObject);

			if (gameMode) {
				if (boardObject instanceof IPlayer) {
					abstractCarWidget
							.updateRushHourListener(new DesignerPlayerCarMouseListener(
									this, abstractCarWidget));
				} else {
					abstractCarWidget
							.updateRushHourListener(new DesignerCarMouseListener(
									this, abstractCarWidget));
				}
			} else {

			}

			carPool.add(abstractCarWidget);
			abstractCarWidget.moveAbove(mainComposite);

		}
	}

	private void switchToGameplay() {
		Object gameState = boardCreationControler.getCurrentState();
		this.gameplayControler = new RushHourGameplayControler(gameState);
		this.gameplayControler.registerGameWon(this);
		for (CarWidget currentCar : carPool) {
			currentCar.updateRushHourListener(new GameplayCarMouseListener(
					this, currentCar));
			currentCar.setLock();
		}

		gamePlayWidget.showBackButton(false);

		final int time = 500;
		final Runnable timer = new Runnable() {
			public void run() {
				if (gamePlayWidget.getLblTime().isDisposed()
						|| tabFolder.getSelectionIndex() == 0
						|| mainComposite.isDisposed()) {
					return;
				}
				gamePlayWidget.getLblTime().setText(
						gameplayControler.elapsedGameTime());
				display.timerExec(time, this);
			}
		};
		display.timerExec(time, timer);

	}

	protected void switchToDesigner() {
		if (gameplayControler != null) {

			Object gameState = gameplayControler.getCurrentState();
			boardCreationControler.loadState(gameState);
			for (CarWidget currentCar : carPool) {
				if (currentCar.player) {
					currentCar
							.updateRushHourListener(new DesignerPlayerCarMouseListener(
									this, currentCar));
					currentCar.removeLock();
					currentCar.isLocked = false;
				} else {
					currentCar
							.updateRushHourListener(new DesignerCarMouseListener(
									this, currentCar));
					currentCar.removeLock();
					currentCar.isLocked = false;
				}
			}
		}
	}

	public void doMoveFromSolver() {
		if(moveQueue != null && !moveQueue.isEmpty()){
			IMove move = moveQueue.poll();
			IGameBoardObject gameBoardObject = (IGameBoardObject) move.getMoveable();
			try {
				gameplayControler.moveCar(gameBoardObject, move.getDistance());
			} catch (IllegalMoveException e) {
				gamePlayWidget.showForthButton(false);
			}
			
			for (CarWidget car : carPool) {
				if(car.gameObject.equals(gameBoardObject)){
					car.moveCarInUi();
					gamePlayWidget.showBackButton(true);
				}
			}
		} else {
			gamePlayWidget.showForthButton(false);
		}
	}

	public void solveGameBoard() {
		if (gameplayControler != null) {
			List<IMove> moveList = gameplayControler.solveGame();
			if (moveList != null && !moveList.isEmpty()) {
				moveQueue = new LinkedList<IMove>(moveList);
				gamePlayWidget.showForthButton(true);
			}
		}
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

		abstractGameBoardWidget = new GameBoardWidget(mainComposite, SWT.NONE,
				BOARDWIDTH, BOARDHEIGHT);
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
				if (tabFolder.getSelectionIndex() == 1) {
					switchToGameplay();
				} else {
					switchToDesigner();
				}
			}
		});

		TabItem tbtmDesigner = new TabItem(tabFolder, SWT.NONE);
		tbtmDesigner.setText("Designer");

		abstractDesignerWidget = new DesignerWidget(this, tabFolder, SWT.NONE);
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

		gamePlayWidget = new GameplayWidget(this, cmpSpiel, SWT.NONE);

		int minX = abstractGameBoardWidget.getMinWidth()
				+ gamePlayWidget.getBounds().width + 30;
		int minY = abstractGameBoardWidget.getMinHeight()
				+ gamePlayWidget.getBounds().height + 30;
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

	@Override
	public void updateGameWon() {
		GameWonNotifier gameWonWindow = new GameWonNotifier(this, gameplayControler.elapsedGameTime(),
				33);
		gameWonWindow.open();
	}

	private void resizeToDefinition() {

		if (abstractGameBoardWidget.getCurrentFieldSize().x > 0
				&& abstractGameBoardWidget.getCurrentFieldSize().y > 0)
			for (CarWidget currentCar : carPool) {
				currentCar.setSize(abstractGameBoardWidget
						.getCurrentFieldSize());
			}

		// Autos neu positionieren
		for (CarWidget currentCar : carPool) {
			abstractGameBoardWidget.repositionCarOnBoard(currentCar);
		}

		mainComposite.setBounds(12, 10, shell.getBounds().width - 30,
				shell.getBounds().height - 65);
	}

	

}
