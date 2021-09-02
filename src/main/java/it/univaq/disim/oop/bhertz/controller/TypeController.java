package it.univaq.disim.oop.bhertz.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMTypesServiceImpl;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TypeController implements Initializable, DataInitializable<User> {
	@FXML
	private Label titleLabel;
	@FXML
	private TableView<Type> typesTable;
	@FXML
	private TableColumn<Type, String> nameColumn;
	@FXML
	private TableColumn<Type, String> hoursColumn;
	@FXML
	private TableColumn<Type, String> kmColumn;
	@FXML
	private TableColumn<Type, MenuButton> actionColumn;

	private ViewDispatcher dispatcher;

	private TypesService typesService;
	private User user;

	private MenuButton localMenuButton;
	private MenuItem menuView;
	private MenuItem menuEdit;
	private MenuItem menuDelete;

	public TypeController() {
		dispatcher = ViewDispatcher.getInstance();
		typesService = new RAMTypesServiceImpl();	

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		hoursColumn.setCellValueFactory((CellDataFeatures<Type, String> param) -> {
			return new SimpleStringProperty(param.getValue().getPriceForDay() + " €/h");
		});
		kmColumn.setCellValueFactory((CellDataFeatures<Type, String> param) -> {
			return new SimpleStringProperty(param.getValue().getPriceForKm() + " €/km");
		});

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Type, MenuButton> param) -> {
			localMenuButton = new MenuButton("Menu");

			menuView = new MenuItem("Visualizza Veicoli");
			menuEdit = new MenuItem("Modifica Tipologia");
			menuDelete = new MenuItem("Elimina Tipologia");
			localMenuButton.getItems().add(menuView);
			if (this.user.getRole() == 0) {
				localMenuButton.getItems().add(menuEdit);
				localMenuButton.getItems().add(menuDelete);
			}
			

			menuView.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("veicles", param.getValue());
			});
			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});	}

	@Override
	public void initializeData(User user) {
		try {
			List<Type> types = typesService.getAllTypes();
			ObservableList<Type> typesData = FXCollections.observableArrayList(types);
			typesTable.setItems(typesData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
		this.user = user;

		/*
		MenuItem menuView = new MenuItem("Visualizza Veicoli");
		MenuItem menuEdit = new MenuItem("Modifica Tipologia");
		MenuItem menuDelete = new MenuItem("Elimina Tipologia");
		menuView.setOnAction((ActionEvent event) -> {
		    Object param = typesTable.getSelectionModel().getSelectedItem();
		    dispatcher.renderView("veicles", param);
		});
		localMenuButton.getItems().add(menuView);
		if (user.getRole() == 0) {
			menu.getItems().add(menuEdit);
			menu.getItems().add(menuDelete);
		}
		typesTable.setContextMenu(menu);*/
	}
}
