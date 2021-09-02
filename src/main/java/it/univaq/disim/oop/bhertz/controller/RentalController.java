package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.RentalService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMRentalServiceImpl;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractType;
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
import javafx.scene.control.cell.PropertyValueFactory;

public class RentalController implements Initializable, DataInitializable<User>{

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
	private TableColumn<Contract, ContractType> stateColumn;
	@FXML
	private TableColumn<Contract, String> paymentColumn;
	@FXML
	private TableColumn<Contract, Button> actionColumn;
	
	private ViewDispatcher dispatcher;
	
	private RentalService rentalService;
	
	public RentalController() throws BusinessException {
		dispatcher = ViewDispatcher.getInstance();
		rentalService = new RAMRentalServiceImpl();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		userColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getCustomer().getName());
		});
		
		veicleColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getVeicle().getModel() + " - " + param.getValue().getVeicle().getPlate());
		});
		
		periodColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getStart().toString());
		});
	
		paymentColumn.setCellValueFactory(new PropertyValueFactory<>("paid")); 
		
		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Contract, Button> param) -> {
			final Button rentalButton = new Button("Bottone");

			rentalButton.setOnAction((ActionEvent event) -> {
				//dispatcher.renderView("veicles", param.getValue());
				System.out.println("bravo, ci sei riuscito");
			});
			return new SimpleObjectProperty<Button>(rentalButton);
		});
	}
	
	
	@Override
	public void initializeData(User user) {
		try {
			List<Contract> contract = rentalService.getAllContracts();
			ObservableList<Contract> contractData = FXCollections.observableArrayList(contract);
			rentalTable.setItems(contractData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

}
