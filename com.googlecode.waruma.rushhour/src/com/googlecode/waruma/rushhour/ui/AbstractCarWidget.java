package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;

public class AbstractCarWidget extends Composite {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public AbstractCarWidget(Composite parent, int style) {
		super(parent, SWT.EMBEDDED);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println("123412341234");
			}
		});
		Image image = SWTResourceManager.getImage(AbstractCarWidget.class,
		"/com/googlecode/waruma/rushhour/ui/images/CAR_blau.png");
		
		this.setBounds(image.getBounds().x,image.getBounds().y,image.getBounds().width,image.getBounds().height);
		this.setBackgroundImage(image);
		setLayout(null);
		System.out.println("X=" + image.getBounds().height + ";Y=" + image.getBounds().width);
		
		
		//this.setBounds(200, 100, 200, 100);
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
