package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ChangeCotractStateConstructor extends ViewUtility
		implements Initializable, DataInitializable<ObjectsCollector<User, Contract>> {

	@FXML
	private Label errorLabel;
	@FXML
	private Label titleLabel;
	@FXML
	private Label kmLabel;
	@FXML
	private Label labelError;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField kmField;

	private ViewDispatcher dispatcher;
	private ContractService contracService;
	private VeiclesService veicleService;
	private ObjectsCollector<User, Contract> objectsCollector;

	public ChangeCotractStateConstructor() {
		this.dispatcher = ViewDispatcher.getInstance();
		this.contracService = BhertzBusinessFactory.getInstance().getContractService();
		this.veicleService = BhertzBusinessFactory.getInstance().getVeiclesService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		saveButton.disableProperty().bind(kmField.textProperty().isEmpty());
	}

	@Override
	public void initializeData(ObjectsCollector<User, Contract> objectsCollector) {
		this.objectsCollector = objectsCollector;
		kmLabel.setText("Ultima lettura km: " + objectsCollector.getObjectB().getVeicle().getKm());
		kmField.setText("" + objectsCollector.getObjectB().getVeicle().getKm());
		titleLabel.setText(titleLabel.getText() + objectsCollector.getObjectB().getVeicle().getModel());
	}

	@FXML
	public void saveAction(ActionEvent e) {

		try {

			double d = Double.parseDouble(kmField.getText());
			if (d < objectsCollector.getObjectB().getVeicle().getKm()) {
				labelError.setText("Input chilometri non valido!");
				return;
			}

			Contract c = objectsCollector.getObjectB();

			if (objectsCollector.getObjectB().getState() != ContractState.MAINTENANCE) {
				if (c.getReturnDateTime() == null) {
					c.setStartKm(d);
					c.setState(ContractState.ACTIVE);
				} else {
					c.setEndKm(d);
					c.setState(ContractState.ENDED);
				}

			}
			Veicle v = c.getVeicle();
			v.setKm(d);
			try {
				veicleService.setVeicle(v);
				contracService.setContract(c);
			} catch (BusinessException e1) {
				dispatcher.renderError(e1);
			}

			if (!(objectsCollector.getObjectB().isSostistuteContract()))
				dispatcher.renderView("rental", objectsCollector.getObjectA());
			else
				dispatcher.renderView("maintenance", objectsCollector.getObjectA());
		} catch (NumberFormatException exception) {

			labelError.setText("Input chilometri non valido!");
			/*
			 * in tutti quei campi di testo in cui occorreva inserire un numero( modifica
			 * km, modifica tariffe ecc ) abbiamo gestito gli input errati con questa
			 * eccezione
			 */

		}

	}

	@FXML
	public void cancelAction(ActionEvent e) {
		dispatcher.renderView("rental", objectsCollector.getObjectA());
	}
}
