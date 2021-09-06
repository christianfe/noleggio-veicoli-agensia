package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.List;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractType;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class StartRentController extends ViewUtility implements Initializable, DataInitializable<ObjectsCollector> {

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
	private Button cancelButton;
	@FXML
	private Label labelError;
	@FXML
	private TextArea aviableTextArea;

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
		dateStartField.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setDisable(item.isBefore(LocalDate.now()));
			}
		});
		dateEndField.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setDisable(item.isBefore(LocalDate.now()));
			}
		});
	}

	@Override
	public void initializeData(ObjectsCollector objectsCollector) {

		ObjectsCollector<User, Veicle> ArgumentsData = objectsCollector;
		this.user = ArgumentsData.getObjectA();
		this.veicle = ArgumentsData.getObjectB();

		titleLabel.setText(titleLabel.getText() + ": " + ArgumentsData.getObjectB().getModel() + " - "
				+ ArgumentsData.getObjectB().getPlate());
		labelError.setStyle(" -fx-alignment: CENTER;");

		String priceForDay = String.format("%.02f", veicle.getType().getPriceForDay());
		String PriceForKm = String.format("%.02f", veicle.getType().getPriceForKm());

		dailyCheckBox.setText(priceForDay + " €/day");
		kmCheckBox.setText(PriceForKm + " €/km");
		
		List<Contract> contractOfVeicle = factory.getContractService().getContractsByVeicle(veicle.getId());
		
		aviableTextArea.setText( factory.getVeiclesService().FindAviableDays(contractOfVeicle) );
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
			else {
				ContractService contractService = factory.getContractService();
				List<Contract> contractOfVeicle = contractService.getContractsByVeicle(veicle.getId());

				boolean oof = factory.getVeiclesService().isVeicleFree(veicle.getId(), dateStartField.getValue(),
						dateEndField.getValue(), contractOfVeicle);

				if (oof)
					System.out.println("si può fare");
				else
					System.out.println("non si può fare");

				Contract newContract = new Contract();
				newContract.setVeicle(veicle);
				newContract.setCustomer((Customer) user);
				newContract.setStartKm(veicle.getKm());
				newContract.setStart(dateStartField.getValue());
				newContract.setEnd(dateEndField.getValue());
				if (dailyCheckBox.isSelected()) {
					newContract.setType(ContractType.TIME);
					newContract.setPrice(veicle.getPriceForDay());
				} else if (kmCheckBox.isSelected()) {
					newContract.setType(ContractType.KM);
					newContract.setPrice(veicle.getPriceForKm());
				}
				veicle.setState(VeicleState.BUSY);

				contractService.addContract(newContract);
				labelError.setText(null);
				dispatcher.renderView("veicles", new ObjectsCollector<User, Type>(user, veicle.getType()));
			}
		} catch (NullPointerException E) {
			labelError.setText("Imposta data di inizio e fine del noleggio");
		}
	}

	@FXML
	public void checkDateStart(ActionEvent e) {
		try {
			dateEndField.setDayCellFactory(d -> new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					setDisable(item.isBefore(dateStartField.getValue()));
				}
			});
		} catch (NullPointerException e1) {
		}
	}

	@FXML
	public void checkDateEnd(ActionEvent e) {
		try {
			// if (dateEndField.getValue().isBefore(dateStartField.getValue()))
			// dateEndField.setValue(dateStartField.getValue());
			dateStartField.setDayCellFactory(d -> new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					setDisable(item.isBefore(LocalDate.now()) || item.isAfter(dateEndField.getValue()));
				}
			});
		} catch (NullPointerException e1) {
		}
	}

	@FXML
	public void cancelAction() {
		dispatcher.renderView("veicles", new ObjectsCollector<User, Type>(user, veicle.getType()));
	}
}
