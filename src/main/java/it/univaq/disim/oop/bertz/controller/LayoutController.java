package it.univaq.disim.oop.bertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bertz.domain.Admin;
import it.univaq.disim.oop.bertz.domain.User;
import it.univaq.disim.oop.bertz.view.MenuElement;

import it.univaq.disim.oop.bertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class LayoutController implements Initializable, DataInitializable<User> {
	
	private static final MenuElement MENU_HOME[] ={ new MenuElement("Home", "home"), new MenuElement("Automezzi", ""), new MenuElement("Noleggi", ""), new MenuElement("Manutenzioni", "")};
	private static final MenuElement MENU_ADMIN =  new MenuElement("Utenti", "user");
	
	@FXML
	private VBox menuBar;
	
	private ViewDispatcher dispatcher;
	
	private User user;

	public void initialize(URL location, ResourceBundle resources) {
		this.dispatcher = ViewDispatcher.getInstance();
	}
	
	@Override
	public void initializeData(User user) {
		this.user = user;
		if (user instanceof Admin)
			menuBar.getChildren().addAll(createButton(MENU_ADMIN));
		//menuBar.getChildren().add(new Separator());
		for (MenuElement menu : MENU_HOME) {
			menuBar.getChildren().add(createButton(menu));
		}
		
		
		
	}
	
	private Button createButton(MenuElement viewItem) {
		Button button = new Button(viewItem.getNome());
		button.setStyle("-fx-background-color: yellow; -fx-font-size: 14;");
		button.setTextFill(Paint.valueOf("black"));
		button.setPrefHeight(10);
		button.setPrefWidth(180);
		button.setOnAction((ActionEvent event) -> {
			dispatcher.renderView(viewItem.getVista(), user);
		});
		return button;
	}
	
	@FXML
	public void logoutAction(MouseEvent event) {
		dispatcher.logout();
	}
	
}
