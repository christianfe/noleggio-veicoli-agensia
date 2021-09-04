package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ObjectsCollector;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class QuotationController implements Initializable, DataInitializable<ObjectsCollector<User, Veicle>> {

	@FXML
	private Label titleQuotation;
	@FXML
	private DatePicker startQuotation;
	@FXML
	private DatePicker endQuotation;
	@FXML
	private Label timeQuotationLabel;
	@FXML
	private Slider kmSlider;
	@FXML
	private Label sliderPositionLabel;
	@FXML
	private Label kmQuotationLabel;

	private Veicle veicle;
	private ViewDispatcher dispatcher;
	private User user;

	public QuotationController() {
		this.dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void initializeData(ObjectsCollector<User, Veicle> collector) {
		this.user = collector.getObjectA();
		this.veicle = collector.getObjectB();
		titleQuotation.setText(titleQuotation.getText() + " per: " + veicle.getModel());
		// sliderPositionLabel.textProperty().bind(Bindings.format("%.2f",
		// kmSlider.valueProperty()));

	}

	@FXML
	public void calculateTimeQuotation() throws NullPointerException {

		try {
			LocalDate startDate = startQuotation.getValue();
			LocalDate endDate = endQuotation.getValue();

			if (startDate.isAfter(endDate))
				timeQuotationLabel.setText("Macchine del tempo non disponibili");
			else {
				float differenceDays = ChronoUnit.DAYS.between(startDate, endDate);
				double PriceQuotation = differenceDays * veicle.getType().getPriceForDay();
				timeQuotationLabel.setText(PriceQuotation + " Euro");
			}

		} catch (NullPointerException e) {

		}
	}

	@FXML
	public void sliderDragAction() {

		double kmSelected = kmSlider.getValue();
		double priceQuotation = kmSelected * veicle.getType().getPriceForKm();

		sliderPositionLabel.setText(String.format("%.02f", kmSelected));
		kmQuotationLabel.setText(String.format("%.02f", priceQuotation));
	}

	@FXML
	public void exitAction() {
		dispatcher.renderView("veicles", new ObjectsCollector<User, Type>(user, veicle.getType()) );
	}
}
