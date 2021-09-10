package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VeicleEditController extends ViewUtility
implements Initializable, DataInitializable<BigObjectsCollector<User, Veicle, Type>> {

	@FXML
	private Label labelTitle;
	@FXML
	private Label labelError;
	@FXML
	private TextField modelField;
	@FXML
	private TextField plateField;
	@FXML
	private TextField kmField;
	@FXML
	private TextField consuptionField;
	@FXML
	private TextField fuelField;
	@FXML
	private Button saveButton;

	private ViewDispatcher dispatcher;
	private VeiclesService veiclesService;
	private BigObjectsCollector<User, Veicle, Type> objectsCollector;
	private boolean creatingNewVeicle;

	public VeicleEditController() {
		dispatcher = ViewDispatcher.getInstance();
		veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		saveButton.disableProperty()
		.bind(modelField.textProperty().isEmpty()
				.or(plateField.textProperty().isEmpty().or(kmField.textProperty().isEmpty()
						.or(consuptionField.textProperty().isEmpty().or(fuelField.textProperty().isEmpty())))));
		super.addForbiddenCharCheck(modelField, plateField, kmField, consuptionField, fuelField);
		//super.setOnlyNumberField(kmField, consuptionField);
	}

	@Override
	public void initializeData(BigObjectsCollector<User, Veicle, Type> objectsCollector) {
		this.objectsCollector = objectsCollector;
		this.creatingNewVeicle = objectsCollector.getObjectB() == null;
		if (!creatingNewVeicle) {
			labelTitle.setText("Modifica Veicolo: " + objectsCollector.getObjectB().getPlate());
			plateField.setDisable(true);
			this.modelField.setText(objectsCollector.getObjectB().getModel());
			this.plateField.setText(objectsCollector.getObjectB().getPlate());
			this.kmField.setText("" + objectsCollector.getObjectB().getKm());
			this.consuptionField.setText("" + objectsCollector.getObjectB().getConsumption());
			this.fuelField.setText("" + objectsCollector.getObjectB().getFuel());

		}
	}

	@FXML
	public void saveAction(ActionEvent e) {
		try {

			try {
			
			if (this.creatingNewVeicle)
				veiclesService.addVeicle(new Veicle(0, objectsCollector.getObjectC(), modelField.getText(),
						plateField.getText(), Double.parseDouble(kmField.getText()),
						Double.parseDouble(consuptionField.getText()), fuelField.getText()));

			else
				veiclesService.setVeicle(objectsCollector.getObjectB().getId(), modelField.getText(),
						Double.parseDouble(kmField.getText()), Double.parseDouble(consuptionField.getText()),
						fuelField.getText());
			
			dispatcher.renderView("veicles", new ObjectsCollector<User, Type>(objectsCollector.getObjectA(), objectsCollector.getObjectC()));
			
			} catch (NumberFormatException exception) {
				labelError.setText("input numerico non valido!");
			}
		
		
		} catch (BusinessException e1) {
			dispatcher.renderError(e1);
		}
		
	}

}
