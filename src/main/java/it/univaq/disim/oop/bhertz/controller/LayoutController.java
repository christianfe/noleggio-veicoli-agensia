package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.domain.Admin;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.MenuElement;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class LayoutController implements Initializable, DataInitializable<User> {
	
	private static final MenuElement MENU_HOME[] ={ new MenuElement("Home", "home"), new MenuElement("Automezzi", "type"), new MenuElement("Noleggi", "rental"), new MenuElement("Manutenzioni", "maintenance")};
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
		//menuBar.getChildren().add(new Separator());
		for (MenuElement menu : MENU_HOME)
			menuBar.getChildren().add(createButton(menu));
		if (user instanceof Admin)
			menuBar.getChildren().addAll(createButton(MENU_ADMIN));
		
	}
	
	private Button createButton(MenuElement viewItem) {
		
		menuBar.setSpacing(12);
		
		Button button = new Button(viewItem.getNome());
		button.setStyle("-fx-background-color: #FFD817; -fx-font-size: 16;  -fx-cursor: hand;");
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
