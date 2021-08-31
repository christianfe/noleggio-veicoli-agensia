package it.univaq.disim.oop.bertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bertz.domain.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeController implements Initializable, DataInitializable<User> {

	@FXML
	private Label welcomeLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@Override
	public void initializeData(User user){
		welcomeLabel.setText("Benvenuto " + user.getName());
	}

}
