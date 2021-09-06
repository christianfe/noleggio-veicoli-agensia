package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractType;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
import it.univaq.disim.oop.bhertz.view.ContractOrder;
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

public class RentalController extends ViewUtility implements Initializable, DataInitializable<User> {

	@FXML
	private Label titleLabel;
	@FXML
	private TableView<Contract> rentalTable;
	@FXML
	private TableColumn<Contract, String> userColumn;
	@FXML
	private TableColumn<Contract, String> veicleColumn;
	@FXML
	private TableColumn<Contract, String> periodColumn;
	@FXML
	private TableColumn<Contract, String> stateColumn;
	@FXML
	private TableColumn<Contract, String> paymentColumn;
	@FXML
	private TableColumn<Contract, String> typeColumn;
	@FXML
	private TableColumn<Contract, MenuButton> actionColumn;

	private ViewDispatcher dispatcher;

	private ContractService contractService;
	private VeiclesService veiclesService;
	private User user;

	public RentalController() throws BusinessException {
		dispatcher = ViewDispatcher.getInstance();
		contractService = BhertzBusinessFactory.getInstance().getContractService();
		veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		userColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getCustomer().getName());
		});

		veicleColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(
					param.getValue().getVeicle().getModel() + " - " + param.getValue().getVeicle().getPlate());
		});

		stateColumn.setStyle("-fx-alignment: CENTER;");
		stateColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getStateString());
		});

		periodColumn.setStyle("-fx-alignment: CENTER;");
		periodColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
			return new SimpleStringProperty(param.getValue().getStart().format(formatter).substring(5) + " / "
					+ param.getValue().getEnd().format(formatter).substring(5));
		});

		paymentColumn.setStyle("-fx-alignment: CENTER;");
		paymentColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().isPaid() ? "SI" : "NO");
		});

		typeColumn.setStyle("-fx-alignment: CENTER;");
		typeColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getType() + "");
		});

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Contract, MenuButton> param) -> {
			MenuButton localMenuButton = new MenuButton("Menu");

			MenuItem menuRichiestaAssistenza = new MenuItem("Richiedi Assistenza");
			MenuItem menuFeedback = new MenuItem("Lascia un Feedback");
			MenuItem menuGestioneRiconsegna = new MenuItem("Gestisci Riconsegna");
			MenuItem menuGestioneConsegna = new MenuItem("Gestisci Consegna");
			MenuItem menuPagato = new MenuItem("Imposta Pagato");
			MenuItem menuStato= new MenuItem("");

			menuRichiestaAssistenza.setOnAction((ActionEvent event) -> {
				Contract assistanceContract = param.getValue();
				AssistanceTicket ticket = new AssistanceTicket();
				ticket.setState(TicketState.REQUIRED);
				ticket.setContract(assistanceContract);
				ticket.setStartDate(LocalDate.now());
				assistanceContract.getVeicle().setState(VeicleState.MAINTENANCE);
				MaintenanceService maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();
				maintenanceService.addTicket(ticket);
				assistanceContract.setAssistance(ticket);
				dispatcher.renderView("rental", user);

			});

			menuGestioneRiconsegna.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("veicleReturn", new ObjectsCollector<User, Contract>(user, param.getValue()));
			});

			menuGestioneConsegna.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("veicleReturn", new ObjectsCollector<User, Contract>(user, param.getValue()));
			});

			menuFeedback.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("createFeedback", new ObjectsCollector<User, Contract>(user, param.getValue()));
			});

			menuStato.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("changeContractState", new ObjectsCollector<User, Contract>(user, param.getValue()));
			});

			menuPagato.setOnAction((ActionEvent event) -> {
				if (JOptionPane.showConfirmDialog(null, "Confermi che il cliente ha pagato €100.00", "Impostare come pagato?",JOptionPane.YES_NO_CANCEL_OPTION) != 0)
					return;
				contractService.setPaid(param.getValue().getId(), true);
				dispatcher.renderView("rental", this.user);
			});

			if (param.getValue().getReturnDateTime() != null) {
				menuGestioneRiconsegna.setDisable(true);
				menuGestioneRiconsegna.setText("Appuntamento Riconsegna: "  + contractService.getContractByID(param.getValue().getId()).getReturnDateTime());
			}

			if (param.getValue().getDeliverDateTime() != null) {
				menuGestioneConsegna.setDisable(true);
				menuGestioneConsegna.setText("Appuntamento Consegna: "  + contractService.getContractByID(param.getValue().getId()).getDeliverDateTime());
			}

			if (this.user.getRole() == 2) {
				switch (param.getValue().getState()) {
				case BOOKED:
				case MAINTENANCE:
					return null;
				case ACTIVE:
					localMenuButton.getItems().add(menuRichiestaAssistenza);
					break;
				case ENDED:
					localMenuButton.getItems().add(menuFeedback);
					break;
				}
			} else if (this.user.getRole() == 1) {
				switch (param.getValue().getState()) {
				case BOOKED:
					localMenuButton.getItems().add(menuGestioneConsegna);
					if (param.getValue().getDeliverDateTime() != null) menuStato.setText("Consegna Veicolo");
					break;
				case ACTIVE:
					localMenuButton.getItems().add(menuGestioneRiconsegna);
					if (!param.getValue().isPaid() && param.getValue().getType() == ContractType.TIME)localMenuButton.getItems().add(menuPagato);
					if (param.getValue().getReturnDateTime() != null)  menuStato.setText("Ritira Veicolo");
					break;
				case MAINTENANCE:
					return null;
				case ENDED:
					if (!param.getValue().isPaid())localMenuButton.getItems().add(menuPagato);
					else return null;
				}
				if (!menuStato.getText().equals(""))localMenuButton.getItems().add(menuStato);
			} else if (this.user.getRole() == 0)
				actionColumn.setVisible(false);

			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});

	}

	@Override
	public void initializeData(User user) {
		this.user = user;
		try {
			List<Contract> contract = (user.getRole() == 2 ? contractService.getContractsByUser(user)
					: contractService.getAllContracts());
			Collections.sort(contract, new ContractOrder());

			ObservableList<Contract> contractData = FXCollections.observableArrayList(contract);
			rentalTable.setItems(contractData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

}
