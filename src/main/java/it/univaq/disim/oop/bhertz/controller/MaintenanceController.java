package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;

public class MaintenanceController extends ViewUtility implements Initializable, DataInitializable<User> {

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
	private TableColumn<AssistanceTicket, MenuButton> actionColumn;
	@FXML
	private TableColumn<AssistanceTicket, String> substituteColumn;

	private ViewDispatcher dispatcher;
	private MaintenanceService maintenanceService;
	private User user;

	public MaintenanceController() {
		dispatcher = ViewDispatcher.getInstance();
		maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userColumn.setCellValueFactory((CellDataFeatures<AssistanceTicket, String> param) -> {
			return new SimpleStringProperty(param.getValue().getContract().getCustomer().getName());
		});
		veicleColumn.setCellValueFactory((CellDataFeatures<AssistanceTicket, String> param) -> {
			return new SimpleStringProperty(param.getValue().getContract().getVeicle().getModel());
		});
		substituteColumn.setCellValueFactory((CellDataFeatures<AssistanceTicket, String> param) -> {
			return new SimpleStringProperty(param.getValue().getSubstituteContract() == null ? "NO"
					: param.getValue().getSubstituteContract().getVeicle().toString());
		});

		stateColumn.setCellValueFactory((CellDataFeatures<AssistanceTicket, String> param) -> {
			String s = "";
			switch (param.getValue().getState()) {
			case REQUIRED:
				s = "Richiesto";
				break;
			case WORKING:
				s = "In Lavorazione";
				break;
			case READY:
				s = "Pronto per il ritiro";
				break;
			case ENDED:
				s = "Conclusa";
				break;
			}
			return new SimpleStringProperty(s);
		});

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<AssistanceTicket, MenuButton> param) -> {
			MenuButton localMenuButton = new MenuButton("Menu");

			MenuItem menuChangeStatus = new MenuItem();
			MenuItem menuAppointment = new MenuItem("Fissa appuntamento ritiro");
			MenuItem menuDetails = new MenuItem("Visualizza Dettagli");
			MenuItem menuFixed = new MenuItem("Conferma veicolo aggiustato");
			localMenuButton.getItems().add(menuDetails);
			if (this.user.getRole() == 1) {
				param.getValue().getContract().setAssistance(param.getValue());
				switch (param.getValue().getState()) {
				case REQUIRED:
					if (param.getValue().getTimeStart() != null) {
						menuAppointment.setText("Appuntamento: " + param.getValue().getStartDate() + " "
								+ param.getValue().getTimeStart());
						menuAppointment.setDisable(true);
						menuChangeStatus.setText("Ritira Veicolo");
						localMenuButton.getItems().add(menuChangeStatus);
					}
					localMenuButton.getItems().add(menuAppointment);
					break;

				case WORKING:
					menuChangeStatus.setText("Seleziona Veicolo Sostitutivo");
					localMenuButton.getItems().add(menuChangeStatus);
					localMenuButton.getItems().add(menuFixed);

					menuFixed.setOnAction((ActionEvent event) -> {
						param.getValue().getContract().getVeicle().setState(VeicleState.BUSY);
							
					param.getValue().setState(TicketState.READY);
						dispatcher.renderView("maintenance", user);
					});

					break;

				case READY:
					if (param.getValue().getEndDate() == null)
						menuAppointment.setText("Fissa Appuntamento");
					else {
						menuAppointment.setText(
								"Appuntamento: " + param.getValue().getEndDate() + " " + param.getValue().getTimeEnd());
						menuAppointment.setDisable(true);
						menuChangeStatus.setText("Riconsegna veicolo al cliente");
						localMenuButton.getItems().add(menuChangeStatus);

					}
					localMenuButton.getItems().add(menuAppointment);
					
					
					
					break;

				case ENDED:
					break;
				}
			} else if (this.user.getRole() == 2) {
				userColumn.setVisible(false);
				actionColumn.setVisible(false);
			}

			menuChangeStatus.setOnAction((ActionEvent event) -> {
				// dispatcher.renderView("maintenanceChangeStatus", new
				// ObjectsCollector<User,AssistanceTicket>(this.user, param.getValue()));
				switch (param.getValue().getState()) {
				case REQUIRED:
					dispatcher.renderView("maintenanceReturn",
							new ObjectsCollector<User, AssistanceTicket>(user, param.getValue()));
					break;
				case WORKING:
					dispatcher.renderView("maintenanceManagement",
							new ObjectsCollector<User, AssistanceTicket>(user, param.getValue()));

					break;
				case READY:
					param.getValue().setState(TicketState.ENDED);

					/*
					 * try {
					 * BhertzBusinessFactory.getInstance().getVeiclesService().refreshAllStates(); }
					 * catch (BusinessException e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); }
					 */

					if (param.getValue().getSubstituteContract() == null) {
						param.getValue().getContract().getVeicle().setState(VeicleState.BUSY);
						dispatcher.renderView("changeContractState",
								new ObjectsCollector<User, Contract>(user, param.getValue().getContract()));
					} else
						dispatcher.renderView("changeContractState",
								new ObjectsCollector<User, Contract>(user, param.getValue().getSubstituteContract()));
						
					break;
				case ENDED:
				}
			});

			menuAppointment.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("appointmentManager",
						new ObjectsCollector<User, Contract>(user, param.getValue().getContract()));
			});
			menuDetails.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("maintenanceDetails",
						new ObjectsCollector<User, AssistanceTicket>(this.user, param.getValue()));
			});

			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});
	}

	@Override
	public void initializeData(User user) {
		this.user = user;
		try {
			List<AssistanceTicket> tickets = (user.getRole() == 2 ? maintenanceService.getTicketByUser(user)
					: maintenanceService.getAllTickets());
			ObservableList<AssistanceTicket> ticketsData = FXCollections.observableArrayList(tickets);
			maintenanceTable.setItems(ticketsData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
}
