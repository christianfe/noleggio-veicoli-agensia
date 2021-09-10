package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.Bhertz;
import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.domain.Admin;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.MenuElement;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class LayoutController extends ViewUtility implements Initializable, DataInitializable<User> {

	private static final MenuElement MENU_HOME[] = { new MenuElement("Home", "home"),
			new MenuElement("Tipologie Di Veicoli", "type"), new MenuElement("Noleggi", "rental"),
			new MenuElement("Manutenzioni", "maintenance"), new MenuElement("Profilo", "userEditor") };
	private static final MenuElement MENU_ADMIN = new MenuElement("Utenti", "user");

	@FXML
	private VBox menuBar;

	private ViewDispatcher dispatcher;
	private UserService userService;
	private User user;

	public void initialize(URL location, ResourceBundle resources) {
		this.dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initializeData(User user) {
		userService = BhertzBusinessFactory.getInstance().getUserService();
		this.user = user;
		// menuBar.getChildren().add(new Separator());
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
			if (button.getText().equals(MENU_HOME[4].getNome()))
				try {
					dispatcher.renderView(viewItem.getVista(), new ObjectsCollector<User, User>(userService.getUsersByID(user.getId()), userService.getUsersByID(user.getId())));
				} catch (BusinessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else
				try {
					dispatcher.renderView(viewItem.getVista(), userService.getUsersByID(user.getId()));
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
		return button;
	}

	@FXML
	public void logoutAction(MouseEvent event) {
		dispatcher.logout();
	}

}
