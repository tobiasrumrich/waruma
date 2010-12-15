package com.googlecode.waruma.rushhour.ui;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.swtdesigner.SWTResourceManager;

public class AbstractCarWidget extends Composite {

	private Point positionOnGameBoard;
	private RushHour mainWindow;
	private Image image;
	private String imageFilename;
	private IImageCache imageCache = new ImageCache();
	private Image originalImage;
	private Orientation orientation = Orientation.NORTH;
	private boolean isLocked;
	private boolean lockX = false;
	private boolean lockY = false;
	private boolean lockInCage = false;

	protected boolean steeringLock;
	protected boolean player;
	protected int length;
	protected boolean knownInController;
	protected IGameBoardObject gameObject;

	public void addToBoardControler() {
		try {
			if (this.length == 3) {
				gameObject = mainWindow.boardCreationControler.createTruck(
						positionOnGameBoard, orientation, steeringLock);
			}
			if (this.length == 2 && player) {
				gameObject = mainWindow.boardCreationControler.createPlayerCar(
						positionOnGameBoard,
						mainWindow.abstractGameBoardWidget.getGoalField(),
						orientation);
			}
			if (this.length == 2 && !player) {
				gameObject = mainWindow.boardCreationControler.createCar(
						positionOnGameBoard, orientation, steeringLock);
			}
			knownInController = true;
		} catch (IllegalBoardPositionException e) {
			// Auto entfernen
			removeFromGameBoard();
		}
	}
	
	public void removeFromGameBoard() {
		this.dispose();
		if(knownInController)
			mainWindow.boardCreationControler.removeObjectFromBoard(gameObject);
		mainWindow.carPool.remove(this);
	}

	public boolean moveToPositionControler(){
		try {
			mainWindow.boardCreationControler.changeCarPosition(gameObject, positionOnGameBoard);
			return true;
		} catch (IllegalBoardPositionException e) {
			// Alte Position auf dem Spielbrett wieder herstellen
			java.awt.Point oldPosition = gameObject.getPosition();
			positionOnGameBoard = new Point(oldPosition.x, oldPosition.y);
			mainWindow.abstractGameBoardWidget.repositionCarOnBoard(this);
			return false;
		}
	}
	
	public void rotateToOrientation(){
		switch (orientation) {
		case NORTH:
			try{
				mainWindow.boardCreationControler.changeRotation(gameObject, Orientation.EAST);
				this.changeOrientation(Orientation.EAST,
						mainWindow.abstractGameBoardWidget.getCurrentFieldSize());
			} catch (IllegalBoardPositionException e) {}
			break;
		case EAST:
			try{
				mainWindow.boardCreationControler.changeRotation(gameObject, Orientation.SOUTH);
				this.changeOrientation(Orientation.SOUTH,
						mainWindow.abstractGameBoardWidget.getCurrentFieldSize());
			} catch (IllegalBoardPositionException e) {}
			break;
		case SOUTH:
			try{
				mainWindow.boardCreationControler.changeRotation(gameObject, Orientation.WEST);
				this.changeOrientation(Orientation.WEST,
						mainWindow.abstractGameBoardWidget.getCurrentFieldSize());
			} catch (IllegalBoardPositionException e) {}
			break;
		case WEST:
			try{
				mainWindow.boardCreationControler.changeRotation(gameObject, Orientation.NORTH);
				this.changeOrientation(Orientation.NORTH,
						mainWindow.abstractGameBoardWidget.getCurrentFieldSize());
			} catch (IllegalBoardPositionException e) {}
			break;
		}
	}
	
	
	
	/**
	 * The height in fields
	 */
	private int fieldHeight = 2;
	/**
	 * The width in fields
	 */
	private int fieldWidth = 1;

	public int getFieldHeight() {
		return fieldHeight;
	}

	public void setFieldHeight(int fieldHeight) {
		this.fieldHeight = fieldHeight;
	}

	public int getFieldWidth() {
		return fieldWidth;
	}

	public void setFieldWidth(int fieldWidth) {
		this.fieldWidth = fieldWidth;
	}

	public boolean isLockedInCage() {
		return lockInCage;
	}

	public void setLockInCage(boolean lockInCage) {
		this.lockInCage = lockInCage;
	}

	public Point getPositionOnGameBoard() {
		return positionOnGameBoard;
	}

	public void setPositionOnGameBoard(Point positionOnGameBoard) {
		this.positionOnGameBoard = positionOnGameBoard;
	}

	/**
	 * Interne Hilfsmethode die das Handling des Hintergrundbildes
	 * initialisiert. Mit dem Aufruf wird der �bergebene Bilderpfas geladen und
	 * das Bild als Hintergrund definiert
	 */
	private void initImageHandling(String imageLocation) {
		// Bild laden und als Originalbild abspeichern
		originalImage = SWTResourceManager.getImage(AbstractCarWidget.class,
				imageLocation);

		// Originalbild als aktuelles Bild setzen
		image = originalImage;

		this.imageFilename = imageLocation;

		// Bild in den Cache
		imageCache.addImage(imageFilename, Orientation.NORTH,
				new Point(image.getBounds().width, image.getBounds().height),
				image);
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public AbstractCarWidget(Composite parent, RushHour rushHour, int width,
			int height, String imageLocation, boolean hasSteeringLock,
			boolean isPlayer) {
		super(parent, SWT.NONE);
		mainWindow = rushHour;
		this.player = isPlayer;
		this.length = height;
		this.steeringLock = hasSteeringLock;
		this.fieldHeight = height;
		this.fieldWidth = width;
		initImageHandling(imageLocation);
		setBackgroundImage(image);

		this.setBounds(image.getBounds().x, image.getBounds().y,
				image.getBounds().width, image.getBounds().height);
		this.setBackgroundImage(image);
		setLayout(null);

	}

	public void changeImage(String imageLocation) {
		initImageHandling(imageLocation);
		// setBackgroundImage(image);
		this.setBackgroundImage(getImage(originalImage, orientation, new Point(
				this.getBounds().width, this.getBounds().height)));
		// System.out.println("changeImage->this.getBounds() = " +
		// this.getBounds());
	}

	public Orientation getOrientation() {
		return orientation;
	}

	private Image getImage(Image gImage, Orientation gOrientation, Point gSize) {

		Image newImage;

		if (imageCache.checkCache(gOrientation, gSize)) {
			newImage = imageCache.getImage(imageFilename, gOrientation, gSize);
		} else {
			ImageData imgData = rotateImage(gImage, gOrientation)
					.getImageData();

			imgData = imgData.scaledTo(gSize.x, gSize.y);

			newImage = new Image(this.getDisplay(), imgData);
			imageCache.addImage(imageFilename, gOrientation, gSize, newImage);
		}

		return newImage;
	}

	public void changeOrientation(Orientation newOrientation,
			Point gameFieldSize) {

		this.orientation = newOrientation;
		setSize(gameFieldSize);
		this.setBackgroundImage(getImage(originalImage, orientation, new Point(
				this.getBounds().width, this.getBounds().height)));

	}

	private Image rotateImage(Image rotateableImage, Orientation direction) {
		// Der folgende Code in dieser Methode wurde �bernommen von
		// http://www.java2s.com/Tutorial/Java/0300__SWT-2D-Graphics/Rotateandflipanimage.htm
		// und wurde geringf�gig angepasst
		ImageData imgData = rotateableImage.getImageData();
		int bytesPerPixel = imgData.bytesPerLine / imgData.width;
		int destBytesPerLine = (direction == Orientation.SOUTH) ? imgData.width
				* bytesPerPixel : imgData.height * bytesPerPixel;
		byte[] newData = new byte[imgData.data.length];
		int width = 0, height = 0;
		for (int srcY = 0; srcY < imgData.height; srcY++) {
			for (int srcX = 0; srcX < imgData.width; srcX++) {
				int destX = 0, destY = 0, destIndex = 0, srcIndex = 0;
				switch (direction) {
				case NORTH: // Do Nothing
					return rotateableImage;
				case WEST: // left 90 degrees
					destX = srcY;
					destY = imgData.width - srcX - 1;
					width = imgData.height;
					height = imgData.width;
					break;
				case EAST: // right 90 degrees
					destX = imgData.height - srcY - 1;
					destY = srcX;
					width = imgData.height;
					height = imgData.width;
					break;
				case SOUTH: // 180 degrees
					destX = imgData.width - srcX - 1;
					destY = imgData.height - srcY - 1;
					width = imgData.width;
					height = imgData.height;
					break;
				}
				destIndex = (destY * destBytesPerLine)
						+ (destX * bytesPerPixel);
				srcIndex = (srcY * imgData.bytesPerLine)
						+ (srcX * bytesPerPixel);
				System.arraycopy(imgData.data, srcIndex, newData, destIndex,
						bytesPerPixel);
			}
		}
		ImageData imgData2 = new ImageData(width, height, imgData.depth,
				imgData.palette, destBytesPerLine, newData);

		return new Image(this.getDisplay(), imgData2);

	}

	public void setSize(Point gameFieldSize) {
		int newX;
		int newY;

		if (orientation == Orientation.NORTH
				|| orientation == Orientation.SOUTH) {

			newX = gameFieldSize.x * fieldWidth;
			newY = gameFieldSize.y * fieldHeight;

			this.setBackgroundImage(getImage(originalImage, orientation,
					new Point(newX, newY)));

		} else {
			newX = gameFieldSize.x * fieldHeight;
			newY = gameFieldSize.y * fieldWidth;

			this.setBackgroundImage(getImage(originalImage, orientation,
					new Point(newX, newY)));
		}
		this.setBounds(this.getBounds().x, this.getBounds().y, newX, newY);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public boolean isLockX() {
		return lockX;
	}

	public void setLockX(boolean lockX) {
		this.lockX = lockX;
	}

	public boolean isLockY() {
		return lockY;
	}

	public void setLockY(boolean lockY) {
		this.lockY = lockY;
	}

}
