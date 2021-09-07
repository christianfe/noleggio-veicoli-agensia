package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class MaintenanceManagementController extends ViewUtility
	implements Initializable, DataInitializable<ObjectsCollector<User, AssistanceTicket>> {
	
	@FXML
	private TextArea infoArea;
	@FXML
	private TableView<Veicle> veicleTable;
	@FXML
	private TableColumn<Veicle, String> veicleColumn; 
	@FXML
	private TableColumn<Veicle, String> plateColumn;
	@FXML
	private TableColumn<Veicle, String> fuelColumn;
	@FXML
	private TableColumn<Veicle, String> consumptionColumn;
	@FXML
	private TableColumn<Veicle, Button> actionColumn;
	
	private ViewDispatcher dispatcher;
	private User user;
	private AssistanceTicket ticket;
	
	public MaintenanceManagementController() {
		dispatcher = ViewDispatcher.getInstance();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeData(ObjectsCollector<User, AssistanceTicket> collector) {
		this.user = collector.getObjectA();
		this.ticket = collector.getObjectB();
		
	}
	
	
	@FXML
	public void fixedVeicle() {}
	
	@FXML
	public void exitAction() {
		dispatcher.renderView("maintenance", user);
	}
	
	
}
