package it.univaq.disim.oop.bhertz.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
import javafx.scene.control.MenuButton;
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
	private TableColumn<Contract, MenuButton> actionColumn;
	
	private ViewDispatcher dispatcher;
	
	private ContractService rentalService;
	private ContextMenu menu = new ContextMenu();
	private User user;
	
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
		actionColumn.setCellValueFactory((CellDataFeatures<Contract, MenuButton> param) -> {
			MenuButton localMenuButton = new MenuButton("Menu");
			
			MenuItem richiestaAssistenza = new MenuItem("Richiedi Assistenza");
			MenuItem gestioneRiconsegna = new MenuItem("Gestisci Riconsegna");
			
			richiestaAssistenza.setOnAction((ActionEvent event) -> {
				System.out.println("richiesta assistenza");
			});
			
			gestioneRiconsegna.setOnAction((ActionEvent event) -> {
				System.out.println("gestione Riconsegna");
			});
			
			if (this.user.getRole() == 2)
				localMenuButton.getItems().add(richiestaAssistenza);
			else if (this.user.getRole() == 1)
				localMenuButton.getItems().add(gestioneRiconsegna);
			else if (this.user.getRole() == 0)
				actionColumn.setVisible(false);
			
			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});
		
	}
	
	@Override
	public void initializeData(User user) {
		
		/*
		switch (user.getRole()) {
		case 2:
			userColumn.setVisible(false);
			MenuItem richiediAssistenza = new MenuItem("Richiedi assistenza");
			richiediAssistenza.setOnAction((ActionEvent event) -> {
			    System.out.println("richiesta assistenza");  
			});
			menu.getItems().add(richiediAssistenza);
		break;
		case 1:
			MenuItem gestioneRiconsegna = new MenuItem("Gestione riconsegna");
			gestioneRiconsegna.setOnAction((ActionEvent event) -> {
			    System.out.println("riconsegna gestita");  
			});
			menu.getItems().add(gestioneRiconsegna);
		break;
		case 0:
			actionColumn.setVisible(false);
			
		break;
		} 
		
		rentalTable.setContextMenu(menu);
		*/
		
		
		this.user = user;
		
		try {
			List<Contract> contract = (user.getRole() == 2 ? rentalService.getContractsByUser(user) : rentalService.getAllContracts());
			ObservableList<Contract> contractData = FXCollections.observableArrayList(contract);
			rentalTable.setItems(contractData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
		
		
		/*
		MenuItem mi1 = new MenuItem("Menu item 1");
		MenuItem mi2 = new MenuItem("Menu item 2");
		mi1.setOnAction((ActionEvent event) -> {
		    System.out.println("Menu item 1");
		    Object param = rentalTable.getSelectionModel().getSelectedItem();
		    param = null;
		    System.out.println("Selected item: " + param);
		});

		menu.getItems().add(mi1);
		menu.getItems().add(mi2);
		rentalTable.setContextMenu(menu);
		*/
	}

}
