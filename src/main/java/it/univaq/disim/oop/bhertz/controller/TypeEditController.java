package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TypeEditController extends ViewUtility
implements Initializable, DataInitializable<ObjectsCollector<User, Type>> {

	@FXML
	private Label labelTitle;
	@FXML
	private Label labelError;
	@FXML
	private TextField nameField;
	@FXML
	private TextField priceForDayField;
	@FXML
	private TextField priceForKmField;
	@FXML
	private Button saveButton;

	private ViewDispatcher dispatcher;
	private TypesService typeService;
	private ObjectsCollector<User, Type> objectsCollector;
	private boolean creatingNewType;

	public TypeEditController() {
		dispatcher = ViewDispatcher.getInstance();
		typeService = BhertzBusinessFactory.getInstance().getTypesService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		saveButton.disableProperty().bind(nameField.textProperty().isEmpty()
				.or(priceForDayField.textProperty().isEmpty().or(priceForKmField.textProperty().isEmpty())));
		super.addForbiddenCharCheck(nameField, priceForDayField, priceForKmField);
		super.setOnlyNumberField(priceForDayField, priceForKmField);
	}

	@Override
	public void initializeData(ObjectsCollector<User, Type> objectsCollector) {
		this.objectsCollector = objectsCollector;
		this.creatingNewType = objectsCollector.getObjectB() == null;
		if (!creatingNewType) {
			labelTitle.setText("Modifica Tipologia: ");
			this.nameField.setText(objectsCollector.getObjectB().getName());
			this.priceForDayField.setText("" + objectsCollector.getObjectB().getPriceForDay());
			this.priceForKmField.setText("" + objectsCollector.getObjectB().getPriceForKm());
		}
	}

	@FXML
	public void saveAction(ActionEvent e) {
		try {

			if (this.creatingNewType)
				typeService.addType(new Type(0, nameField.getText(), Double.parseDouble(priceForKmField.getText()),
						Double.parseDouble(priceForDayField.getText())));
			else
				typeService.setType(objectsCollector.getObjectB().getId(), nameField.getText(),
						Double.parseDouble(priceForKmField.getText()), Double.parseDouble(priceForDayField.getText()));
			dispatcher.renderView("type", objectsCollector.getObjectA());
		} catch (NumberFormatException e1) {
			labelError.setText("Formato numerico non valido");
		} catch (BusinessException e1) {
			dispatcher.renderError(e1);
		}
	}

}
