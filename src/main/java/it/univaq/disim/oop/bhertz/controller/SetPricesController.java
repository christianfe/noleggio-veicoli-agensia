package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SetPricesController extends ViewUtility implements Initializable, DataInitializable<ObjectsCollector<User, Veicle>> {

	@FXML
	private Label titleLabel;
	@FXML
	private TextField kmField;
	@FXML
	private TextField timeField;

	private ViewDispatcher dispatcher;
	private Veicle veicle;
	private User user;
	private VeiclesService veicleService;

	public SetPricesController() {
		dispatcher = ViewDispatcher.getInstance();
		veicleService = BhertzBusinessFactory.getInstance().getVeiclesService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.addForbiddenCharCheck(kmField, timeField);

	}

	@Override
	public void initializeData(ObjectsCollector<User, Veicle> object) {
		this.user = object.getObjectA();
		this.veicle = object.getObjectB();

		kmField.setText(String.valueOf(veicle.getPriceForKm()));
		timeField.setText(String.valueOf(veicle.getPriceForDay()));
		titleLabel.setText(titleLabel.getText() + ": " + veicle.getModel());

	}

	@FXML
	public void setStandardPrices() {
		kmField.setText(String.valueOf(veicle.getType().getPriceForKm()));
		timeField.setText(String.valueOf(veicle.getType().getPriceForDay()));
	}

	@FXML
	public void setPrices() {
		veicle.setPriceForDay(Double.parseDouble(timeField.getText()));
		veicle.setPriceForKm(Double.parseDouble(kmField.getText()));

		exitAction();
	}

	@FXML
	public void exitAction() {
		dispatcher.renderView("veicles", new ObjectsCollector<User, Type>(user, veicle.getType()));
	}

}
