package it.univaq.disim.oop.bhertz.view;

import javafx.scene.control.TextField;

public class ViewUtility {

	protected static CharSequence FORBIDDEN_CHARS = ";";

	protected void addCheckListener(TextField... fields) {
		for (TextField field : fields)
			field.textProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue.contains(FORBIDDEN_CHARS)) field.setText(oldValue);
			});
	}
}
