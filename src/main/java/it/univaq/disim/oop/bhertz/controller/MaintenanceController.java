package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMMaintenanceServiceImpl;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;

public class MaintenanceController implements Initializable, DataInitializable<User>{

	@FXML
	private Label titleLabel;
	@FXML
	private TableView<AssistanceTicket> maintenanceTable;
	@FXML
	private TableColumn<AssistanceTicket, String> userColumn;
	@FXML
	private TableColumn<AssistanceTicket, String> veicleColumn;
	@FXML
	private TableColumn<AssistanceTicket, String> stateColumn;
	@FXML
	private TableColumn<AssistanceTicket, Button> actionColumn;
	
	private ViewDispatcher dispatcher;
	private MaintenanceService maintenanceService;
	
	public MaintenanceController() {
		dispatcher = ViewDispatcher.getInstance();
		try {
			maintenanceService = new RAMMaintenanceServiceImpl();
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userColumn.setCellValueFactory((CellDataFeatures<AssistanceTicket, String> param) -> {
			return new SimpleStringProperty(param.getValue().getContract().getCustomer().getName());
		});
		veicleColumn.setCellValueFactory((CellDataFeatures<AssistanceTicket, String> param) -> {
			return new SimpleStringProperty(param.getValue().getContract().getVeicle().getModel());
		});
		/*stateColumn.setCellValueFactory((CellDataFeatures<AssistanceTicket, String> param) -> {
			return new SimpleStringProperty(param.getValue().getContract());
		});*/
		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<AssistanceTicket, Button> param) -> {
			final Button button = new Button("Seleziona");

			button.setOnAction((ActionEvent event) -> {
				//dispatcher.renderView("veicles", param.getValue());
			});
			return new SimpleObjectProperty<Button>(button);
		});
	}
	
	@Override
	public void initializeData(User t) {
		try {
			List<AssistanceTicket> tickets = maintenanceService.getAllTickets();
			ObservableList<AssistanceTicket> ticketsData = FXCollections.observableArrayList(tickets);
			maintenanceTable.setItems(ticketsData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
}
