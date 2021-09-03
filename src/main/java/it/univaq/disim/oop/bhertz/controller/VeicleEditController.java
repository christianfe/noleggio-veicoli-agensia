package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.BigObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VeicleEditController implements Initializable, DataInitializable<BigObjectsCollector<User, Veicle, Type>> {

	@FXML
	private Label labelTitle;
	@FXML
	private TextField modelField;
	@FXML
	private TextField plateField;
	@FXML
	private TextField kmField;
	@FXML
	private TextField consuptionField;
	@FXML
	private Button saveButton;
	
	
	private ViewDispatcher dispatcher;
	private VeiclesService veiclesService;
	private TypesService typeService;
	private BigObjectsCollector<User, Veicle, Type> objectsCollector;
	
	public VeicleEditController() {
		dispatcher= ViewDispatcher.getInstance();
		veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
		typeService = BhertzBusinessFactory.getInstance().getTypesService();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		saveButton.disableProperty().bind(modelField.textProperty().isEmpty().or(plateField.textProperty().isEmpty().or(kmField.textProperty().isEmpty().or(consuptionField.textProperty().isEmpty()))));
	}
	
	@Override
	public void initializeData(BigObjectsCollector<User, Veicle, Type> objectsCollector) {
		this.objectsCollector = objectsCollector;
	}
	
	@FXML
	public void saveAction(ActionEvent e) {
		veiclesService.addVeicle(new Veicle(0, objectsCollector.getObjectC(), modelField.getText(), plateField.getText(), Integer.parseInt(kmField.getText()), Double.parseDouble(consuptionField.getText())));
		dispatcher.renderView("veicles", new ObjectsCollector<User, Type>(objectsCollector.getObjectA(), objectsCollector.getObjectC()));
	}

}
