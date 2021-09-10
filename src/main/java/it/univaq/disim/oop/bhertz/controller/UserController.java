package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.domain.Staff;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.beans.property.SimpleObjectProperty;
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

public class UserController extends ViewUtility implements Initializable, DataInitializable<Object> {

	@FXML
	private Label titleLabel;

	@FXML
	private TableView<User> staffTable;
	@FXML
	private TableColumn<User, String> usernameStaffColumn;
	@FXML
	private TableColumn<User, String> nameStaffColumn;
	@FXML
	private TableColumn<User, MenuButton> actionStaffColumn;

	@FXML
	private TableView<User> customerTable;
	@FXML
	private TableColumn<User, String> usernameCustomersColumn;
	@FXML
	private TableColumn<User, String> nameCustomersColumn;
	@FXML
	private TableColumn<User, MenuButton> actionCustomersColumn;

	private ViewDispatcher dispatcher;
	private UserService userServices;

	public UserController() {
		dispatcher = ViewDispatcher.getInstance();
		userServices = BhertzBusinessFactory.getInstance().getUserService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usernameStaffColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		nameStaffColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		actionStaffColumn.setStyle("-fx-alignment: CENTER;");
		actionStaffColumn.setCellValueFactory((CellDataFeatures<User, MenuButton> param) -> {
			MenuButton localMenuButton = new MenuButton("Menu");

			MenuItem menuEdit = new MenuItem("Modifica Utente");
			MenuItem menuDelete = new MenuItem("Elimina Utente");

			localMenuButton.getItems().add(menuEdit);
			localMenuButton.getItems().add(menuDelete);

			menuEdit.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("userEditor", new ObjectsCollector<User, User>(null, param.getValue()));
			});

			menuDelete.setOnAction((ActionEvent event) -> {
				if (JOptionPane.showConfirmDialog(null, "Confermi di voler eliminare l'utente selezionato?",
						"Eliminare l'utente?", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == 0) {
					try {
						userServices.deleteUser(param.getValue().getId());
					} catch (BusinessException e) {
						dispatcher.renderError(e);
					}
					dispatcher.renderView("user", null);
				}
			});

			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});

		usernameCustomersColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		nameCustomersColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		actionCustomersColumn.setStyle("-fx-alignment: CENTER;");
		actionCustomersColumn.setCellValueFactory((CellDataFeatures<User, MenuButton> param) -> {
			MenuButton localMenuButton = new MenuButton("Menu");

			MenuItem menuEdit = new MenuItem("Modifica Utente");
			MenuItem menuDelete = new MenuItem("Elimina Utente");

			localMenuButton.getItems().add(menuEdit);
			localMenuButton.getItems().add(menuDelete);

			menuEdit.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("userEditor", new ObjectsCollector<User, User>(null, param.getValue()));
			});

			menuDelete.setOnAction((ActionEvent event) -> {
				if (JOptionPane.showConfirmDialog(null, "Confermi di voler eliminare l'utente selezionato?",
						"Eliminare l'utente?", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == 0) {
					try {
						userServices.deleteUser(param.getValue().getId());
					} catch (BusinessException e) {
						dispatcher.renderError(e);
					}
					dispatcher.renderView("user", null);
				}
			});

			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});
	}

	@Override
	public void initializeData(Object o) {
		try {
			List<User> staffs = userServices.getUserByRole(1);
			ObservableList<User> staffData = FXCollections.observableArrayList(staffs);
			staffTable.setItems(staffData);

			List<User> users = userServices.getUserByRole(2);
			ObservableList<User> userData = FXCollections.observableArrayList(users);
			customerTable.setItems(userData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	public void addOperatorAction(ActionEvent event) {
		dispatcher.renderView("userEditor", new ObjectsCollector<User, User>(null, new Staff()));
	}

	public void addUserAction(ActionEvent event) {
		dispatcher.renderView("userEditor", new ObjectsCollector<User, User>(null, new Customer()));
	}

}
