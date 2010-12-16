package com.googlecode.waruma.rushhour.ui;

import java.util.HashMap;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import com.googlecode.waruma.rushhour.framework.Orientation;

public class ImageCache implements IImageCache {
	private HashMap<String, HashMap<Orientation, HashMap<Point, Image>>> 
	internalCache =
			new HashMap<String, HashMap<Orientation, HashMap<Point, Image>>>();

	public ImageCache() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.ui.IImageCache#addImage(com.googlecode
	 * .waruma.rushhour.framework.Orientation, org.eclipse.swt.graphics.Point,
	 * org.eclipse.swt.graphics.Image)
	 */
	@Override
	public void addImage(String filename, Orientation orientation, Point point,
			Image image) {
		if (internalCache.containsKey(filename)
				&& internalCache.get(filename).containsKey(orientation)) {
			HashMap<Point, Image> hashMap =
					internalCache.get(filename).get(orientation);
			hashMap.put(point, image);
			internalCache.get(filename).put(orientation, hashMap);
		} else {
			HashMap<Point, Image> hashMap = new HashMap<Point, Image>();
			hashMap.put(point, image);
			if (internalCache.containsKey(filename)) {
				internalCache.get(filename).put(orientation, hashMap);
			} else {
				internalCache.put(filename, new HashMap());
				internalCache.get(filename).put(orientation, hashMap);

			}
			// System.out.println("CACHE INFO: Added "+orientation + " with " +
			// point);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.ui.IImageCache#getImage(com.googlecode
	 * .waruma.rushhour.framework.Orientation, org.eclipse.swt.graphics.Point)
	 */
	@Override
	public Image
			getImage(String filename, Orientation orientation, Point point) {
		Image imageFromCache;

		if (internalCache.containsKey(filename)
				&& internalCache.get(filename).containsKey(orientation)
				&& internalCache.get(orientation).containsKey(point)) {

			// System.out.println("CACHE INFO: Loaded from cache "+orientation +
			// " with " + point);
			// Es liegt ein Bild im Cache
			return internalCache.get(filename).get(orientation).get(point);

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.ui.IImageCache#checkCache(com.googlecode
	 * .waruma.rushhour.framework.Orientation, org.eclipse.swt.graphics.Point)
	 */
	@Override
	public Boolean checkCache(Orientation orientation, Point point) {
		if (internalCache.containsKey(orientation)
				&& internalCache.get(orientation).containsKey(point)) {
			// Es liegt ein Bild im Cache
			return true;

		}
		// System.out.println("CACHE INFO: Not found "+orientation + " with " +
		// point);
		return false;
	}
}
