package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMContractServiceImpl;
import it.univaq.disim.oop.bhertz.domain.Contract;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;

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
	private TableColumn<Contract, String> stateColumn;
	@FXML
	private TableColumn<Contract, String> paymentColumn;
	@FXML
	private TableColumn<Contract, Button> actionColumn;
	
	private ViewDispatcher dispatcher;
	
	private ContractService rentalService;
	
	public RentalController() throws BusinessException {
		dispatcher = ViewDispatcher.getInstance();
		rentalService = new RAMContractServiceImpl();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		userColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getCustomer().getName());
		});
		
		veicleColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getVeicle().getModel() + " - " + param.getValue().getVeicle().getPlate());
		});
		
		stateColumn.setStyle("-fx-alignment: CENTER;");
		stateColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getStateString());
		});
		
		
		periodColumn.setStyle("-fx-alignment: CENTER;");
		periodColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().getStart().toString().substring(5) + " / " + param.getValue().getEnd().toString().substring(5));
		});
	
		paymentColumn.setStyle("-fx-alignment: CENTER;");
		paymentColumn.setCellValueFactory((CellDataFeatures<Contract, String> param) -> {
			return new SimpleStringProperty(param.getValue().isPaid() ? "SI":"NO");
		});

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
		if (user.getRole() == 2)
			userColumn.setVisible(false);
		try {
			List<Contract> contract = (user.getRole() == 2 ? rentalService.getContractsByUser(user) : rentalService.getAllContracts());
			ObservableList<Contract> contractData = FXCollections.observableArrayList(contract);
			rentalTable.setItems(contractData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
		
		
		MenuItem mi1 = new MenuItem("Menu item 1");
		MenuItem mi2 = new MenuItem("Menu item 2");
		mi1.setOnAction((ActionEvent event) -> {
		    System.out.println("Menu item 1");
		    Object param = rentalTable.getSelectionModel().getSelectedItem();
		    System.out.println("Selected item: " + param);
		});

		ContextMenu menu = new ContextMenu();
		menu.getItems().add(mi1);
		menu.getItems().add(mi2);
		rentalTable.setContextMenu(menu);
		
	}

}
