package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
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

public class RentalController implements Initializable, DataInitializable<User> {

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
	private TableColumn<Contract, MenuButton> actionColumn;

	private ViewDispatcher dispatcher;

	private BhertzBusinessFactory factory;
	private ContractService rentalService;
	private User user;

	public RentalController() throws BusinessException {
		dispatcher = ViewDispatcher.getInstance();
		factory = BhertzBusinessFactory.getInstance();
		rentalService = factory.getContractService();
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
			return new SimpleStringProperty(param.getValue().getStart().toString().substring(5) + " / "
					+ param.getValue().getEnd().toString().substring(5));
		});

		paymentColumn.setStyle("-fx-alignment: CENTER;");
		paymentColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().isPaid() ? "SI" : "NO");
		});

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Contract, MenuButton> param) -> {
			MenuButton localMenuButton = new MenuButton("Menu");

			MenuItem menuRichiestaAssistenza = new MenuItem("Richiedi Assistenza");
			MenuItem menuGestioneRiconsegna = new MenuItem("Gestisci Riconsegna");
			MenuItem menuPagato = new MenuItem();

			menuPagato.setText(param.getValue().isPaid() ? "Imposta Non Pagato" : "Imposta Pagato");

			menuRichiestaAssistenza.setOnAction((ActionEvent event) -> {

				Contract assistanceContract = param.getValue();
				AssistanceTicket ticket = new AssistanceTicket();
				ticket.setState(TicketState.REQUIRED);
				ticket.setContract(assistanceContract);
				ticket.setStartDate(LocalDate.now());
				assistanceContract.getVeicle().setState(VeicleState.MAINTENANCE);
				MaintenanceService maintenanceService = factory.getMaintenanceService();
				maintenanceService.addTicket(ticket);
				assistanceContract.setAssistance(ticket);
				dispatcher.renderView("rental", user);

			});

			menuGestioneRiconsegna.setOnAction((ActionEvent event) -> {
			});

			menuPagato.setOnAction((ActionEvent event) -> {
				// TODO deve aggiornare la vista oltre che dB
				param.getValue().setPaid(!param.getValue().isPaid());
			});

			if (this.user.getRole() == 2) {
				if (param.getValue().getVeicle().getState() == VeicleState.MAINTENANCE)
					localMenuButton.setVisible(false);
				else 
					localMenuButton.getItems().add(menuRichiestaAssistenza);
			} else if (this.user.getRole() == 1) {
				localMenuButton.getItems().add(menuGestioneRiconsegna);
				localMenuButton.getItems().add(menuPagato);
			} else if (this.user.getRole() == 0)
				actionColumn.setVisible(false);

			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});

	}

	@Override
	public void initializeData(User user) {
		this.user = user;

		try {
			List<Contract> contract = (user.getRole() == 2 ? rentalService.getContractsByUser(user)
					: rentalService.getAllContracts());
			ObservableList<Contract> contractData = FXCollections.observableArrayList(contract);
			rentalTable.setItems(contractData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

}
