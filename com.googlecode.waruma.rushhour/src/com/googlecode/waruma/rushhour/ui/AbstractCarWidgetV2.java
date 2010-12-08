package com.googlecode.waruma.rushhour.ui;

import java.awt.Transparency;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.googlecode.waruma.rushhour.framework.Orientation;
import com.swtdesigner.SWTResourceManager;

public class AbstractCarWidgetV2 extends Composite {

	private final class CanvasListener implements PaintListener {
		private Image transparentIdeaImage;

		public void paintControl(PaintEvent e) {
		    e.gc.drawImage(transparentIdeaImage,0,0);
		   }
	}

	private Image image;
	private HashMap<Point, Image> image_cache = new HashMap<Point, Image>();
	private Image originalImage;
	private Orientation orientation = Orientation.NORTH;
	private Label transparentIdeaLabel;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public AbstractCarWidgetV2(Composite parent, int style) {
		super(parent, SWT.EMBEDDED);
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				transparentIdeaLabel.setImage(resizeImage(new Point(getBounds().width,
						getBounds().height)));
				
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println("123412341234");
			}
		});

		originalImage =  new Image(this.getDisplay(),(new ImageData(
	    	    getClass().getResourceAsStream("/com/googlecode/waruma/rushhour/ui/images/car_rot.gif"))));
		image = originalImage;
		// ImageData imgData = image.getImageData();
		// imgData = imgData.scaledTo(400, 300);
		// image = new Image(this.getDisplay(), imgData);
		
		
	    ImageData ideaData = new ImageData(
	    	    getClass().getResourceAsStream("/com/googlecode/waruma/rushhour/ui/images/car_rot.gif"));
	    
	     int whitePixel = ideaData.palette.getPixel(new RGB(0,0,0));
	     ideaData.transparentPixel = whitePixel;
	        Image transparentIdeaImage = new Image(getDisplay(),ideaData);
	        
	        
	        
	        transparentIdeaLabel = new Label(getShell(),SWT.NONE);
	        transparentIdeaLabel.setImage(transparentIdeaImage);
	           Canvas canvas = new Canvas(getShell(),SWT.NONE);
	           canvas.addPaintListener(new CanvasListener());

	        
		this.setBounds(image.getBounds().x, image.getBounds().y,
				image.getBounds().width, image.getBounds().height);
		transparentIdeaLabel.setImage(image);
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

//	public void changeOrientation(Orientation newOrientation) {
//		if ((orientation == Orientation.NORTH || orientation == Orientation.SOUTH)
//				&& (newOrientation == Orientation.NORTH || newOrientation == Orientation.SOUTH))  {
//
//			image = rotateImage(newOrientation);
//		} else if ((orientation == Orientation.WEST || orientation == Orientation.EAST)
//				&& (newOrientation == Orientation.WEST || newOrientation ==Orientation.EAST)){
//			image = rotateImage(newOrientation);
//		}
//		
//		else {
//			this.setBounds(this.getBounds().x, this.getBounds().y,
//					this.getBounds().height, this.getBounds().width);
//			image = rotateImage(newOrientation);
//		}
//		this.orientation = newOrientation;
//
//		
//		this.setBackgroundImage(image);
//	}



	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
