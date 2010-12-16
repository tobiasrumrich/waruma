package com.googlecode.waruma.rushhour.ui;

import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.swtdesigner.SWTResourceManager;

public class GameBoardWidget extends Composite {

	private Composite parent;
	private Label[][] spielbrett;
	private int hoehe;
	private int breite;
	private int minHoeheFeld = 50;
	private int minBreiteFeld = 50;
	private String inField = "NULL!!";
	private Point goalField;
	
	private org.eclipse.swt.graphics.Color colorForHighlight = SWTResourceManager.getColor(0, 210, 0);
	private org.eclipse.swt.graphics.Color colorForOdd =SWTResourceManager.getColor(166, 202, 240) ;
	private org.eclipse.swt.graphics.Color colorForUnodd = SWTResourceManager.getColor(119, 136, 153);

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 * @param breite
	 * @param hoehe
	 */
	public GameBoardWidget(Composite parent, int style, int breite,
			int hoehe) {
		super(parent, style);
		
		this.parent = parent;
		this.hoehe = hoehe;
		this.breite = breite;
		this.addControlListener(new ControlListener() {
			
			@Override
			public void controlResized(ControlEvent arg0) {
				arg0.
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void controlMoved(ControlEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		spielbrett = new Label[breite][hoehe];
		GridLayout gridLayout = new GridLayout(breite, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginLeft = 0;
		gridLayout.marginBottom = 0;
		gridLayout.marginRight = 0;
		setLayout(gridLayout);
		for (int j = 0; j < hoehe; j++) {
			for (int i = 0; i < breite; i++) {

				spielbrett[i][j] = new Label(this, SWT.NONE);
				spielbrett[i][j].setText(i + ":" + j);
				spielbrett[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL,
						true, true, 1, 1));

				if ((i + j) % 2 == 0) {
					spielbrett[i][j].setBackground(colorForOdd);
				} else {
					spielbrett[i][j].setBackground(colorForUnodd);
				}
			}

		}
	}

	public int getMinHeight() {
		return (hoehe * minHoeheFeld)
				+ ((hoehe - 1) * ((GridLayout) this.getLayout()).horizontalSpacing);
	}

	public int getMinWidth() {
		return (breite * minHoeheFeld)
				+ ((breite - 1) * ((GridLayout) this.getLayout()).verticalSpacing);
	}

	public Point getCurrentFieldSize() {
		return (spielbrett[0][0].getSize());
	}

	public Point getGoalField() {
		return goalField;
	}

	public void setGoalField(Point goalField) {
		this.goalField = goalField;
	}

	
	public int getHoehe() {
		return hoehe;
	}

	public int getBreite() {
		return breite;
	}

	public String woBinIch() {
		/*
		 * int aktuelleBreite = spielbrett[0][0].getSize().x; int aktuelleHoehe
		 * = spielbrett[0][0].getSize().y; int aktuellerAbstandX = ((GridLayout)
		 * this.getLayout()).horizontalSpacing; int aktuellerAbstandY =
		 * ((GridLayout) this.getLayout()).verticalSpacing;
		 * 
		 * int pixelFeldX = point.x /
		 * (breite*(aktuelleBreite+aktuellerAbstandX)); int pixelFeldY = point.y
		 * / (hoehe+aktuellerAbstandY);
		 */
		return inField;
	}

	public void setHighlight(Point fieldPoint) {
		spielbrett[fieldPoint.x][fieldPoint.y].setBackground( colorForHighlight);
	}
	
	public void removeHighlight(Point fieldPoint) {
		if ((fieldPoint.x + fieldPoint.y) % 2 == 0) {
			spielbrett[fieldPoint.x][fieldPoint.y].setBackground(colorForOdd);
		} else {
			spielbrett[fieldPoint.x][fieldPoint.y].setBackground(colorForUnodd);
		}
		setBackground( colorForHighlight);
	}
	
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	void repositionCarOnBoard(CarWidget carWidget) {
		if (carWidget.getPositionOnGameBoard() != null) {
			int boardX = getLocation().x
					+ parent.getLocation().x;
			int boardY = getLocation().y
					+ parent.getLocation().y;
			int newCarX = (carWidget.getPositionOnGameBoard().x * getCurrentFieldSize().x) + boardX;
			int newCarY = (carWidget.getPositionOnGameBoard().y * getCurrentFieldSize().y) + boardY;
			if (newCarX < (boardX + getBounds().width - (getCurrentFieldSize().x / 2))) {
				Point location = new Point(newCarX, newCarY);
				carWidget.setLocation(location);
			}
		}
	}

}
