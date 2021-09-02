package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMUserServiceImpl;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
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

public class UserController implements Initializable, DataInitializable<Object>{

	
	@FXML
	private Label titleLabel;
	
	@FXML
	private TableView<User> staffTable;
	@FXML
	private TableColumn<User, String> idStaffColumn;
	@FXML
	private TableColumn<User, String> usernameStaffColumn;
	@FXML
	private TableColumn<User, String> nameStaffColumn;
	@FXML
	private TableColumn<User, Button> actionStaffColumn;
	
	@FXML
	private TableView<User> customerTable;
	@FXML
	private TableColumn<User, String> idCustomersColumn;
	@FXML
	private TableColumn<User, String> usernameCustomersColumn;
	@FXML
	private TableColumn<User, String> nameCustomersColumn;
	@FXML
	private TableColumn<User, Button> actionCustomersColumn;
	
	private ViewDispatcher dispatcher;
	private UserService userServices;
	
	public UserController() {
		dispatcher = ViewDispatcher.getInstance();
		userServices = new RAMUserServiceImpl();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idStaffColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		usernameStaffColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		nameStaffColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		actionStaffColumn.setStyle("-fx-alignment: CENTER;");
		actionStaffColumn.setCellValueFactory((CellDataFeatures<User, Button> param) -> {
			final Button userButton = new Button("Elimina");
			userButton.setOnAction((ActionEvent event) -> {
				//dispatcher.renderView("appelli", param.getValue());
			});
			return new SimpleObjectProperty<Button>(userButton);
		});

		idCustomersColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		usernameCustomersColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		nameCustomersColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		actionCustomersColumn.setStyle("-fx-alignment: CENTER;");
		actionCustomersColumn.setCellValueFactory((CellDataFeatures<User, Button> param) -> {
			final Button userButton = new Button("Elimina");
			userButton.setOnAction((ActionEvent event) -> {
				//dispatcher.renderView("appelli", param.getValue());
			});
			return new SimpleObjectProperty<Button>(userButton);
		});
	}
	
	@Override
	public void initializeData(Object o) {
		try {
			List<User> staffs = userServices.getUserByRole(1);
			ObservableList<User> staffData = FXCollections.observableArrayList(staffs);
			staffTable.setItems(staffData);
			
			List<User> users = userServices.getUserByRole(2);
			ObservableList<User> userData = FXCollections.observableArrayList(users);
			customerTable.setItems(userData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	public void addOperatorAction (ActionEvent event) {
		dispatcher.renderView("addUser", null);
	}
	
}
