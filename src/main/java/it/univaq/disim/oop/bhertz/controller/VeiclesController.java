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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VeiclesController implements Initializable, DataInitializable<ObjectsCollector<User, Type>> {

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
	private TableColumn<Veicle, VeicleState> stateColumn;
	@FXML
	private TableColumn<Veicle, MenuButton> actionColumn;

	private ViewDispatcher dispatcher;
	private VeiclesService veiclesService;
	private User user;
	private ObjectsCollector<User, Type> argumentsData;

	public VeiclesController() {
		dispatcher = ViewDispatcher.getInstance();
		veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
		consumiColumn.setCellValueFactory((CellDataFeatures<Veicle, String> param) -> {
			return new SimpleStringProperty(param.getValue().getConsumption() + " km/l");
		});
		kmColumn.setCellValueFactory((CellDataFeatures<Veicle, String> param) -> {
			return new SimpleStringProperty(param.getValue().getKm() + " km");
		});
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Veicle, MenuButton> param) -> {
			MenuButton localMenuButton = new MenuButton("Menu");

			MenuItem menuRent = new MenuItem("Noleggia Veicolo");
			MenuItem menuEdit = new MenuItem("Modifica Veicolo");
			MenuItem menuDelete = new MenuItem("Elimina Veicolo");
			if (this.user.getRole() == 2) {

				if (param.getValue().getState() == VeicleState.FREE)
					localMenuButton.getItems().add(menuRent);
				else localMenuButton.setVisible(false);

			} else {
				localMenuButton.getItems().add(menuEdit);
				localMenuButton.getItems().add(menuDelete);
			}

			menuRent.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("startRent", new ObjectsCollector<User, Veicle>(user, param.getValue()));
			});

			menuDelete.setOnAction((ActionEvent event) -> {
				if (param.getValue().getState() != VeicleState.FREE)
					JOptionPane.showMessageDialog(null, "Impostare il veicolo come libero prima di eliminarlo", "Errore", JOptionPane.ERROR_MESSAGE);
				else if (JOptionPane.showConfirmDialog(null, "Confermi di voler eliminare il Veicolo selezionato e tutti i contratti associati?", "Eliminare l'utente?", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == 0) {
					veiclesService.deleteVeicle(param.getValue().getId());
					dispatcher.renderView("veicles", argumentsData);
				}
			});
			
			menuEdit.setOnAction((ActionEvent event) -> {
			});

			
			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});
	}

	@Override
	public void initializeData(ObjectsCollector<User, Type> objectsCollector) {
		this.argumentsData = objectsCollector;
		titleLabel.setText(titleLabel.getText() + " " + argumentsData.getObjectB().getName());
		this.user = (User) objectsCollector.getObjectA();
		if (user.getRole() == 1)
			actionColumn.setVisible(false);
		try {
			List<Veicle> veicleList = veiclesService.getVeiclesByType((Type) objectsCollector.getObjectB());
			ObservableList<Veicle> veiclesData = FXCollections.observableArrayList(veicleList);
			veiclesTable.setItems(veiclesData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
	
	@FXML
	private void addVeicleAction(ActionEvent e) {
		dispatcher.renderView("veicleEdit", new BigObjectsCollector<User, Veicle, Type>(argumentsData.getObjectA(), null, argumentsData.getObjectB()));
	}
}
