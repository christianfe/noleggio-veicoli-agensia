package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.domain.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class HomeController implements Initializable, DataInitializable<User> {

	@FXML
	private Label welcomeLabel;
	@FXML
	private Label notificationLabel;
	@FXML
	private TextArea notificationTextArea;
	
	private User user;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@Override
	public void initializeData(User user){
		this.user = user;
		welcomeLabel.setText("Benvenuto " + user.getName());
		

		
		//welcomeLabel1.setText("ddswecsc");
		
		if (user.getRole() == 2) {
			notificationLabel.setVisible(true);
			notificationTextArea.setVisible(true);
		}
		
		}
	}


