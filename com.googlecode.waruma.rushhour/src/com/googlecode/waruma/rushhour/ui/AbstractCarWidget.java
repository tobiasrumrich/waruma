package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import com.swtdesigner.SWTResourceManager;

public class AbstractCarWidget extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AbstractCarWidget(Composite parent, int style) {
		super(parent, SWT.NO_BACKGROUND | SWT.NO_MERGE_PAINTS | SWT.EMBEDDED);
		
		Label Car = new Label(this, SWT.CENTER);
		Car.setEnabled(false);
		Car.setBackground(SWTResourceManager.getColor(232, 232, 232));
		Car.setImage(SWTResourceManager.getImage(AbstractCarWidget.class, "/com/googlecode/waruma/rushhour/ui/images/CAR_blau.png"));
		Car.setBounds(0, 0, 150, 150);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
