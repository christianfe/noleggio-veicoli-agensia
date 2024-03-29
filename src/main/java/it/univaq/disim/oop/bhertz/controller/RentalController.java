package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.ContractType;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

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
	@FXML
	private HBox filterHBox;
	@FXML
	private CheckBox filterCheckBox;
	@FXML
	private DatePicker filterDatePicker;

	private ViewDispatcher dispatcher;
	private ContractService contractService;
	private FeedbackService feedbackService;
	private VeiclesService veiclesService;
	private User user;

	public RentalController() throws BusinessException {
		dispatcher = ViewDispatcher.getInstance();
		contractService = BhertzBusinessFactory.getInstance().getContractService();
		feedbackService = BhertzBusinessFactory.getInstance().getFeedbackService();
		veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
		veiclesService.refreshAllStates();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filterDatePicker.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setDisable(item.isBefore(LocalDate.now()));
			}
		});
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
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
			return new SimpleStringProperty(
					param.getValue().getStart().format(formatter) + "/" + param.getValue().getEnd().format(formatter));
		});

		paymentColumn.setStyle("-fx-alignment: CENTER;");
		paymentColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().isPaid() ? "SI" : "NO");
		});

		typeColumn.setStyle("-fx-alignment: CENTER;");
		typeColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getPrice()
					+ (param.getValue().getType() == ContractType.KM ? " €/km" : " €/giorno"));
		});

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Contract, MenuButton> param) -> {
			MenuButton localMenuButton = new MenuButton("Menu");

			MenuItem menuRichiestaAssistenza = new MenuItem("Richiedi Assistenza");
			MenuItem menuFeedback = new MenuItem("Lascia un Feedback");
			MenuItem menuGestioneRiconsegna = new MenuItem("Gestisci Riconsegna");
			MenuItem menuGestioneConsegna = new MenuItem("Gestisci Consegna");
			MenuItem menuPagato = new MenuItem("Imposta Pagato");
			MenuItem menuStato = new MenuItem("");

			menuRichiestaAssistenza.setOnAction((ActionEvent event) -> {
				if (JOptionPane.showConfirmDialog(null,
						"Confermi di voler richiedere assistenza per il veicolo: "
								+ param.getValue().getVeicle().getModel() + "?",
								"Cotinuare?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
					Contract assistanceContract = param.getValue();
					assistanceContract.setState(ContractState.MAINTENANCE);
					AssistanceTicket ticket = new AssistanceTicket();
					ticket.setState(TicketState.REQUIRED);
					ticket.setContract(assistanceContract);
					ticket.setStartDate(LocalDate.now());
					assistanceContract.getVeicle().setState(VeicleState.MAINTENANCE);
					MaintenanceService maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();
					try {
						maintenanceService.addTicket(ticket);
					} catch (BusinessException e) {
						dispatcher.renderError(e);
					}
					assistanceContract.setAssistance(ticket);
					dispatcher.renderView("maintenance", user);
				}
			});

			menuGestioneRiconsegna.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("appointmentManager",
						new ObjectsCollector<User, Contract>(user, param.getValue()));
			});

			menuGestioneConsegna.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("appointmentManager",
						new ObjectsCollector<User, Contract>(user, param.getValue()));
			});

			menuFeedback.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("createFeedback", new ObjectsCollector<User, Contract>(user, param.getValue()));
			});

			menuStato.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("changeContractState",
						new ObjectsCollector<User, Contract>(user, param.getValue()));
			});

			menuPagato.setOnAction((ActionEvent event) -> {
				double mult;
				if (param.getValue().getType() == ContractType.TIME)
					mult = ChronoUnit.DAYS.between(param.getValue().getStart(), param.getValue().getEnd());
				else
					mult = param.getValue().getEndKm() - param.getValue().getStartKm();
				double toPay = mult * param.getValue().getPrice();
				if (JOptionPane.showConfirmDialog(null, "Confermi che il cliente ha pagato €" + toPay,
						"Impostare come pagato?", JOptionPane.YES_NO_CANCEL_OPTION) != 0)
					return;
				try {
					contractService.setPaid(param.getValue().getId(), true);
				} catch (BusinessException e) {
					dispatcher.renderError(e);
				}
				dispatcher.renderView("rental", this.user);
			});

			if (param.getValue().getReturnDateTime() != null) {
				menuGestioneRiconsegna.setDisable(true);
				try {
					menuGestioneRiconsegna.setText("Appuntamento Riconsegna: "
							+ contractService.getContractByID(param.getValue().getId()).getReturnDateTime());
				} catch (BusinessException e) {
					dispatcher.renderError(e);
				}
			}

			if (param.getValue().getDeliverDateTime() != null) {
				menuGestioneConsegna.setDisable(true);
				try {
					menuGestioneConsegna.setText("Appuntamento Consegna: "
							+ contractService.getContractByID(param.getValue().getId()).getDeliverDateTime());
				} catch (BusinessException e) {
					dispatcher.renderError(e);
				}
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
					try {
						if (!feedbackService.isFeedBackSet(param.getValue()))
							localMenuButton.getItems().add(menuFeedback);
						else
							return null;
					} catch (BusinessException e) {
						dispatcher.renderError(e);
					}
				}
			} else if (this.user.getRole() == 1) {
				switch (param.getValue().getState()) {
				case BOOKED:
					localMenuButton.getItems().add(menuGestioneConsegna);
					if (param.getValue().getDeliverDateTime() != null)
						menuStato.setText("Consegna Veicolo");
					break;
				case ACTIVE:
					localMenuButton.getItems().add(menuGestioneRiconsegna);
					if (!param.getValue().isPaid() && param.getValue().getType() == ContractType.TIME)
						localMenuButton.getItems().add(menuPagato);
					if (param.getValue().getReturnDateTime() != null)
						menuStato.setText("Ritira Veicolo");
					break;
				case MAINTENANCE:
					return null;
				case ENDED:
					if (!param.getValue().isPaid())
						localMenuButton.getItems().add(menuPagato);
					else
						return null;
				}
				if (!menuStato.getText().equals(""))
					localMenuButton.getItems().add(menuStato);
			} else if (this.user.getRole() == 0)
				actionColumn.setVisible(false);
			if (param.getValue().isSostistuteContract())
				menuRichiestaAssistenza.setDisable(true);
			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});

	}

	@Override
	public void initializeData(User user) {
		this.user = user;
		List<Contract> contractList = null;
		if (user.getRole() != 1)
			filterHBox.setVisible(false);
		try {
			switch (user.getRole()) {
			case 0:
				contractList = contractService.getAllContracts(2);
				break;
			case 1:
				contractList = new ArrayList<Contract>();
				List<Contract> temp = contractService.getAllContracts(2);
				for (Contract c : temp)
					if (!(c.getState() == ContractState.ENDED && c.isPaid()))
						contractList.add(c);
				break;
			case 2:
				contractList = contractService.getContractsByUser(2, user);
				break;
			}
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
		Collections.sort(contractList, new ContractOrder());
		ObservableList<Contract> contractData = FXCollections.observableArrayList(contractList);
		rentalTable.setItems(contractData);
	}

	@FXML
	public void filterAction(ActionEvent e) {
		try {
			if (!filterCheckBox.isSelected()) {
				this.initializeData(user);
				return;
			}
			List<Contract> contracts = contractService.getAllContracts(0);
			List<Contract> selectedContract = new ArrayList<>();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			for (Contract c : contracts)
				if (c.getState() == ContractState.ACTIVE && c.getReturnDateTime() != null && filterDatePicker.getValue()
				.format(formatter).toString().equals(c.getReturnDateTime().substring(0, 10)))
					selectedContract.add(c);
			ObservableList<Contract> contractData = FXCollections.observableArrayList(selectedContract);
			rentalTable.setItems(contractData);
		} catch (NullPointerException ex) {
			filterCheckBox.setSelected(false);
		} catch (BusinessException ex) {
			dispatcher.renderError(ex);
		}
	}

}
