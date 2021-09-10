package it.univaq.disim.oop.bhertz.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class QuotationController extends ViewUtility
		implements Initializable, DataInitializable<ObjectsCollector<User, Veicle>> {

	@FXML
	private Label titleLabel;
	@FXML
	private DatePicker dateStartField;
	@FXML
	private DatePicker dateEndField;
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
		dateStartField.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setDisable(item.isBefore(LocalDate.now()));
			}
		});
		dateEndField.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setDisable(item.isBefore(LocalDate.now()));
			}
		});
	}

	@Override
	public void initializeData(ObjectsCollector<User, Veicle> collector) {
		this.user = collector.getObjectA();
		this.veicle = collector.getObjectB();
		titleLabel.setText(titleLabel.getText() + " per: " + veicle.getModel());
		// sliderPositionLabel.textProperty().bind(Bindings.format("%.2f",
		// kmSlider.valueProperty()));

	}

	public void calculateTimeQuotation() {
		try {
			LocalDate startDate = dateStartField.getValue();
			LocalDate endDate = dateEndField.getValue();

			if (startDate.isAfter(endDate))
				timeQuotationLabel.setText("Macchine del tempo non disponibili");
			else {
				float differenceDays = ChronoUnit.DAYS.between(startDate, endDate);
				double PriceQuotation = differenceDays * veicle.getPriceForDay();
				timeQuotationLabel.setText(PriceQuotation + " Euro");
			}
		} catch (NullPointerException e) {
		}
	}

	@FXML
	public void sliderDragAction() {

		double kmSelected = kmSlider.getValue();
		double priceQuotation = kmSelected * veicle.getPriceForKm();

		sliderPositionLabel.setText(String.format("%.02f", kmSelected));
		kmQuotationLabel.setText(String.format("%.02f", priceQuotation));
	}

	@FXML
	public void exitAction() {
		dispatcher.renderView("veicles", new ObjectsCollector<User, Type>(user, veicle.getType()));
	}

	@FXML
	public void checkDateStart(ActionEvent e) {
		try {
			dateEndField.setDayCellFactory(d -> new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					setDisable(item.isBefore(dateStartField.getValue()));
				}
			});
		} catch (NullPointerException e1) {
		}
		calculateTimeQuotation();
	}

	@FXML
	public void checkDateEnd(ActionEvent e) {
		try {
			// if (dateEndField.getValue().isBefore(dateStartField.getValue()))
			// dateEndField.setValue(dateStartField.getValue());
			dateStartField.setDayCellFactory(d -> new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					setDisable(item.isBefore(LocalDate.now()) || item.isAfter(dateEndField.getValue()));
				}
			});
		} catch (NullPointerException e1) {
		}
		calculateTimeQuotation();
	}
}
