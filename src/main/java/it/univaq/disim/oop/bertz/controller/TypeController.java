package it.univaq.disim.oop.bertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bertz.domain.User;
import it.univaq.disim.oop.bertz.domain.Type;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		hoursColumn.setCellValueFactory(new PropertyValueFactory<>("priceForDay"));
		kmColumn.setCellValueFactory(new PropertyValueFactory<>("priceForKm"));
			
		
		actionColumn.setStyle("-fx-alignment: CENTER;");
		actionColumn.setCellValueFactory((CellDataFeatures<Type, Button> param) -> {
			final Button veicleButton = new Button("Appelli");
			veicleButton.setOnAction((ActionEvent event) -> {
				//dispatcher.renderView("appelli", param.getValue());
			});
			return new SimpleObjectProperty<Button>(veicleButton);
		});
	}
	
	@Override
	public void initializeData(User user){
		titleLabel.setText("Noleggi in corso " + user.getName());
	}
}
