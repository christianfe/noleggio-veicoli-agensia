package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMTypesServiceImpl;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMVeicleserviceImpl;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VeiclesController implements Initializable, DataInitializable<Type>{

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
	private TableColumn<Veicle, Button> actionColumn;

	private ViewDispatcher dispatcher;
	private VeiclesService veiclesService; 

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
		consumiColumn.setCellValueFactory(new PropertyValueFactory<>("consumption"));
		kmColumn.setCellValueFactory(new PropertyValueFactory<>("km"));
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Veicle, Button> param) -> {
			final Button veicleButton = new Button("Seleziona");
			veicleButton.setOnAction((ActionEvent event) -> {
			//	dispatcher.renderView("veicles", param.getValue());
			});
			return new SimpleObjectProperty<Button>(veicleButton);
		});

	}
	
	@Override
	public void initializeData(Type t){
		try {
			List<Veicle> veicleList = veiclesService.getVeiclesByType(t);
			ObservableList<Veicle> veiclesData = FXCollections.observableArrayList(veicleList);
			veiclesTable.setItems(veiclesData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
}
