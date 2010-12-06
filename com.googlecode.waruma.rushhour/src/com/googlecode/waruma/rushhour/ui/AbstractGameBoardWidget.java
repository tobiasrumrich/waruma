package com.googlecode.waruma.rushhour.ui;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.layout.GridData;

public class AbstractGameBoardWidget extends Composite {

	private Label[][] spielbrett;
	private int hoehe;
	private int breite;
	private int minHoeheFeld = 50;
	private int minBreiteFeld = 50;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @param breite 
	 * @param hoehe 
	 */
	public AbstractGameBoardWidget(Composite parent, int style, int breite, int hoehe) {
		super(parent, style);
		
		this.hoehe = hoehe;
		this.breite = breite;
		System.out.println(breite+"++++++");
		spielbrett = new Label[hoehe][breite];
		setLayout(new GridLayout(breite, false));
		((GridLayout) this.getLayout()).horizontalSpacing = 10;
		((GridLayout) this.getLayout()).verticalSpacing = 10;
		
	
				
		
		
		
		for (int i = 0; i < hoehe; i++) {
			for (int j = 0; j < breite; j++) {
				spielbrett[i][j] = new Label(this, SWT.NONE);
				spielbrett[i][j].setText(""+i + "" + j);
				spielbrett[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
				
				if ((i+j) % 2 == 0) {
					spielbrett[i][j].setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));	
				} else {
					spielbrett[i][j].setBackground(SWTResourceManager.getColor(119, 136, 153));	
				}
			}
			
		}
	}
	
	
	public int getMinHeight (){
		return (hoehe * minHoeheFeld)+((hoehe-1)*((GridLayout) this.getLayout()).horizontalSpacing);
	}
	
	public int getMinWidth (){
		return (breite*minHoeheFeld) + ((breite-1)*((GridLayout) this.getLayout()).verticalSpacing);
	}
	
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
