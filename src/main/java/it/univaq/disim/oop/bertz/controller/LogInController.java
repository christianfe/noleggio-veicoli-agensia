package it.univaq.disim.oop.bertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bertz.business.BusinessException;
import it.univaq.disim.oop.bertz.business.UserNotFoundException;
import it.univaq.disim.oop.bertz.business.UserService;
import it.univaq.disim.oop.bertz.domain.User;
import it.univaq.disim.oop.bertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController implements Initializable {
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

	private UserService userService;

	private ViewDispatcher dispatcher;

	public LogInController() {
		this.dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logInButton.disableProperty()
				.bind(usernameField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty()));
	}

	@FXML
	private void logInTry(ActionEvent event) {
		try {
			User user = userService.authenticate(usernameField.getText(), passwordField.getText());
			dispatcher.loggedIn(user);
		} catch (UserNotFoundException e) {
			labelError.setText("Username e/o password errati!");
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
		/*
		 * if (!usernameField.getText().equals("") ||
		 * !passwordField.getText().equals(""))
		 * labelError.setText("Username e/o password errati!"); else {
		 * this.dispatcher.loggedIn(); }
		 */
	}

	@FXML
	private void signup(ActionEvent e) {
		this.dispatcher.signup();
	}
}
