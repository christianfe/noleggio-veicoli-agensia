package it.univaq.disim.oop.bhertz.controller;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewUtility {
	/*
	 * In questa classe sono presenti metodi e parametri che devono essere usati in
	 * molti controllori per facilitare la complenzione dei codice, questi metodi
	 * sono scritti una sola volta e vengono richiamati dai controllori quando
	 * necessiario. Tutte le classi controllori sono figlie di questa classe, anche
	 * delle classi controllori dove non sono presenti TextField. questa scelta
	 * deriva dal voler facilitare futuri interventi di manutenzione al codice,
	 * facendo trovare al manutentore una classe già pronta ad accogliere nuovi
	 * metodi
	 */

	public static final int DAYS_VEICLE_BUSY_AFTER_RENT = 1;

	protected static final String[] FORBIDDEN_INPUT = { ";" };

	/*
	 * Il metodo di seguito aggiunge una Listener ai TextField passati come
	 * parametri la quale in caso di modifica del testo controlla che non siano
	 * stati scritti caratteri che potrebbero procurare errore al codice quando i
	 * dati vengono salvati sul file. Nel caso specifico è stato vietato solo il
	 * ";", carattere usato per dividere i dati nei file.
	 */
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

	/*
	 * Questo metodo implementa il controllo per far si che l'utente non possa
	 * inserire qualcosa di diverso dal formato hh:mm. Usato solamente nei campi che
	 * devono contenere l'orario
	 */
	protected void setTimeField(TextField... fields) {
		for (TextField field : fields)
			field.textProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue.length() > 5) {
					field.setText(oldValue);
					return;
				}
				Integer lenght = newValue.length();
				if (lenght <= 0)
					return;
				char c = newValue.charAt(lenght - 1);
				if ((lenght == 3 && c != ':') || (lenght != 3 && (c < '0' || c > '9')))
					field.setText(oldValue);
				int h = -1, m = -1;
				if (lenght >= 2)
					h = Integer.parseInt(newValue.substring(0, 2));
				if (lenght >= 4)
					m = Integer.parseInt(newValue.substring(3));
				if (h > 23 || m >= 60)
					field.setText(oldValue);
			});
	}
}
