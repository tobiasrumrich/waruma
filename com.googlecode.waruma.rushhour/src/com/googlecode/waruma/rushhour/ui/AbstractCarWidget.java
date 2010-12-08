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
import org.eclipse.swt.widgets.Composite;

import com.googlecode.waruma.rushhour.framework.Orientation;
import com.swtdesigner.SWTResourceManager;

public class AbstractCarWidget extends Composite {

	private Image image;
	private HashMap<Point, Image> image_cache = new HashMap<Point, Image>();
	private Image originalImage;
	private Orientation orientation = Orientation.NORTH;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public AbstractCarWidget(Composite parent, int style) {
		super(parent, SWT.EMBEDDED);
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				setBackgroundImage(resizeImage(new Point(getBounds().width,
						getBounds().height)));
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println("123412341234");
			}
		});

		originalImage = SWTResourceManager.getImage(AbstractCarWidget.class,
				"/com/googlecode/waruma/rushhour/ui/images/car_rot.png");
		image = originalImage;
		// ImageData imgData = image.getImageData();
		// imgData = imgData.scaledTo(400, 300);
		// image = new Image(this.getDisplay(), imgData);

		this.setBounds(image.getBounds().x, image.getBounds().y,
				image.getBounds().width, image.getBounds().height);
		this.setBackgroundImage(image);
		setLayout(null);
		System.out.println("X=" + image.getBounds().height + ";Y="
				+ image.getBounds().width);
	

	}

	
	
	public Orientation getOrientation() {
		return orientation;
	}

	private Image resizeImage(Point newSize) {

		if (image_cache.containsKey(newSize)) {
			System.out.println("FROM CACHE");
			return image_cache.get(newSize);
		}
		System.out.println("NEW BUILD");

		ImageData imgData = originalImage.getImageData();

		imgData = imgData.scaledTo(newSize.x, newSize.y);
		
		

		image = new Image(this.getDisplay(), imgData);
		
		image.getImageData().transparentPixel = 255255255;

		image_cache.put(newSize, image);
		return image;

		// this.setBounds(image.getBounds().x, image.getBounds().y,
		// image.getBounds().width, image.getBounds().height);

	}

	public void changeOrientation(Orientation newOrientation) {
		if ((orientation == Orientation.NORTH || orientation == Orientation.SOUTH)
				&& (newOrientation == Orientation.NORTH || newOrientation == Orientation.SOUTH))  {

			image = rotateImage(newOrientation);
		} else if ((orientation == Orientation.WEST || orientation == Orientation.EAST)
				&& (newOrientation == Orientation.WEST || newOrientation ==Orientation.EAST)){
			image = rotateImage(newOrientation);
		}
		
		else {
			this.setBounds(this.getBounds().x, this.getBounds().y,
					this.getBounds().height, this.getBounds().width);
			image = rotateImage(newOrientation);
		}
		this.orientation = newOrientation;

		
		this.setBackgroundImage(image);
	}

	private Image rotateImage(Orientation direction) {
		ImageData imgData = originalImage.getImageData();
		int bytesPerPixel = imgData.bytesPerLine / imgData.width;
		int destBytesPerLine = (direction == Orientation.SOUTH) ? imgData.width
				* bytesPerPixel : imgData.height * bytesPerPixel;
		byte[] newData = new byte[imgData.data.length];
		int width = 0, height = 0;
		for (int srcY = 0; srcY < imgData.height; srcY++) {
			for (int srcX = 0; srcX < imgData.width; srcX++) {
				int destX = 0, destY = 0, destIndex = 0, srcIndex = 0;
				switch (direction) {
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

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
