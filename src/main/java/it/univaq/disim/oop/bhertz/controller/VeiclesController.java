package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
import it.univaq.disim.oop.bhertz.view.BigObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VeiclesController extends ViewUtility
		implements Initializable, DataInitializable<ObjectsCollector<User, Type>> {

	@FXML
	private Label titleLabel;
	@FXML
	private TableView<Veicle> veiclesTable;
	@FXML
	private TableColumn<Veicle, String> modelColumn;
	@FXML
	private TableColumn<Veicle, String> consumiColumn;
	@FXML
	private TableColumn<Veicle, String> kmColumn;
	@FXML
	private TableColumn<Veicle, String> stateColumn;
	@FXML
	private TableColumn<Veicle, MenuButton> actionColumn;
	@FXML
	private TableColumn<Veicle, String> priceHourColumn;
	@FXML
	private TableColumn<Veicle, String> priceKmColumn;
	@FXML
	private TableColumn<Veicle, String> fuelColumn;
	@FXML
	private Button addVeicleButton;
	@FXML
	private CheckBox veicleFreeCheckBox;
	@FXML
	private CheckBox veicleBusyCheckBox;
	@FXML
	private CheckBox veicleManteinanceCheckBox;
	

	private ViewDispatcher dispatcher;
	private VeiclesService veiclesService;
	private User user;
	private ObjectsCollector<User, Type> objectsCollector;

	public VeiclesController() {
		dispatcher = ViewDispatcher.getInstance();
		veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
		
		try {
			veiclesService.refreshAllStates();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		priceHourColumn.setCellValueFactory((CellDataFeatures<Veicle, String> param) -> {
			return new SimpleStringProperty(param.getValue().getPriceForDay() + "  €/giorno");
		});
		priceKmColumn.setCellValueFactory((CellDataFeatures<Veicle, String> param) -> {
			return new SimpleStringProperty(param.getValue().getPriceForKm() + "  €/km");
		});

		modelColumn.setCellValueFactory((CellDataFeatures<Veicle, String> param) -> {
			return new SimpleStringProperty(param.getValue() + "");
		});
		consumiColumn.setCellValueFactory((CellDataFeatures<Veicle, String> param) -> {
			return new SimpleStringProperty(param.getValue().getConsumption() + " km/l");
		});
		kmColumn.setCellValueFactory((CellDataFeatures<Veicle, String> param) -> {
			return new SimpleStringProperty(String.format("%.01f", param.getValue().getKm()) + " km");
		});
		stateColumn.setCellValueFactory((CellDataFeatures<Veicle, String> param) -> {
			String s = "";
			switch (param.getValue().getState()) {
			case FREE:
				s = "Libero";
				break;
			case BUSY:
				s = "Occupato";
				break;
			case MAINTENANCE:
				s = "In Manutenzione";
				break;
			}
			return new SimpleStringProperty(s);
		});
		fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuel"));

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Veicle, MenuButton> param) -> {
			MenuButton localMenuButton = new MenuButton("Menu");

			MenuItem menuRent = new MenuItem("Noleggia Veicolo");
			MenuItem menuQuotation = new MenuItem("Calcola Preventivo");
			MenuItem menuEdit = new MenuItem("Modifica Veicolo");
			MenuItem menuDelete = new MenuItem("Elimina Veicolo");
			MenuItem menuFeedback = new MenuItem("Visualizza Feedback");
			MenuItem menuPrice = new MenuItem("Modifica tariffe Veicolo");

			switch (this.user.getRole()) {
				case 2:
					localMenuButton.getItems().add(menuRent);
					localMenuButton.getItems().add(menuQuotation);
					break;
				case 0:
					localMenuButton.getItems().add(menuEdit);
					localMenuButton.getItems().add(menuDelete);
					localMenuButton.getItems().add(menuPrice);
					break;

			}

			localMenuButton.getItems().add(menuFeedback);

			menuRent.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("startRent", new ObjectsCollector<User, Veicle>(user, param.getValue()));
			});

			menuPrice.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("setPrices", new ObjectsCollector<User, Veicle>(user, param.getValue()));
			});

			menuFeedback.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("feedback", new ObjectsCollector<User, Veicle>(user, param.getValue()));
			});

			menuDelete.setOnAction((ActionEvent event) -> {
				if (param.getValue().getState() != VeicleState.FREE)
					JOptionPane.showMessageDialog(null, "E' possibile modificare solo veicoli Liberi", "Errore",
							JOptionPane.ERROR_MESSAGE);
				else if (JOptionPane.showConfirmDialog(null,
						"Confermi di voler eliminare il Veicolo selezionato e tutti i contratti associati?",
						"Eliminare?", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == 0) {
					try {
						veiclesService.deleteVeicle(param.getValue().getId());
					} catch (BusinessException e) {
						dispatcher.renderError(e);
					}
					dispatcher.renderView("veicles", objectsCollector);
				}
			});

			menuEdit.setOnAction((ActionEvent event) -> {
				if (param.getValue().getState() != VeicleState.FREE)
					JOptionPane.showMessageDialog(null, "E' possibile eliminare solo veicoli Liberi", "Errore",
							JOptionPane.ERROR_MESSAGE);
				else
					dispatcher.renderView("veicleEdit", new BigObjectsCollector<User, Veicle, Type>(
							objectsCollector.getObjectA(), param.getValue(), objectsCollector.getObjectB()));
			});

			menuQuotation.setOnAction((ActionEvent event) -> {
				ObjectsCollector<User, Veicle> collector = new ObjectsCollector<>();
				collector.setObjectA(user);
				collector.setObjectB(param.getValue());
				dispatcher.renderView("quotation", collector);
			});

			return new SimpleObjectProperty<MenuButton>(localMenuButton);

		});

	}

	@Override
	public void initializeData(ObjectsCollector<User, Type> objectsCollector) {
		this.objectsCollector = objectsCollector;
		titleLabel.setText(titleLabel.getText() + " " + objectsCollector.getObjectB().getName());
		this.user = objectsCollector.getObjectA();

		switch (user.getRole()) {
			case 2:
				stateColumn.setVisible(false);
				veicleBusyCheckBox.setVisible(false);
				veicleManteinanceCheckBox.setVisible(false);
				veicleFreeCheckBox.setVisible(false);
			case 1:
				addVeicleButton.setVisible(false);
				break;

		}

		List<Veicle> veicleList = null;
		try {
			veicleList = this.veiclesService.getVeiclesByType(objectsCollector.getObjectB());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObservableList<Veicle> veiclesData = FXCollections.observableArrayList(veicleList);
		veiclesTable.setItems(veiclesData);
	}

	@FXML
	public void addVeicleAction(ActionEvent e) {
		dispatcher.renderView("veicleEdit", new BigObjectsCollector<User, Veicle, Type>(objectsCollector.getObjectA(),
				null, objectsCollector.getObjectB()));
	}
	
	@FXML
	public void veicleFilterAction(ActionEvent e) {
		List<Veicle> veicleList = null;
		try {
			veicleList = this.veiclesService.getVeiclesByStateAndType(objectsCollector.getObjectB(), veicleFreeCheckBox.isSelected(), veicleBusyCheckBox.isSelected(), veicleManteinanceCheckBox.isSelected());
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObservableList<Veicle> veiclesData = FXCollections.observableArrayList(veicleList);
		veiclesTable.setItems(veiclesData);
	}
}
