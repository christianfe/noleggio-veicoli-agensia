package it.univaq.disim.oop.bhertz.view;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewUtility {

	public static final int DAYS_VEICLE_BUSY_AFTER_RENT = 1;

	protected static final String[] FORBIDDEN_INPUT = { ";" };

	protected void addForbiddenCharCheck(TextField... fields) {
		for (TextField field : fields)
			field.textProperty().addListener((observable, oldValue, newValue) -> {
				for (String s : FORBIDDEN_INPUT)
					if (newValue.contains(s))
						field.setText(oldValue);
			});
	}

	protected void addForbiddenCharCheck(TextArea... fields) {
		for (TextArea field : fields)
			field.textProperty().addListener((observable, oldValue, newValue) -> {
				for (String s : FORBIDDEN_INPUT)
					if (newValue.contains(s))
						field.setText(oldValue);
			});
	}

	protected void setOnlyNumberField(TextField... fields) {
		/*for (TextField field : fields) {
			field.textProperty().addListener((observable, oldValue, newValue) -> {
				char c = newValue.charAt(newValue.length() - 1);
				if (c < '0' || c > '9' || c > '.')
					field.setText(oldValue);
			});
		}*/
	}

	protected void setTimeField(TextField... fields) {
		for (TextField field : fields)
			field.textProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue.length() > 5 ) {
					field.setText(oldValue);
					return;
				}
				Integer lenght = newValue.length();
				if (lenght <= 0) return;
				char c = newValue.charAt(lenght - 1);
				if ((lenght == 3 && c != ':') || (lenght != 3 && (c < '0' || c > '9')))
					field.setText(oldValue);
				int h = -1, m = -1;
				if (lenght >= 2)
					h = Integer.parseInt(newValue.substring(0, 2));
				if (lenght >= 4)
					m = Integer.parseInt(newValue.substring(3));
				if (h > 23 || m  >= 60)
					field.setText(oldValue);
			});
	}
}
