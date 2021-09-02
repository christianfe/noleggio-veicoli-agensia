package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StartRentController implements Initializable, DataInitializable<ObjectsCollector>{

	@FXML
	private Label titleLabel;
	@FXML
	private DatePicker dateStartField;
	@FXML
	private DatePicker dateEndField;
	@FXML
	private CheckBox dailyCheckBox;
	@FXML
	private CheckBox kmCheckBox;
	@FXML
	private Button confirmButton;
	@FXML
	private Label labelError;

	private User user;
	private Veicle veicle;


	public StartRentController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void initializeData(ObjectsCollector objectsCollector){
		this.user = (User) objectsCollector.getObjectA();
		this.veicle= (Veicle) objectsCollector.getObjectB();
		
		dailyCheckBox.setText(veicle.getType().getPriceForDay() + " €/day");
		kmCheckBox.setText(veicle.getType().getPriceForKm() + " €/km");
	}

	@FXML
	public void dailyCheckAction(ActionEvent e){
		if (dailyCheckBox.isSelected()) kmCheckBox.setSelected(false);
	}

	@FXML
	public void kmCheckAction(ActionEvent e){
		if (kmCheckBox.isSelected()) dailyCheckBox.setSelected(false);
	}

	@FXML
	public void startContractAction(ActionEvent e){
		if (!dailyCheckBox.isSelected() && !kmCheckBox.isSelected())
			labelError.setText("Selezionare una tipologia di contratto");
	}

}
