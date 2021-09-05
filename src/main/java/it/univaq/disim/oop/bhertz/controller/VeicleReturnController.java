package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.Notification;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.NotificationDictionary;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VeicleReturnController extends ViewUtility implements Initializable, DataInitializable<ObjectsCollector<User, Contract>> {

	@FXML
	private Label titleLabel;
	@FXML
	private Label subtitle1Label;
	@FXML
	private Label subtitle2Label;
	@FXML
	private Label subtitle3Label;
	@FXML
	private DatePicker datePicker;
	@FXML
	private TextField timeField;
	@FXML
	private Button saveButton;
	@FXML
	private Label labelError;

	private ObjectsCollector<User, Contract> objectsCollector;
	private ViewDispatcher dispatcher;

	public VeicleReturnController() {
		this.dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initializeData(ObjectsCollector<User, Contract> objectsCollector) {
		this.objectsCollector = objectsCollector;
		this.titleLabel.setText(titleLabel.getText() + "''" + objectsCollector.getObjectB().getVeicle().getModel() + "''");
		this.subtitle1Label.setText("Cliete: " + objectsCollector.getObjectB().getCustomer().getName());
		this.subtitle2Label.setText(objectsCollector.getObjectB().getStart() + " - " + objectsCollector.getObjectB().getEnd());
		this.subtitle2Label.setText(objectsCollector.getObjectB().isPaid() ? "Contratto Pagato" : "Contratto Non Pagato");
		
		datePicker.setValue(objectsCollector.getObjectB().getEnd());
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.setTimeField(timeField);
		datePicker.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setDisable(item.isBefore(objectsCollector.getObjectB().getEnd()));
			}});
	}

	@FXML	
	public void saveAction(ActionEvent e) {
		try {
			objectsCollector.getObjectB().setState(ContractState.ENDED);
			BhertzBusinessFactory.getInstance().getContractService().setContract(objectsCollector.getObjectB());
			BhertzBusinessFactory.getInstance().getNotificationsService().addNotification(new Notification(objectsCollector.getObjectB().getCustomer(), NotificationDictionary.END_RENT_APPOINTMENT_TITLE, NotificationDictionary.END_RENT_APPOINTMENT_TEXT + datePicker.getValue() + timeField.getText()));
			dispatcher.renderView("rental", objectsCollector.getObjectA());
		}catch (NullPointerException e1) {
			labelError.setText("Impostare data valida");
		}
	}
}
