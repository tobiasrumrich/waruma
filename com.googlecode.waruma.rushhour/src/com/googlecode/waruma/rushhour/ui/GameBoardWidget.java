package com.googlecode.waruma.rushhour.ui;

import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.googlecode.waruma.rushhour.framework.Orientation;
import com.swtdesigner.SWTResourceManager;

public class GameBoardWidget extends Composite {

	private Composite parent;
	public Label[][] spielbrett;
	private int hoehe;
	private int breite;
	private int minHoeheFeld = 50;
	private int minBreiteFeld = 50;
	private String inField = "NULL!!";
	private Point goalField;
	private IImageCache imageCache = new ImageCache();
	
	private org.eclipse.swt.graphics.Color colorForHighlight = SWTResourceManager.getColor(0, 210, 0);
	private org.eclipse.swt.graphics.Color colorForOdd =SWTResourceManager.getColor(166, 202, 240) ;
	private org.eclipse.swt.graphics.Color colorForUnodd = SWTResourceManager.getColor(119, 136, 153);
	private Image tile_even = SWTResourceManager.getImage(GameBoardWidget.class, "/com/googlecode/waruma/rushhour/ui/images/tile_even.png");
	private Image tile_odd = SWTResourceManager.getImage(GameBoardWidget.class, "/com/googlecode/waruma/rushhour/ui/images/tile_odd.png");
	private Image original_tile_even = tile_even;
	private Image original_tile_odd = tile_odd;
	private String imageFilename = "Tiles";
	

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
			// System.out.println("Mausposition auf Spielfeld: " + fieldX + ":"
			// + fieldY);
		}

		@Override
		public void mouseExit(MouseEvent arg0) {
			inField = "Tsch�ssing";

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
	public GameBoardWidget(Composite parent, int style, int breite,
			int hoehe) {
		super(parent, style);
		
		this.parent = parent;
		this.hoehe = hoehe;
		this.breite = breite;
		this.addControlListener(new ControlListener() {
			
			@Override
			public void controlResized(ControlEvent arg0) {
				setSize();
				
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
				spielbrett[i][j].addMouseTrackListener(new FieldMouseListener(
						i, j));
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
	
	public void setSize() {
		
		
		for (int j = 0; j < hoehe; j++) {
			for (int i = 0; i < breite; i++) {

				if ((i + j) % 2 == 0) {
					spielbrett[i][j].setBackgroundImage(getImage(original_tile_odd));
				} else {
					spielbrett[i][j].setBackgroundImage(getImage(original_tile_even));
				}
			}
		}
	}
	
	private Image getImage(Image gImage) {

		Image newImage;

		if ((imageCache.checkCache(Orientation.NORTH, spielbrett[0][0].getSize()))) {
			newImage = imageCache.getImage(imageFilename, Orientation.NORTH, spielbrett[0][0].getSize());
		} else {
			
			ImageData imgData = gImage.getImageData();

			
			imgData = imgData.scaledTo(parent.getBounds().width/(breite + 2),parent.getBounds().height/(hoehe));
			

			newImage = new Image(this.getDisplay(), imgData);
			imageCache.addImage(imageFilename, Orientation.NORTH, spielbrett[0][0].getSize(), newImage);
		}

		return newImage;
	}

}
