package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import com.googlecode.waruma.rushhour.framework.Orientation;

public interface IImageCache {

	public abstract void addImage(Orientation orientation, Point point,
			Image image);

	public abstract Image getImage(Orientation orientation, Point point);

	public abstract Boolean checkCache(Orientation orientation, Point point);

}