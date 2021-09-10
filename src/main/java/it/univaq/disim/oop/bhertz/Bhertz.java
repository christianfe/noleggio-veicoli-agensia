package it.univaq.disim.oop.bhertz;

import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class Bhertz extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setResizable(false);
		ViewDispatcher.getInstance().loginView(stage);
	}

}
