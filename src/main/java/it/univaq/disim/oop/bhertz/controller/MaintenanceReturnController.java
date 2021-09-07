package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MaintenanceReturnController extends ViewUtility
		implements Initializable, DataInitializable<ObjectsCollector<User, AssistanceTicket>> {

	@FXML
	private Label veicleLabel;
	@FXML
	private TextArea descriptionArea;
	@FXML
	private Label kmLabel;
	@FXML
	private TextField newKmField;
	@FXML
	private Label errorLabel;
	@FXML
	private Button confirmButton;

	private ViewDispatcher dispatcher;
	private User user;
	private AssistanceTicket ticket;

	public MaintenanceReturnController() {
		this.dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	@Override
	public void initializeData(ObjectsCollector<User, AssistanceTicket> collector) {
		this.user = collector.getObjectA();
		this.ticket = collector.getObjectB();

		confirmButton.disableProperty()
				.bind(newKmField.textProperty().isEmpty().or(descriptionArea.textProperty().isEmpty()));

		veicleLabel.setText("Veicolo: " + ticket.getContract().getVeicle().getModel() + " - "
				+ ticket.getContract().getVeicle().getPlate());
		kmLabel.setText(kmLabel.getText() + ticket.getContract().getStartKm());

	}

	@FXML
	public void saveAction() {
		ticket.setState(TicketState.WORKING);
		ticket.setDescription(descriptionArea.getText());
		ticket.setVeicleKm(Double.parseDouble(newKmField.getText()));
		dispatcher.renderView("maintenance", this.user);
	}

	@FXML
	public void exitAction() {
		dispatcher.renderView("maintenance", this.user);
	}

}
