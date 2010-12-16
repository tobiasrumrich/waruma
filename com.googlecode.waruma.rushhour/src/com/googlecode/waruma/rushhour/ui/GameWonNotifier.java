package com.googlecode.waruma.rushhour.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class GameWonNotifier extends Dialog {

	protected Object result;
	protected Shell shlSpielGewonnen;
	private int countMoves;
	private String time;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GameWonNotifier(Shell parent, String time, int countMoves) {
		super(parent, SWT.APPLICATION_MODAL);
		this.time = time;
		this.countMoves = countMoves;
		setText("Spiel gewonnen!");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlSpielGewonnen.open();
		shlSpielGewonnen.layout();
		Display display = getParent().getDisplay();
		while (!shlSpielGewonnen.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlSpielGewonnen = new Shell(getParent(), getStyle());
		shlSpielGewonnen.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shlSpielGewonnen.setSize(637, 198);
		shlSpielGewonnen.setText("Spiel gewonnen!");
		
		Label lblBild = new Label(shlSpielGewonnen, SWT.NONE);
		lblBild.setBackgroundImage(SWTResourceManager.getImage(GameWonNotifier.class, "/com/googlecode/waruma/rushhour/ui/images/jean_victor_balin_tick_from_OpenClipArt_org.png"));
		lblBild.setBounds(10, 10, 200, 151);
		
		Label lblDuHastDas = new Label(shlSpielGewonnen, SWT.NONE);
		lblDuHastDas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDuHastDas.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.BOLD));
		lblDuHastDas.setBounds(248, 24, 337, 45);
		lblDuHastDas.setText("Das Spiel ist zuende.");
		
		Label lblBentigteZge = new Label(shlSpielGewonnen, SWT.NONE);
		lblBentigteZge.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		lblBentigteZge.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblBentigteZge.setBounds(248, 84, 183, 23);
		lblBentigteZge.setText("Für die Lösung benötige Zeit:");
		
		Label lblFrDieLsung = new Label(shlSpielGewonnen, SWT.NONE);
		lblFrDieLsung.setText("Für die Lösung benötige Züge:");
		lblFrDieLsung.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		lblFrDieLsung.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFrDieLsung.setBounds(248, 113, 183, 23);
		
		Label lblTime = new Label(shlSpielGewonnen, SWT.NONE);
		lblTime.setText(time);
		lblTime.setFont(SWTResourceManager.getFont("Tahoma", 14, SWT.BOLD));
		lblTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTime.setBounds(438, 78, 183, 23);
		
		Label lblMoves = new Label(shlSpielGewonnen, SWT.NONE);
		lblMoves.setText(countMoves + " Züge");
		lblMoves.setFont(SWTResourceManager.getFont("Tahoma", 14, SWT.BOLD));
		lblMoves.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMoves.setBounds(437, 107, 183, 23);
		
		Button btnZurckZumDesigner = new Button(shlSpielGewonnen, SWT.NONE);
		btnZurckZumDesigner.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlSpielGewonnen.dispose();
			}
		});
		btnZurckZumDesigner.setBounds(466, 138, 141, 23);
		btnZurckZumDesigner.setText("Zurück zum Designer");

	}
}
