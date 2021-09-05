package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.domain.Notification;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.NotificationsService;

public class HomeController extends ViewUtility implements Initializable, DataInitializable<User> {

	@FXML
	private Label welcomeLabel;
	@FXML
	private Label notificationLabel;
	@FXML
	private TextArea notificationTextArea;

	private User user;
	private List<Notification> listByUser;
	private NotificationsService notificationService;

	public HomeController() {
		notificationService = BhertzBusinessFactory.getInstance().getNotificationsService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(User user) {
		this.user = user;
		welcomeLabel.setText("Benvenuto " + user.getName());
		if (user.getRole() == 2) {
			notificationLabel.setVisible(true);
			notificationTextArea.setVisible(true);
			listByUser = notificationService.getNotificationByUser(user.getId());
			for (Notification n : listByUser) {
				notificationTextArea.appendText("-" + n.getText() + "\n");
			}

		}

	}
}
