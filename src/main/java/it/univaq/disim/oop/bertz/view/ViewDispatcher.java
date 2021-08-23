package it.univaq.disim.oop.bertz.view;

import java.io.IOException;

import it.univaq.disim.oop.bertz.controller.DataInitializable;
import it.univaq.disim.oop.bertz.domain.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewDispatcher {

    private static final String FXML_SUFFIX = ".fxml";
    private static final String RESOURCE_BASE = "/views/";
	private static ViewDispatcher instance = new ViewDispatcher();
	
    private Stage stage;
    private BorderPane layout;
    
	private ViewDispatcher() {
	}

	public static ViewDispatcher getInstance() {
		return instance;
	}
	
    
    public void loginView(Stage stage) throws ViewException {
        this.stage = stage;
        Parent loginView = loadView("login").getView();
        Scene scene = new Scene(loginView);
        stage.setScene(scene);
        stage.show();
    }

    /*public void loggedIn() {
        try {
             layout = (BorderPane) loadView("layout");
             Parent home = loadView("home");
			 layout.setCenter(home);
             Scene scene = new Scene(layout);
             stage.setScene(scene);
        } catch (ViewException e) {
             e.printStackTrace();
             renderError(e);
        }
    }*/

    public void logout() {
  		try {
  			Parent loginView = loadView("login").getView();
  			Scene scene = new Scene(loginView);
  			stage.setScene(scene);
  		} catch (ViewException e) {
  			renderError(e);
  		}
  	}
    
   
    public <T> void renderView(String viewName, T data) {
		try {
			View<T> view = loadView(viewName);
			DataInitializable<T> controller = view.getController();
			controller.initializeData(data);
			layout.setCenter(view.getView());
			
		} catch (ViewException e) {
			renderError(e);
		}
	}

    public void renderError(Exception e) {
        e.printStackTrace();
        System.exit(1);
    }

   /*
     public void renderView(String viewName) {
        try {
            Parent view = loadView(viewName);
            layout.setCenter(view);
        } catch (ViewException e) {
            renderError(e);
        }
    }
    private Parent loadView(String view) throws ViewException {
        try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource(RESOURCE_BASE + view + FXML_SUFFIX));
             return loader.load();
        } catch (IOException e) {
             e.printStackTrace();
             throw new ViewException(e);
        } 
    }*/
    private <T> View<T> loadView(String viewName) throws ViewException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(RESOURCE_BASE + viewName + FXML_SUFFIX));
			Parent parent = (Parent) loader.load();
			return new View<>(parent, loader.getController());

		} catch (IOException ex) {
			throw new ViewException(ex);
		}
	}

	public void signup(){
		System.out.println("CALMA, prima o poi arriverà");
	}

	 public void loggedIn(User utente) {
	
		 
		 try {
				View<User> layoutView = loadView("layout");
				DataInitializable<User> layoutController = layoutView.getController();
				layoutController.initializeData(utente);

				layout = (BorderPane) layoutView.getView();
				//Anche in questo caso viene passato l'utente perche' nella vista di 
				//benvenuto il testo varia a seconda se e' docente od utente
				renderView("home", utente);
				Scene scene = new Scene(layout);
				stage.setScene(scene);
			} catch (ViewException e) {
				renderError(e);
			
			}
	 	
	 
	 }
}