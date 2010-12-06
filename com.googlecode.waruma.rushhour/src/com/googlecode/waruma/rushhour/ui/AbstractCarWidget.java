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
		super(parent, SWT.NO_BACKGROUND);
		
		Label label = new Label(this, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(232, 232, 232));
		label.setImage(SWTResourceManager.getImage(AbstractCarWidget.class, "/com/googlecode/waruma/rushhour/ui/images/CAR_blau.png"));
		label.setBounds(0, 0, 100, 200);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
