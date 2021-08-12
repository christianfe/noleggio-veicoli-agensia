package it.univaq.disim.oop.bertz;

import it.univaq.disim.oop.bertz.view.ViewDispatcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class Bertz extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		 ViewDispatcher dispatcher = ViewDispatcher.getInstance();
        dispatcher.loginView(stage);
	}

}
