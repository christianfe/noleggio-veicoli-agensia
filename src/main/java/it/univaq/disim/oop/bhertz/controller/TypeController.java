package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMTypesServiceImpl;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
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

public class TypeController implements Initializable, DataInitializable<User>{
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
	private TableColumn<Type, Button> actionColumn;

	private ViewDispatcher dispatcher;
	private TypesService typesService ;


	public TypeController() {
		dispatcher = ViewDispatcher.getInstance();
		typesService = new RAMTypesServiceImpl();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		hoursColumn.setCellValueFactory(new PropertyValueFactory<>("priceForDay"));
		kmColumn.setCellValueFactory(new PropertyValueFactory<>("priceForKm"));

		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Type, Button> param) -> {
			final Button veicleButton = new Button("Seleziona");
			veicleButton.setOnAction((ActionEvent event) -> {
				//dispatcher.renderView("appelli", param.getValue());
			});
			return new SimpleObjectProperty<Button>(veicleButton);
		});
	}

	@Override
	public void initializeData(User user){
		try {
			List<Type> types = typesService.getAllTypes();
			ObservableList<Type> insegnamentiData = FXCollections.observableArrayList(types);
			typesTable.setItems(insegnamentiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
}
