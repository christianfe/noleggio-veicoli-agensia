package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RentalController implements Initializable, DataInitializable<User>{

	@FXML
	private Label titleLabel;
	@FXML
	private TableView<Type> rentalTable;
	@FXML
	private TableColumn<Type, String> userColumn;
	@FXML
	private TableColumn<Type, String> veicleColumn;
	@FXML
	private TableColumn<Type, String> periodColumn;
	@FXML
	private TableColumn<Type, String> stateColumn;
	@FXML
	private TableColumn<Type, String> paymentColumn;
	@FXML
	private TableColumn<Type, Button> actionColumn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
	}

}