package com.googlecode.waruma.rushhour.ui;

import java.util.HashMap;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import com.googlecode.waruma.rushhour.framework.Orientation;

public class ImageCache implements IImageCache {
	private HashMap<Orientation, HashMap<Point, Image>> internalCache = new HashMap<Orientation, HashMap<Point, Image>>();
	
	public ImageCache () {
		
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.waruma.rushhour.ui.IImageCache#addImage(com.googlecode.waruma.rushhour.framework.Orientation, org.eclipse.swt.graphics.Point, org.eclipse.swt.graphics.Image)
	 */
	@Override
	public void addImage(Orientation orientation, Point point, Image image) {
		if (internalCache.containsKey(orientation)) {
			HashMap<Point, Image> hashMap = internalCache.get(orientation);
			hashMap.put(point, image);
			internalCache.put(orientation, hashMap);	
		}
		else {
			HashMap<Point, Image> hashMap = new HashMap<Point,Image>();
			hashMap.put(point, image);
			internalCache.put(orientation, hashMap);
			//System.out.println("CACHE INFO: Added "+orientation + " with " + point);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.waruma.rushhour.ui.IImageCache#getImage(com.googlecode.waruma.rushhour.framework.Orientation, org.eclipse.swt.graphics.Point)
	 */
	@Override
	public Image getImage(Orientation orientation, Point point) {
		Image imageFromCache;
		
		if (internalCache.containsKey(orientation) && internalCache.get(orientation).containsKey(point)) {
			
			//System.out.println("CACHE INFO: Loaded from cache "+orientation + " with " + point);
			//Es liegt ein Bild im Cache
			return internalCache.get(orientation).get(point);
			
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.waruma.rushhour.ui.IImageCache#checkCache(com.googlecode.waruma.rushhour.framework.Orientation, org.eclipse.swt.graphics.Point)
	 */
	@Override
	public Boolean checkCache(Orientation orientation, Point point) {
		if (internalCache.containsKey(orientation) && internalCache.get(orientation).containsKey(point)) {
			//Es liegt ein Bild im Cache
			return true;
			
		}
		//System.out.println("CACHE INFO: Not found "+orientation + " with " + point);
		return false;
	}
}
