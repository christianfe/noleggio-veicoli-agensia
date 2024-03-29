package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.Notification;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AppointmentManagerController extends ViewUtility
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

	private ObjectsCollector<User, Contract> objectsCollector;
	private ViewDispatcher dispatcher;
	private ContractService contractService;
	private MaintenanceService maintenanceService;
	private int mode; // 1: consegna; 2: riconsegna; 3: gestione manutenzione; 4: appuntamento fine
	// manutenzione

	public AppointmentManagerController() {
		this.dispatcher = ViewDispatcher.getInstance();
		this.contractService = BhertzBusinessFactory.getInstance().getContractService();
		this.maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();
	}

	@Override
	public void initializeData(ObjectsCollector<User, Contract> objectsCollector) {
		this.objectsCollector = objectsCollector;

		if (objectsCollector.getObjectB().getState() == ContractState.MAINTENANCE)
			this.mode = 3;
		else
			this.mode = objectsCollector.getObjectB().getDeliverDateTime() == null ? 1 : 2;

		try {
			if (objectsCollector.getObjectB().getAssistance().getState() == TicketState.READY)
				this.mode = 4;
		} catch (NullPointerException e) {

		}

		switch (this.mode) {
			case 1:
				this.titleLabel.setText(
						"Gestione Consegna Veicolo '" + objectsCollector.getObjectB().getVeicle().getModel() + "'");
				break;
			case 2:
				this.titleLabel.setText(
						titleLabel.getText() + " '" + objectsCollector.getObjectB().getVeicle().getModel() + "'");
				break;
			case 3:

				this.titleLabel.setText(
						"Gestione Assistenza Veicolo '" + objectsCollector.getObjectB().getVeicle().getModel() + "'");
				break;
		}

		this.subtitle1Label.setText("Cliete: " + objectsCollector.getObjectB().getCustomer().getName());
		this.subtitle2Label
				.setText(objectsCollector.getObjectB().getStart() + " - " + objectsCollector.getObjectB().getEnd());
		this.subtitle3Label.setText(objectsCollector.getObjectB().isPaid() ? "Noleggio Pagato" : "Noleggio Non Pagato");
		datePicker.setValue(objectsCollector.getObjectB().getEnd());

		super.setTimeField(timeField);
		datePicker.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);

				switch (mode) {
					case 1:
						setDisable(item.isBefore(objectsCollector.getObjectB().getStart())
								|| item.isAfter(objectsCollector.getObjectB().getStart().plusDays(2))
								|| item.isAfter(objectsCollector.getObjectB().getEnd()));
						break;
					case 2:
						setDisable(item.isBefore(objectsCollector.getObjectB().getEnd())
								|| item.isAfter(objectsCollector.getObjectB().getEnd().plusDays(2)));
						break;
					case 3:
						setDisable(item.isBefore(objectsCollector.getObjectB().getAssistance().getStartDate())
								|| item.isAfter(
										objectsCollector.getObjectB().getAssistance().getStartDate().plusDays(2))
								|| item.isAfter(objectsCollector.getObjectB().getEnd()));
						break;
					case 4:
						setDisable(item.isBefore(objectsCollector.getObjectB().getAssistance().getStartDate())
								|| item.isAfter(
										objectsCollector.getObjectB().getAssistance().getStartDate().plusDays(2))
								|| item.isAfter(objectsCollector.getObjectB().getEnd()));
						break;

				}

			}
		});

	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void saveAction(ActionEvent e) {
		try {
			if (timeField.getText().length() != 5) {
				labelError.setText("Orario non valido, inserire ora nel formato hh:mm");
				e.consume();
				return;
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			Contract c;
			AssistanceTicket t;
			switch (this.mode) {
				case 1:
					BhertzBusinessFactory.getInstance().getNotificationsService()
							.addNotification(new Notification(objectsCollector.getObjectB().getCustomer(),
									NotificationDictionary.START_RENT_APPOINTMENT_TITLE,
									NotificationDictionary.START_RENT_APPOINTMENT_TEXT
											+ datePicker.getValue().format(formatter) + "  " + timeField.getText()));
					c = contractService.getContractByID(objectsCollector.getObjectB().getId());
					c.setDeliverDateTime(datePicker.getValue().format(formatter) + "  " + timeField.getText());
					contractService.setContract(c);
					dispatcher.renderView("rental", objectsCollector.getObjectA());
					break;
				case 2:
					BhertzBusinessFactory.getInstance().getNotificationsService()
							.addNotification(new Notification(objectsCollector.getObjectB().getCustomer(),
									NotificationDictionary.END_RENT_APPOINTMENT_TITLE,
									NotificationDictionary.END_RENT_APPOINTMENT_TEXT
											+ datePicker.getValue().format(formatter) + "  " + timeField.getText()));

					c = this.contractService.getContractByID(objectsCollector.getObjectB().getId());
					c.setReturnDateTime(datePicker.getValue().format(formatter) + "  " + timeField.getText());
					contractService.setContract(c);

					dispatcher.renderView("rental", objectsCollector.getObjectA());
					break;
				case 3:
					t = objectsCollector.getObjectB().getAssistance();
					t.setStartDate(LocalDate.now());
					t.setTimeStart(timeField.getText());
					maintenanceService.setTicket(t);
					BhertzBusinessFactory.getInstance().getNotificationsService()
							.addNotification(new Notification(objectsCollector.getObjectB().getCustomer(),
									NotificationDictionary.START_MAINTENANCE_APPOINTMENT_TITLE,
									NotificationDictionary.START_MAINTENANCE_APPOINTMENT_TEXT
											+ datePicker.getValue().format(formatter) + "  " + timeField.getText()));
					dispatcher.renderView("maintenance", objectsCollector.getObjectA());
					break;
				case 4:
					t = objectsCollector.getObjectB().getAssistance();
					t.setEndDate(datePicker.getValue());
					t.setTimeEnd(timeField.getText());
					maintenanceService.setTicket(t);
					String text;
					if (objectsCollector.getObjectB().getAssistance().getSubstituteContract() == null)
						text = NotificationDictionary.END_MAINTENANCE_APPOINTMENT_TEXT;
					else
						text = NotificationDictionary.END_MAINTENANCE_APPOINTMENT_TEXT_SUBSTITUTE;

					BhertzBusinessFactory.getInstance().getNotificationsService()
							.addNotification(new Notification(objectsCollector.getObjectB().getCustomer(),
									NotificationDictionary.END_MAINTENANCE_APPOINTMENT_TITLE,
									text + datePicker.getValue().format(formatter) + "  " + timeField.getText()));
					dispatcher.renderView("maintenance", objectsCollector.getObjectA());
					break;
			}

		} catch (NullPointerException e1) {
			labelError.setText("Impostare data valida");
		} catch (BusinessException e1) {
			dispatcher.renderError(e1);
		}
	}

	@FXML
	public void cancelAction(ActionEvent e) {
		if (mode == 3)
			dispatcher.renderView("maintenance", objectsCollector.getObjectA());
		else
			dispatcher.renderView("rental", objectsCollector.getObjectA());
	}
}
