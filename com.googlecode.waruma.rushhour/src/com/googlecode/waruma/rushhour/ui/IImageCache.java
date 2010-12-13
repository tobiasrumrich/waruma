package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import com.googlecode.waruma.rushhour.framework.Orientation;

public interface IImageCache {

	public abstract Boolean checkCache(Orientation orientation, Point point);

	void addImage(String filename, Orientation orientation, Point point,
			Image image);

	Image getImage(String filename, Orientation orientation, Point point);

}