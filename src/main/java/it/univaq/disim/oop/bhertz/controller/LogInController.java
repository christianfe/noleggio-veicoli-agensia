package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.UserNotFoundException;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMUserServiceImpl;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LogInController implements Initializable, DataInitializable<Object> {
	@FXML
	private TextField usernameField;
	@FXML
	private TextField newUsernameField;
	@FXML
	private TextField newNameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField NewPasswordField;
	@FXML
	private PasswordField NewPasswordRepeatField;
	@FXML
	private Button logInButton;
	@FXML
	private Label labelErrorSignup;
	@FXML
	private Label labelErrorLogin;
	@FXML
	private Button buttonSignup;
	@FXML
	private Button registerButton;
	@FXML
	private Button backSignup;
	@FXML
	private Pane loginPane;
	@FXML
	private Pane registerPane;
	@FXML
	private AnchorPane LogInStage;


	private UserService userService;

	private ViewDispatcher dispatcher;

	public LogInController() {
		userService = new RAMUserServiceImpl();
		this.dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		LogInStage.setStyle("-fx-background-color: #f1f1f1");
		logInButton.disableProperty().bind(usernameField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty()));
		registerButton.disableProperty().bind(newUsernameField.textProperty().isEmpty().or(newNameField.textProperty().isEmpty().or(NewPasswordField.textProperty().isEmpty().or(NewPasswordRepeatField.textProperty().isEmpty()))));
	}

	@FXML
	private void logInTry(ActionEvent event) {
		try {
			User user = userService.authenticate(usernameField.getText(), passwordField.getText());
			dispatcher.loggedIn(user);
		} catch (UserNotFoundException e) {
			labelErrorLogin.setText("Username e/o password errati!");
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	private void signup(ActionEvent e) {
		if (!NewPasswordField.getText().equals(NewPasswordRepeatField.getText()))
			labelErrorSignup.setText("Le password immesse sono diverse!");
	}

	@FXML
	private void switchView(ActionEvent e) {
		registerPane.setVisible(loginPane.isVisible());
		loginPane.setVisible(!loginPane.isVisible());
		labelErrorSignup.setText("");
		labelErrorLogin.setText("");
	}
}
