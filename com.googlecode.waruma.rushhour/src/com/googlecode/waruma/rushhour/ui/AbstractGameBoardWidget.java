package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.swtdesigner.SWTResourceManager;

public class AbstractGameBoardWidget extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AbstractGameBoardWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
				
		

		
		Label lblA = new Label(this, SWT.NONE);
		lblA.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lblA.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		
		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		
		Label labe2 = new Label(this, SWT.NONE);
		labe2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		labe2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		
		Label label4 = new Label(this, SWT.NONE);
		label4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		label4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		
		Label label3 = new Label(this, SWT.NONE);
		label3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		label3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		
		Label label5 = new Label(this, SWT.NONE);
		label5.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		label5.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		


	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
