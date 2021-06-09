package it.univaq.disim.oop.bertz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Bertz extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
			Parent loginWindow = loader.load();
			Scene scene= new Scene(loginWindow);
			stage.setScene(scene);
			stage.show();
	}

}
