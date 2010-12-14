package com.googlecode.waruma.rushhour.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class AbstractDesignerWidget extends Composite {

	private AbstractCarWidget designerPreviewCar;
	private final RushHour mainWindow;
	private Combo carTypeComboBox;
	private Combo carImageComoBox;
	protected String[] data;
	private String[] labels;
	private Composite carCreationComposite;
	private Button lenkradschloss;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public AbstractDesignerWidget(final RushHour mainWindow, Composite parent,
			int style) {
		super(parent, style);
		this.setLayout(new GridLayout(2, false));
		this.mainWindow = mainWindow;

		carCreationComposite = new Composite(this, SWT.NONE);
		carCreationComposite.setLayout(null);
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, true, false,
				2, 1);
		gd_composite.heightHint = 282;
		carCreationComposite.setLayoutData(gd_composite);

		designerPreviewCar = new AbstractCarWidget(carCreationComposite, mainWindow, 11, 3,
				RushHour.IMAGEBASEPATH + "2F_car_Peterwagen_carimg.png", false, false);
		designerPreviewCar.setBounds(68, 22, 100, 200);

		designerPreviewCar.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				String imageFileName = data[carImageComoBox.getSelectionIndex()];

				int carLength = 2;

				if(carTypeComboBox.getSelectionIndex() == 1)
					carLength = 3;
				
				boolean isPlayer = false;
				
				if(carTypeComboBox.getSelectionIndex() == 2)
					isPlayer = true;
				
				AbstractCarWidget newCarFromDesigner = new AbstractCarWidget(
						mainWindow.shell, mainWindow, 1, carLength, RushHour.IMAGEBASEPATH
								+ imageFileName, lenkradschloss.getSelection(), isPlayer);
				
				newCarFromDesigner.moveAbove(mainWindow.mainComposite);
				
				if(carTypeComboBox.getSelectionIndex() == 2){
					newCarFromDesigner.addMouseListener(new PlayerCarMouseListener(
							mainWindow, newCarFromDesigner));
				}else{
					newCarFromDesigner.addMouseListener(new CarMouseListener(
						mainWindow, newCarFromDesigner));
				}
				
				newCarFromDesigner.setSize(mainWindow.abstractGameBoardWidget
						.getCurrentFieldSize());
				newCarFromDesigner.setLocation(
						arg0.x + designerPreviewCar.getLocation().x
								+ mainWindow.tabFolder.getLocation().x,

						arg0.y + designerPreviewCar.getLocation().y
								+ mainWindow.tabFolder.getLocation().y);
				mainWindow.carPool.add(newCarFromDesigner);
			}

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});

		Label lblOrientierung = new Label(this, SWT.NONE);
		lblOrientierung.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblOrientierung.setText("Fahrzeugtyp");

		carTypeComboBox = new Combo(this, SWT.READ_ONLY);
		carTypeComboBox.setItems(new String[] { "PKW (belegt 2 Felder)",
				"LKW (belegt 3 Felder)", "Spieler (belegt 2 Felder)" });
		carTypeComboBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		carTypeComboBox.select(0);

		Label lblAussehen = new Label(this, SWT.NONE);
		lblAussehen.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblAussehen.setText("Aussehen");

		carImageComoBox = new Combo(this, SWT.READ_ONLY);

		carImageComoBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String imageFileName = data[carImageComoBox.getSelectionIndex()];

				designerPreviewCar.changeImage(RushHour.IMAGEBASEPATH
						+ imageFileName);
			}
		});

		carTypeComboBox.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				List<ImageBean> images;
				String[] labels = { "not found" };

				switch (carTypeComboBox.getSelectionIndex()) {
				case 0:
					images = mainWindow.availableCars;
					labels = new String[images.size()];
					data = new String[images.size()];
					for (int i = 0; i < images.size(); i++) {
						labels[i] = images.get(i).getCarName();
						data[i] = images.get(i).getFilename();
					}
					designerPreviewCar.setSize(new Point(100, 200));
					designerPreviewCar.setBounds(
							designerPreviewCar.getBounds().x,
							designerPreviewCar.getBounds().y, 100, 200);
					designerPreviewCar.changeImage(RushHour.IMAGEBASEPATH
							+ data[0]);

					break;
				case 1:
					images = mainWindow.availableTrucks;
					labels = new String[images.size()];
					data = new String[images.size()];
					for (int i = 0; i < images.size(); i++) {
						labels[i] = images.get(i).getCarName();
						data[i] = images.get(i).getFilename();
					}
					designerPreviewCar.setSize(new Point(67, 200));
					designerPreviewCar.setBounds(
							designerPreviewCar.getBounds().x,
							designerPreviewCar.getBounds().y, 67, 200);
					designerPreviewCar.changeImage(RushHour.IMAGEBASEPATH
							+ data[0]);
					break;
				case 2:
					images = mainWindow.availablePlayers;
					labels = new String[images.size()];
					data = new String[images.size()];
					for (int i = 0; i < images.size(); i++) {
						labels[i] = images.get(i).getCarName();
						data[i] = images.get(i).getFilename();
					}
					designerPreviewCar.setSize(new Point(100, 200));
					designerPreviewCar.setBounds(
							designerPreviewCar.getBounds().x,
							designerPreviewCar.getBounds().y, 100, 200);
					designerPreviewCar.changeImage(RushHour.IMAGEBASEPATH
							+ data[0]);
					break;

				}
				carImageComoBox.setItems(labels);
				carImageComoBox.select(0);
				designerPreviewCar.changeImage(RushHour.IMAGEBASEPATH
						+ data[carImageComoBox.getSelectionIndex()]);

			}
		});

		List<ImageBean> availableImages = mainWindow.availableCars;
		labels = new String[availableImages.size()];
		data = new String[availableImages.size()];

		for (int i = 0; i < availableImages.size(); i++) {
			labels[i] = availableImages.get(i).getCarName();
			data[i] = availableImages.get(i).getFilename();
		}

		carImageComoBox.setItems(labels);
		carImageComoBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		carImageComoBox.select(0);
		designerPreviewCar.setSize(new Point(100, 100));
		designerPreviewCar.setBounds(designerPreviewCar.getBounds().x,
				designerPreviewCar.getBounds().y, 100, 200);
		designerPreviewCar.changeImage(RushHour.IMAGEBASEPATH + data[0]);

		Label label_2 = new Label(this, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));


		lenkradschloss = new Button(this, SWT.CHECK);
		lenkradschloss.setText("Lenkradschloss");
		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		new Label(this, SWT.NONE);

	}
	
	
	protected void addPlayerCarToCombobox() {
		carTypeComboBox.setItems(new String[] { "PKW (belegt 2 Felder)",
				"LKW (belegt 3 Felder)", "Spieler (belegt 2 Felder)"});
		carTypeComboBox.select(0);
	}
	
	protected void removePlayerCarToCombobox(){
		carTypeComboBox.setItems(new String[] { "PKW (belegt 2 Felder)",
				"LKW (belegt 3 Felder)"});
		carTypeComboBox.select(0);
		
		List<ImageBean> images = mainWindow.availableCars;
		labels = new String[images.size()];
		data = new String[images.size()];
		for (int i = 0; i < images.size(); i++) {
			labels[i] = images.get(i).getCarName();
			data[i] = images.get(i).getFilename();
		}
		designerPreviewCar.changeImage(RushHour.IMAGEBASEPATH
				+ data[0]);
		
		carImageComoBox.setItems(labels);
		carImageComoBox.select(0);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
