package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.domain.Staff;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserEditorController implements Initializable, DataInitializable<ObjectsCollector<User, User>> {

	@FXML
	private Label labelErrorSignup;
	@FXML
	private Button registerButton;
	@FXML
	private PasswordField newPasswordField;
	@FXML
	private PasswordField newPasswordRepeatField;
	@FXML
	private TextField newUsernameField;
	@FXML
	private TextField newNameField;
	@FXML
	private Label titleLabel;

	private ViewDispatcher dispatcher;
	private UserService userServices;
	private User userToEdit;
	private User userEditing;
	private boolean creatingNewOperator = true;

	public UserEditorController() {
		this.dispatcher = ViewDispatcher.getInstance();
		userServices = BhertzBusinessFactory.getInstance().getUserService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		registerButton.disableProperty().bind(newUsernameField.textProperty().isEmpty().or(newNameField.textProperty().isEmpty().or(newPasswordField.textProperty().isEmpty().or(newPasswordRepeatField.textProperty().isEmpty()))));
	}

	@Override
	public void initializeData(ObjectsCollector<User, User> objectsCollector) {
		userEditing = objectsCollector.getObjectA();
		userToEdit = objectsCollector.getObjectB();
		if (userToEdit != null) {
			creatingNewOperator = false;
			titleLabel.setText("Modifica Utente");
			newNameField.setText(userToEdit.getName());
			newUsernameField.setText(userToEdit.getUsername());
			newPasswordField.setText(userToEdit.getPassword());
			newPasswordRepeatField.setText(userToEdit.getPassword());
		}
	}

	@FXML
	private void signup(ActionEvent e) {
		if (creatingNewOperator && userServices.isUsernameSet(newUsernameField.getText()))
			labelErrorSignup.setText("Username non disponibile!");
		else if (!creatingNewOperator && userServices.isUsernameSet(this.userToEdit.getId(), newUsernameField.getText()))
			labelErrorSignup.setText("Username non disponibile!");
		else if (!newPasswordField.getText().equals(newPasswordRepeatField.getText()))
			labelErrorSignup.setText("Le password immesse sono diverse!");
		else {
			if (creatingNewOperator) userServices.addUser(new Staff(0, newNameField.getText(), newUsernameField.getText(), newPasswordField.getText()));
			else userServices.setUser(userToEdit.getId(), newNameField.getText(), newUsernameField.getText(), newPasswordField.getText());

			if (userEditing != null && userToEdit != null && userEditing.getId() == userToEdit.getId()) {
				JOptionPane.showMessageDialog(null, "Dati aggiornati con successo!");
				dispatcher.renderView("home", userEditing);
			} else dispatcher.renderView("user", null);
		}
	}
}