package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMUserServiceImpl;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class addOperatorController implements Initializable, DataInitializable<Object> {

	@FXML
	private Label labelErrorSignup;
	@FXML
	private Button registerButton;
	@FXML
	private PasswordField NewPasswordField;
	@FXML
	private PasswordField NewPasswordRepeatField;
	@FXML
	private TextField newUsernameField;
	@FXML
	private TextField newNameField;
	
	private ViewDispatcher dispatcher;
	
	public addOperatorController() {
		this.dispatcher = ViewDispatcher.getInstance();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void signup(ActionEvent e) {
		if (!NewPasswordField.getText().equals(NewPasswordRepeatField.getText()))
			labelErrorSignup.setText("Le password immesse sono diverse!");
		else {
			UserService userService = new RAMUserServiceImpl();
			try {
				userService.addUser(new Customer(0, newNameField.getText(), newUsernameField.getText(), NewPasswordField.getText()));
			} catch (BusinessException e1) {
				dispatcher.renderError(e1);
			}
			dispatcher.renderView("user", null);
		}
			
	}
}
