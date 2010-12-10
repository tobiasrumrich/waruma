package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.swtdesigner.SWTResourceManager;

public class AbstractGameBoardWidget extends Composite {

	private Label[][] spielbrett;
	private int hoehe;
	private int breite;
	private int minHoeheFeld = 50;
	private int minBreiteFeld = 50;
	private String inField = "NULL!!";

	private class FieldMouseListener implements MouseTrackListener {
		
		private int fieldX;
		private int fieldY;

		public FieldMouseListener(int fieldX, int fieldY) {
			this.fieldX = fieldX;
			this.fieldY = fieldY;
		}
		@Override
		public void mouseEnter(MouseEvent arg0) {
			inField = fieldX + "--" + fieldY;
			//System.out.println("Mausposition auf Spielfeld: " + fieldX + ":" + fieldY);
		}

		@Override
		public void mouseExit(MouseEvent arg0) {
			inField = "Tschüssing";

		}

		@Override
		public void mouseHover(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 * @param breite
	 * @param hoehe
	 */
	public AbstractGameBoardWidget(Composite parent, int style, int breite,
			int hoehe) {
		super(parent, style);

		this.hoehe = hoehe;
		this.breite = breite;

		spielbrett = new Label[hoehe][breite];
		GridLayout gridLayout = new GridLayout(breite, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginLeft = 0;
		gridLayout.marginBottom = 0;
		gridLayout.marginRight = 0;
		setLayout(gridLayout);

		for (int i = 0; i < hoehe; i++) {
			for (int j = 0; j < breite; j++) {
				spielbrett[i][j] = new Label(this, SWT.NONE);
				spielbrett[i][j].setText(i + ":" + j);
				spielbrett[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL,
						true, true, 1, 1));

				if ((i + j) % 2 == 0) {
					spielbrett[i][j].setBackground(SWTResourceManager.getColor(
							166, 202, 240));
				} else {
					spielbrett[i][j].setBackground(SWTResourceManager.getColor(
							119, 136, 153));
				}
				spielbrett[i][j]
						.addMouseTrackListener(new FieldMouseListener(i,j));
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

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
