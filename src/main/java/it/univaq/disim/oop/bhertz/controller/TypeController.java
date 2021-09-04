package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.TypeNotEmptyException;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TypeController extends ViewUtility implements Initializable, DataInitializable<User> {
	@FXML
	private Label titleLabel;
	@FXML
	private TableView<Type> typesTable;
	@FXML
	private TableColumn<Type, Integer> nameColumn;
	@FXML
	private TableColumn<Type, String> dayColumn;
	@FXML
	private TableColumn<Type, String> kmColumn;
	@FXML
	private TableColumn<Type, MenuButton> actionColumn;
	@FXML
	private Button addTypeButton;

	private ViewDispatcher dispatcher;

	private TypesService typesService;
	private User user;

	public TypeController() {
		dispatcher = ViewDispatcher.getInstance();
		typesService = BhertzBusinessFactory.getInstance().getTypesService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		dayColumn.setCellValueFactory((CellDataFeatures<Type, String> param) -> {
			return new SimpleStringProperty(param.getValue().getPriceForDay() + " €/h");
		});

		kmColumn.setCellValueFactory((CellDataFeatures<Type, String> param) -> {
			return new SimpleStringProperty(param.getValue().getPriceForKm() + " €/km");
		});

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Type, MenuButton> param) -> {
			MenuButton localMenuButton = new MenuButton("Menu");

			MenuItem menuView = new MenuItem("Visualizza Veicoli");
			MenuItem menuEdit = new MenuItem("Modifica Tipologia");
			MenuItem menuDelete = new MenuItem("Elimina Tipologia");
			localMenuButton.getItems().add(menuView);
			if (this.user.getRole() == 0) {
				localMenuButton.getItems().add(menuEdit);
				localMenuButton.getItems().add(menuDelete);
			}

			menuView.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("veicles", new ObjectsCollector<User, Type>(user, param.getValue()));
			});
			menuEdit.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("typeEdit", new ObjectsCollector<User, Type>(this.user, param.getValue()));
			});
			menuDelete.setOnAction((ActionEvent event) -> {
				if (JOptionPane.showConfirmDialog(null, "Confermi di voler eliminare la tipologia selezionata?", "Eliminare?", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == 0) {
					try {
						typesService.deleteType(param.getValue().getId());
					} catch (TypeNotEmptyException e) {
						JOptionPane.showMessageDialog(null, "Prima di poter eliminare una tipologia devono essere eliminati tutti i veicoli associati", "Errore", JOptionPane.ERROR_MESSAGE);
					} catch (BusinessException e) {
						e.printStackTrace();
					}
					dispatcher.renderView("type", this.user);
				}
			});

			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});
	}

	@Override
	public void initializeData(User user) {
		this.user = user;
		
		if (user.getRole() == 2 || user.getRole()== 1)
			addTypeButton.setVisible(false);
		
		try {
			List<Type> types = typesService.getAllTypes();
			ObservableList<Type> typesData = FXCollections.observableArrayList(types);
			typesTable.setItems(typesData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
	
	@FXML
	private void addTypeAction(ActionEvent e) {
		this.dispatcher.renderView("typeEdit", new ObjectsCollector<User, Type>(this.user, null));
	}
}
