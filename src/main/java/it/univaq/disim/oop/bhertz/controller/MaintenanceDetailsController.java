package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class MaintenanceDetailsController extends ViewUtility implements Initializable, DataInitializable<ObjectsCollector<User, AssistanceTicket>>{
	
	@FXML
	private Label titleLabel;
	@FXML
	private Label veicleInfoLabel;
	@FXML
	private Label startAssistanceLabel;
	@FXML
	private Label endAssistanceLabel;
	@FXML
	private Label newVeicleTitle;
	@FXML
	private Label newVeicleInfo;
	@FXML
	private CheckBox newVeicleCheck;
	@FXML
	private TextArea descriptionArea;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;
	
	private ViewDispatcher dispatcher;
	private MaintenanceService maintenanceService;
	private ObjectsCollector<User, AssistanceTicket> objectsCollector;
	private boolean newVeicleActive;

	public MaintenanceDetailsController() {
		this.dispatcher = ViewDispatcher.getInstance();
		this.maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.addForbiddenCharCheck(descriptionArea);
	}
	
	@Override
	public void initializeData(ObjectsCollector<User, AssistanceTicket> objectsCollector) {
		this.objectsCollector = objectsCollector;
		this.newVeicleActive = objectsCollector.getObjectB().getSubstituteContract() != null;
		this.newVeicleCheck.setSelected(newVeicleActive);
		this.descriptionArea.setText(objectsCollector.getObjectB().getDescription());
		this.veicleInfoLabel.setText(objectsCollector.getObjectB().getContract().getVeicle() + " - al km " + objectsCollector.getObjectB().getVeicleKm());
		if (objectsCollector.getObjectB().getStartDate() == null)
			this.startAssistanceLabel.setText(startAssistanceLabel.getText() + ": Non definito");
		else
			this.startAssistanceLabel.setText(startAssistanceLabel.getText() + ": " + objectsCollector.getObjectB().getStartDate() + " " + objectsCollector.getObjectB().getTimeStart());
		if (objectsCollector.getObjectB().getEndDate() == null)
			this.endAssistanceLabel.setText(endAssistanceLabel.getText() + ": Non definito");
		else
			this.endAssistanceLabel.setText(endAssistanceLabel.getText() + ": " + objectsCollector.getObjectB().getEndDate() + " " + objectsCollector.getObjectB().getTimeEnd());
		if (!newVeicleActive) {
			newVeicleTitle.setText("");
			newVeicleInfo.setText("");
		} else {
			newVeicleTitle.setText(objectsCollector.getObjectB().getSubstituteContract().getVeicle().toString());
			newVeicleInfo.setText("Cliente: " + objectsCollector.getObjectB().getContract().getCustomer().getName());
		}
		if (objectsCollector.getObjectA().getRole() != 1 || objectsCollector.getObjectB().getState() == TicketState.ENDED) {
			descriptionArea.setEditable(false);
			saveButton.setVisible(false);
		}
	}
	
	@FXML
	public void newVeicleCheckAction(ActionEvent e) {
		newVeicleCheck.setSelected(newVeicleActive);
	}
	
	@FXML
	public void saveAction(ActionEvent e) {
		AssistanceTicket t = objectsCollector.getObjectB();
		t.setDescription(descriptionArea.getText());
		try {
			maintenanceService.setTicket(t);
		} catch (BusinessException e1) {
			dispatcher.renderError(e1);
		}
		dispatcher.renderView("maintenance", objectsCollector.getObjectA());
	}
	
	@FXML
	public void cancelAction(ActionEvent e) {
		dispatcher.renderView("maintenance", objectsCollector.getObjectA());
	}
	

}
