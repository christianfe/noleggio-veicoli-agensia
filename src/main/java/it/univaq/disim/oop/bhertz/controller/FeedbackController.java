package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;

public class FeedbackController 
	implements Initializable, DataInitializable<ObjectsCollector<User, Veicle>> {

	@FXML
	private Label titleLabel;
	@FXML
	private TableView<Feedback> feedbackTable;
	@FXML
	private TableColumn<Feedback, String> userColumn;
	@FXML
	private TableColumn<Feedback, String> valutationColumn;
	@FXML
	private TableColumn<Feedback, String> descriptionColumn;

	private User user;
	private Veicle veicle;
	private ViewDispatcher dispatcher;
	private FeedbackService feedbackService;

	public FeedbackController() {
		dispatcher = ViewDispatcher.getInstance();
		feedbackService = BhertzBusinessFactory.getInstance().getFeedbackService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		userColumn.setCellValueFactory((CellDataFeatures<Feedback, String> param) -> {
			return new SimpleStringProperty(param.getValue().getContract().getCustomer().getName());
		});
		valutationColumn.setCellValueFactory((CellDataFeatures<Feedback, String> param) -> {
			return new SimpleStringProperty("" + param.getValue().getValutation());
		});
		descriptionColumn.setCellValueFactory((CellDataFeatures<Feedback, String> param) -> {
			return new SimpleStringProperty(param.getValue().getBody());
		});

	}

	@Override
	public void initializeData(ObjectsCollector<User, Veicle> collector) {
		this.user = collector.getObjectA();
		this.veicle = collector.getObjectB();

		titleLabel.setText(titleLabel.getText() + " " + veicle.getModel());

		try {
			List<Feedback> feedbackList = this.feedbackService.getFeedbackByVeicle(veicle);
			//List<Feedback> feedbackList = new ArrayList<>();
			ObservableList<Feedback> feedbackData = FXCollections.observableArrayList(feedbackList);
			feedbackTable.setItems(feedbackData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	void exitFeedback() {
		dispatcher.renderView("veicles", new ObjectsCollector<User, Type>(user, veicle.getType()));
	}
}
