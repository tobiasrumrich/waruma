package com.googlecode.waruma.rushhour.ui;

import java.util.HashMap;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;

public class AbstractCarWidget extends Composite {

	private Image image;

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
				setBackgroundImage(resizeImage(new Point(getBounds().width,getBounds().height)));
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println("123412341234");
			}
		});

		image = SWTResourceManager.getImage(AbstractCarWidget.class,
				"/com/googlecode/waruma/rushhour/ui/images/CAR_blau.png");

		//ImageData imgData = image.getImageData();
		//imgData = imgData.scaledTo(400, 300);
		//image = new Image(this.getDisplay(), imgData);

		this.setBounds(image.getBounds().x, image.getBounds().y,
				image.getBounds().width, image.getBounds().height);
		this.setBackgroundImage(image);
		setLayout(null);
		System.out.println("X=" + image.getBounds().height + ";Y="
				+ image.getBounds().width);

	}

	private Image resizeImage(Point newSize) {
		HashMap<Point, Image> image_cache = new HashMap<Point, Image>();

		if (image_cache.containsKey(newSize)) {
			return image_cache.get(newSize);
		}

		ImageData imgData = image.getImageData();

		imgData = imgData.scaledTo(newSize.x, newSize.y);
		image = new Image(this.getDisplay(), imgData);

		image_cache.put(newSize, image);
		return image;
		
		//this.setBounds(image.getBounds().x, image.getBounds().y,
		//		image.getBounds().width, image.getBounds().height);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
