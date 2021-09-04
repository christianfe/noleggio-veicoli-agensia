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
}
