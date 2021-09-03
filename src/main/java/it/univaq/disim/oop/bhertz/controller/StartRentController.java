package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractType;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class StartRentController implements Initializable, DataInitializable<ObjectsCollector> {

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
	private BhertzBusinessFactory factory;
	private ViewDispatcher dispatcher;

	public StartRentController() {
		this.dispatcher = dispatcher.getInstance();
		factory = BhertzBusinessFactory.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(ObjectsCollector objectsCollector) {

		ObjectsCollector<User, Veicle> ArgumentsData = objectsCollector;
		this.user = ArgumentsData.getObjectA();
		this.veicle = ArgumentsData.getObjectB();

		titleLabel.setText(titleLabel.getText() + ": " + ArgumentsData.getObjectB().getModel() + " - "
				+ ArgumentsData.getObjectB().getPlate());
		titleLabel.setStyle(" -fx-font-size: 15; -fx-alignment: LEFT;");

		String priceForDay = String.format("%.02f", veicle.getType().getPriceForDay());
		String PriceForKm = String.format("%.02f", veicle.getType().getPriceForKm());

		dailyCheckBox.setText(priceForDay + " €/day");
		kmCheckBox.setText(PriceForKm + " €/km");
	}

	@FXML
	public void dailyCheckAction(ActionEvent e) {
		if (dailyCheckBox.isSelected())
			kmCheckBox.setSelected(false);
	}

	@FXML
	public void kmCheckAction(ActionEvent e) {
		if (kmCheckBox.isSelected())
			dailyCheckBox.setSelected(false);
	}

	@FXML
	public void startContractAction(ActionEvent e) throws NullPointerException {

		try {
			if (!dailyCheckBox.isSelected() && !kmCheckBox.isSelected())
				labelError.setText("Selezionare una tipologia di contratto");
			else if (dateStartField.getValue().isAfter(dateEndField.getValue()))
				labelError.setText("Macchine del tempo non disponibili");
			else {
				ContractService contractService = factory.getContractService();

				Contract newContract = new Contract();
				newContract.setVeicle(veicle);
				newContract.setCustomer(user);
				newContract.setStartKm(veicle.getKm());
				newContract.setStart(dateStartField.getValue());
				newContract.setEnd(dateEndField.getValue());
				if (dailyCheckBox.isSelected())
					newContract.setType(ContractType.TIME);
				else if (kmCheckBox.isSelected())
					newContract.setType(ContractType.KM);

				veicle.setState(VeicleState.BUSY);

				contractService.addContract(newContract);
				labelError.setText(null);
				dispatcher.renderView("veicles", new ObjectsCollector<User, Type>(user, veicle.getType()));

			}
		} catch (NullPointerException E) {
			labelError.setText("Seleziona un periodo valido");

		}

	}

}
