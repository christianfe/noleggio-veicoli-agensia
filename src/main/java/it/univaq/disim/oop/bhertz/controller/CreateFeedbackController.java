package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class CreateFeedbackController extends ViewUtility
		implements Initializable, DataInitializable<ObjectsCollector<User, Contract>> {

	@FXML
	private Label titleFeedback;
	@FXML
	private Slider sliderFeedback;
	@FXML
	private TextField descriptionFeedback;
	@FXML
	private Button confirmFeedback;
	@FXML
	private Button noFeedback;

	private ViewDispatcher dispatcher;
	private FeedbackService feedbackService;
	private User user;
	private Contract contract;

	public CreateFeedbackController() {
		dispatcher = ViewDispatcher.getInstance();
		feedbackService = BhertzBusinessFactory.getInstance().getFeedbackService();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		confirmFeedback.disableProperty().bind(descriptionFeedback.textProperty().isEmpty());
		super.addForbiddenCharCheck(descriptionFeedback);
	}

	@Override
	public void initializeData(ObjectsCollector<User, Contract> collector) {
		this.user = collector.getObjectA();
		this.contract = collector.getObjectB();

		titleFeedback
				.setText(titleFeedback.getText() + " per il noleggio del veicolo: " + contract.getVeicle().getModel());
	}

	@FXML
	public void createFeedback() {
		Feedback newFeedback = new Feedback();
		newFeedback.setBody(descriptionFeedback.getText());
		newFeedback.setContract(contract);
		newFeedback.setValutation((int) sliderFeedback.getValue());
		newFeedback.setDate(LocalDate.now());

		try {
			feedbackService.addFeedback(newFeedback);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
		confirmFeedback.disableProperty().unbind();
		confirmFeedback.setDisable(true);
		dispatcher.renderView("rental", user);

	}

	@FXML
	public void exitFeedback() {
		dispatcher.renderView("rental", user);
	}

}
