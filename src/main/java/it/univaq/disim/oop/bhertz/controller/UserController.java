package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.domain.Admin;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserController implements Initializable, DataInitializable<Admin>{

	
	@FXML
	private Label titleLabel;
	
	@FXML
	private TableView<Type> staffTable;
	@FXML
	private TableColumn<Type, String> idStaffColumn;
	@FXML
	private TableColumn<Type, String> usernameStaffColumn;
	@FXML
	private TableColumn<Type, String> nameStaffColumn;
	@FXML
	private TableColumn<Type, String> telStaffColumn;
	@FXML
	private TableColumn<Type, Button> actionStaffColumn;
	
	@FXML
	private TableView<Type> customerTable;
	@FXML
	private TableColumn<Type, String> idCustomersColumn;
	@FXML
	private TableColumn<Type, String> usernameCustomersColumn;
	@FXML
	private TableColumn<Type, String> nameCustomersColumn;
	@FXML
	private TableColumn<Type, String> telCustomersColumn;
	@FXML
	private TableColumn<Type, Button> actionCustomersColumn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	public void addStaffUser (ActionEvent event) {
		
	}
	
}
