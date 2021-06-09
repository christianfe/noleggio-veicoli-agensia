package it.univaq.disim.oop.bertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

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
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logInButton.disableProperty().bind(usernameField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty()));
	}
	
	@FXML
	private void logInTry(ActionEvent e) {
		if (!usernameField.getText().equals("test") || !passwordField.getText().equals("test"))
			labelError.setText("Sei un down");
		else labelError.setText("Sei poco un down");
	}
	
	
}
