package it.univaq.disim.oop.bertz.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewDispatcher {

	private static final String PREFIX = "/views/";
	private static final String SUFFIX = ".fxml";
	private static ViewDispatcher dispatcher = new ViewDispatcher();

	private Stage stage;
	private BorderPane layout;

	public void loginView(Stage stage) throws IOException {
	
	this.stage = stage;
	Parent loginWindow = loadView("login").getView();
	Scene scene= new Scene(loginWindow);
	stage.setScene(scene);
	stage.show();

	}

	public void loadView(String viewName) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(PREFIX + viewName + SUFFIX));

	}



}
