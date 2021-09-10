package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MaintenanceManagementController extends ViewUtility
		implements Initializable, DataInitializable<ObjectsCollector<User, AssistanceTicket>> {

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
	private VeiclesService veiclesService;
	private MaintenanceService maintenanceService;
	private ContractService contractService;

	public MaintenanceManagementController() {
		dispatcher = ViewDispatcher.getInstance();
		this.veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
		this.contractService = BhertzBusinessFactory.getInstance().getContractService();
		this.maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		veicleColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
		plateColumn.setCellValueFactory(new PropertyValueFactory<>("plate"));
		fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuel"));

		consumptionColumn.setCellValueFactory((CellDataFeatures<Veicle, String> param) -> {
			return new SimpleStringProperty(param.getValue().getPriceForKm() + "  €/km");
		});

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Veicle, Button> param) -> {
			Button localButton = new Button();
			localButton.setText("Seleziona");

			localButton.setOnAction((ActionEvent event) -> {
				Contract substituteContract = new Contract();
				substituteContract.setVeicle(param.getValue());
				substituteContract.setStart(ticket.getStartDate());
				substituteContract.setEnd(ticket.getContract().getEnd());
				substituteContract.setSostistuteContract(true);
				substituteContract.setCustomer(ticket.getContract().getCustomer());
				substituteContract.setPaid(ticket.getContract().isPaid());
				substituteContract.setStartKm(ticket.getVeicleKm());
				substituteContract.setPrice(ticket.getContract().getPrice());
				substituteContract.setState(ContractState.BOOKED);
				substituteContract.setType(ticket.getContract().getType());
				
				ticket.setSubstituteContract(substituteContract);
				try {
					contractService.addContract(substituteContract);
					maintenanceService.setTicket(ticket);
				} catch (BusinessException e) {
					dispatcher.renderError(e);
				}
				dispatcher.renderView("maintenance", user);
			});

			return new SimpleObjectProperty<Button>(localButton);
		});
	}

	@Override
	public void initializeData(ObjectsCollector<User, AssistanceTicket> collector) {
		this.user = collector.getObjectA();
		this.ticket = collector.getObjectB();
		try {
			List<Veicle> veicleList = null;
			veicleList = this.veiclesService
					.getVeiclesByType(collector.getObjectB().getContract().getVeicle().getType());
			List<Veicle> veicleListByAviability = new ArrayList<>();
			for (Veicle v : veicleList) {
				List<Contract> contractOfVeicle = contractService.getContractsByVeicle(0, v.getId());
				if (veiclesService.isVeicleFree(ticket.getStartDate(), ticket.getContract().getEnd(), contractOfVeicle))
					veicleListByAviability.add(v);
			}
			ObservableList<Veicle> veiclesData = FXCollections.observableArrayList(veicleListByAviability);
			veicleTable.setItems(veiclesData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void exitAction() {
		dispatcher.renderView("maintenance", user);
	}

}
