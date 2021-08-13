package it.univaq.disim.oop.bertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController implements Initializable{
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button logInButton;
	@FXML
	private Label labelError;
	@FXML
	private Button buttonSignup;
	
	private ViewDispatcher dispatcher;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.dispatcher = ViewDispatcher.getInstance();
		//logInButton.disableProperty().bind(usernameField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty()));
	}
	
	@FXML
	private void logInTry(ActionEvent e) {
		if (!usernameField.getText().equals("") || !passwordField.getText().equals(""))
			labelError.setText("Username e/o password errati!");
		else {
			this.dispatcher.loggedIn();
		};
	}
	
	@FXML
	private void signup(ActionEvent e) {
		this.dispatcher.signup();
	}
}
