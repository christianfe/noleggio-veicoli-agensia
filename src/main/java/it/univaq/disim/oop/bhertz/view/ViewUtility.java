package it.univaq.disim.oop.bhertz.view;

import javafx.scene.control.TextField;

public class ViewUtility {

	protected static final String[] FORBIDDEN_INPUT = { ";" };

	protected void addForbiddenCharCheck(TextField... fields) {
		for (TextField field : fields)
			field.textProperty().addListener((observable, oldValue, newValue) -> {
				for (String s : FORBIDDEN_INPUT)
					if (newValue.contains(s))
						field.setText(oldValue);
			});
	}

	protected void setOnlyNumberField(TextField... fields) {
		for (TextField field : fields)
			field.textProperty().addListener((observable, oldValue, newValue) -> {
				char c = newValue.charAt(newValue.length() - 1);
				if (c < '0' || c > '9')
					field.setText(oldValue);
			});
	}
}
