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
		shlSpielGewonnen.setSize(672, 198);
		shlSpielGewonnen.setText("Spiel gewonnen!");
		
		Label lblBild = new Label(shlSpielGewonnen, SWT.NONE);
		lblBild.setBackgroundImage(SWTResourceManager.getImage(GameWonNotifier.class, "/com/googlecode/waruma/rushhour/ui/images/jean_victor_balin_tick_from_OpenClipArt_org.png"));
		lblBild.setBounds(22, 22, 200, 151);
		
		Label lblDuHastDas = new Label(shlSpielGewonnen, SWT.NONE);
		lblDuHastDas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDuHastDas.setFont(SWTResourceManager.getFont("Tahoma", 17, SWT.BOLD));
		lblDuHastDas.setBounds(262, 22, 337, 45);
		lblDuHastDas.setText("Das Spiel ist zuende.");
		
		Label lblBentigteZge = new Label(shlSpielGewonnen, SWT.NONE);
		lblBentigteZge.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		lblBentigteZge.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblBentigteZge.setBounds(262, 82, 183, 23);
		lblBentigteZge.setText("Für die Lösung benötige Zeit:");
		
		Label lblFrDieLsung = new Label(shlSpielGewonnen, SWT.NONE);
		lblFrDieLsung.setText("Für die Lösung benötige Züge:");
		lblFrDieLsung.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		lblFrDieLsung.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFrDieLsung.setBounds(262, 111, 183, 23);
		
		Label lblTime = new Label(shlSpielGewonnen, SWT.NONE);
		lblTime.setText(time);
		lblTime.setFont(SWTResourceManager.getFont("Tahoma", 14, SWT.BOLD));
		lblTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTime.setBounds(452, 76, 183, 23);
		
		Label lblMoves = new Label(shlSpielGewonnen, SWT.NONE);
		lblMoves.setText(countMoves + " Züge");
		lblMoves.setFont(SWTResourceManager.getFont("Tahoma", 14, SWT.BOLD));
		lblMoves.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMoves.setBounds(451, 105, 183, 23);
		
		Button btnZurckZumDesigner = new Button(shlSpielGewonnen, SWT.NONE);
		btnZurckZumDesigner.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlSpielGewonnen.dispose();
			}
		});
		btnZurckZumDesigner.setBounds(519, 163, 141, 23);
		btnZurckZumDesigner.setText("Zurück zum Designer");

	}
}
