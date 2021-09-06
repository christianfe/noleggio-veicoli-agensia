package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Contract;
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

public class VeicleReturnController extends ViewUtility
		implements Initializable, DataInitializable<ObjectsCollector<User, Contract>> {

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

	private VeiclesService veiclesService;

	private ObjectsCollector<User, Contract> objectsCollector;
	private ViewDispatcher dispatcher;
	private int mode; // 1: consegna 2: riconsegna

	public VeicleReturnController() {
		this.dispatcher = ViewDispatcher.getInstance();
		veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
	}

	@Override
	public void initializeData(ObjectsCollector<User, Contract> objectsCollector) {
		this.objectsCollector = objectsCollector;
		this.subtitle1Label.setText("Cliete: " + objectsCollector.getObjectB().getCustomer().getName());
		this.subtitle2Label
				.setText(objectsCollector.getObjectB().getStart() + " - " + objectsCollector.getObjectB().getEnd());
		this.subtitle3Label.setText(objectsCollector.getObjectB().isPaid() ? "Noleggio Pagato" : "Noleggio Non Pagato");
		datePicker.setValue(objectsCollector.getObjectB().getEnd());
		this.mode = objectsCollector.getObjectB().getDeliverDateTime() == null ? 1 : 2;
		if (mode == 1)
			this.titleLabel.setText(
					"Gestione Consegna Veicolo '" + objectsCollector.getObjectB().getVeicle().getModel() + "'");
		else if (mode == 2)
			this.titleLabel
					.setText(titleLabel.getText() + " '" + objectsCollector.getObjectB().getVeicle().getModel() + "'");
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.setTimeField(timeField);
		datePicker.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setDisable(item.isBefore(objectsCollector.getObjectB().getEnd())
						|| item.isAfter(objectsCollector.getObjectB().getEnd().plusDays(2)));
			}
		});
	}

	@FXML
	public void saveAction(ActionEvent e) {
		try {
			if (timeField.getText().length() != 5) {
				labelError.setText("Orario non valido, inserire ora nel formato hh:mm");
				e.consume();
				return;
			}
			if (mode == 1) {
				BhertzBusinessFactory.getInstance().getNotificationsService()
						.addNotification(new Notification(objectsCollector.getObjectB().getCustomer(),
								NotificationDictionary.START_RENT_APPOINTMENT_TITLE,
								NotificationDictionary.START_RENT_APPOINTMENT_TEXT + datePicker.getValue() + "  "
										+ timeField.getText()));
				BhertzBusinessFactory.getInstance().getContractService()
						.getContractByID(objectsCollector.getObjectB().getId())
						.setDeliverDateTime(datePicker.getValue() + "  " + timeField.getText());
			} else if (mode == 2) {
				BhertzBusinessFactory.getInstance().getNotificationsService()
						.addNotification(new Notification(objectsCollector.getObjectB().getCustomer(),
								NotificationDictionary.END_RENT_APPOINTMENT_TITLE,
								NotificationDictionary.END_RENT_APPOINTMENT_TEXT + datePicker.getValue() + "  "
										+ timeField.getText()));
				BhertzBusinessFactory.getInstance().getContractService()
						.getContractByID(objectsCollector.getObjectB().getId())
						.setReturnDateTime(datePicker.getValue() + "  " + timeField.getText());
			}

			dispatcher.renderView("rental", objectsCollector.getObjectA());

		} catch (NullPointerException e1) {
			labelError.setText("Impostare data valida");
		}
	}

	@FXML
	public void cancelAction(ActionEvent e) {
		dispatcher.renderView("rental", objectsCollector.getObjectA());
	}
}
