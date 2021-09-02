package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMMaintenanceServiceImpl;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.TicketState;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
	private TableColumn<AssistanceTicket, TicketState> stateColumn;
	@FXML
	private TableColumn<AssistanceTicket, MenuButton> actionColumn;

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
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<AssistanceTicket, MenuButton> param) -> {
			MenuButton localMenuButton= new MenuButton("Menu");

			MenuItem menuChangeStatus = new MenuItem();
			MenuItem menuNewVeicle = new MenuItem("Veicolo Sostitutivo");

			if (param.getValue().getState() == TicketState.ENDED) {
				menuChangeStatus.setDisable(true);
				menuChangeStatus.setText("Ticket Risolto");
			}
			else menuChangeStatus.setText((param.getValue().getState() == TicketState.REQUIRED) ? "Imposta come IN LAVORAZIONE" : "Imposta come RISOLTO");

			localMenuButton.getItems().add(menuChangeStatus);
			localMenuButton.getItems().add(menuNewVeicle);

			menuChangeStatus.setOnAction((ActionEvent event) -> {});
			menuNewVeicle.setOnAction((ActionEvent event) -> {});

			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});
	}

	@Override
	public void initializeData(User user) {
		if (user.getRole() == 2)
			userColumn.setVisible(false);
		
		if (user.getRole() != 1)
			actionColumn.setVisible(false);

		try {
			List<AssistanceTicket> tickets = (user.getRole() == 2 ? maintenanceService.getTicketByUser(user) : maintenanceService.getAllTickets());
			ObservableList<AssistanceTicket> ticketsData = FXCollections.observableArrayList(tickets);
			maintenanceTable.setItems(ticketsData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
}
