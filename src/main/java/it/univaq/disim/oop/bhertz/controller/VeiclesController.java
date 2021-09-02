package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMVeicleserviceImpl;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
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

public class VeiclesController implements Initializable, DataInitializable<ObjectsCollector>{

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

	public VeiclesController(){
		dispatcher = ViewDispatcher.getInstance();
		try {
			veiclesService = new RAMVeicleserviceImpl();
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
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
			MenuButton localMenuButton= new MenuButton("Menu");

			MenuItem menuRent = new MenuItem("Noleggia Veicolo");
			MenuItem menuEdit = new MenuItem("Modifica Tipologia");
			MenuItem menuDelete = new MenuItem("Elimina Tipologia");
			if (this.user.getRole() == 2) localMenuButton.getItems().add(menuRent);
			else {
				localMenuButton.getItems().add(menuEdit);
				localMenuButton.getItems().add(menuDelete);
			}

			menuRent.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("startRent", new ObjectsCollector<User, Veicle>(user, param.getValue()));
			});
			
					
			return new SimpleObjectProperty<MenuButton>(localMenuButton);
		});
	}


	@Override
	public void initializeData(ObjectsCollector objColl){
		
		ObjectsCollector<User, Type> ArgumentsData = objColl;
		titleLabel.setText(titleLabel.getText() + " " +  ArgumentsData.getObjectB().getName() );
		this.user = (User) objColl.getObjectA();
		if (user.getRole() == 1)
			actionColumn.setVisible(false);
		try {
			List<Veicle> veicleList = veiclesService.getVeiclesByType((Type)objColl.getObjectB());
			ObservableList<Veicle> veiclesData = FXCollections.observableArrayList(veicleList);
			veiclesTable.setItems(veiclesData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
}
