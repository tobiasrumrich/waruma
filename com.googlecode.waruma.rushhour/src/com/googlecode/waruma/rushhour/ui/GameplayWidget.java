package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.swtdesigner.SWTResourceManager;
/**
 * Composite fÃ¼r die Spielkontrolle
 * @author Rumrich
 *
 */
public class GameplayWidget extends Composite {

	private Label lblDebug;
	private Label lblDebug2;
	private Label lblZeit;
	private Label lblTime;
	private Button btnRueckwaerts;
	private Button btnVorwaerts;
	private final RushHour mainWindow;
	protected Label lblZge;
	protected Label lblMoves;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public GameplayWidget(final RushHour mainWindow, Composite parent, int style) {
		super(parent, style);
		this.mainWindow = mainWindow;
		this.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false,
				true, 1, 1));
		GridLayout gl_cmpSpielkontrolle = new GridLayout(2, false);
		gl_cmpSpielkontrolle.marginWidth = 3;
		gl_cmpSpielkontrolle.marginTop = 3;
		gl_cmpSpielkontrolle.marginRight = 3;
		gl_cmpSpielkontrolle.marginLeft = 3;
		gl_cmpSpielkontrolle.marginHeight = 3;
		gl_cmpSpielkontrolle.marginBottom = 3;
		this.setLayout(gl_cmpSpielkontrolle);

		Label lblSpieldaten = new Label(this, SWT.NONE);
		GridData gd_lblSpieldaten = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1);
		gd_lblSpieldaten.widthHint = 150;
		lblSpieldaten.setLayoutData(gd_lblSpieldaten);
		lblSpieldaten.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblSpieldaten.setText("Spieldaten");

		lblZeit = new Label(this, SWT.NONE);
		
		lblZeit.setText("Zeit");

		lblTime = new Label(this, SWT.NONE);
		lblTime.setText("00:00:00");

		lblZge = new Label(this, SWT.NONE);
		GridData gd_lblZge = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_lblZge.heightHint = 42;
		lblZge.setLayoutData(gd_lblZge);
		lblZge.setText("Züge");

		lblMoves = new Label(this, SWT.NONE);
		lblMoves.setText("0");
		GridData gd_lblMoves = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_lblMoves.widthHint = 170;
		gd_lblMoves.heightHint = 42;
		lblMoves.setLayoutData(gd_lblMoves);

		

		Button btnLoesen = new Button(this, SWT.NONE);
		btnLoesen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				2, 1));
		btnLoesen.setText("Spielfeld lösen");
		btnLoesen.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mainWindow.solveGameBoard();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		btnRueckwaerts = new Button(this, SWT.NONE);
		btnRueckwaerts.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1));
		btnRueckwaerts.setText("<<<");
		btnRueckwaerts.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mainWindow.undoLatestMove();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		btnVorwaerts = new Button(this, SWT.NONE);
		btnVorwaerts.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false,
				1, 1));
		btnVorwaerts.setEnabled(false);
		btnVorwaerts.setText(">>>");
		btnVorwaerts.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mainWindow.doMoveFromSolver();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

	}
	
	public void showForthButton(boolean show){
		btnVorwaerts.setEnabled(show);
	}
	
	public void showBackButton(boolean show){
		btnRueckwaerts.setEnabled(show);
	}
	
	public Label getLblTime() {
		return lblTime;
	}
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
