package it.univaq.disim.oop.bhertz.controller;

public class NotificationDictionary {

	/*
	 * Questa classe agisce come un vero e proprio dizionario. E' stata creata con
	 * lo scopo di rendere il codice più pulito e di facilitarne la scrittura, la
	 * manutenzione e la comprenzione. In questa prima versione solamente le
	 * notifiche hanno un loro dizionario; l'idea generale del dizionario è quella
	 * di settare ogni testo delle viste in modo da agevolarne l'uso dal lato utente
	 * ed eventualmente aggiunge la possibilità di creare un bilinguismo nel
	 * programma.
	 */

	public static final String END_RENT_APPOINTMENT_TITLE = "Appuntamento Restituzione Veicolo";
	public static final String END_RENT_APPOINTMENT_TEXT = "Il suo appuntamento per la restituzione del veicolo è stato fissato per il giorno: ";
	public static final String START_RENT_APPOINTMENT_TITLE = "Appuntamento Consegna Veicolo";
	public static final String START_RENT_APPOINTMENT_TEXT = "Il suo appuntamento per la consegna del veicolo è stato fissato per il giorno: ";
	public static final String START_MAINTENANCE_APPOINTMENT_TITLE = "Appuntamento Gestione Manutenzione";
	public static final String START_MAINTENANCE_APPOINTMENT_TEXT = "Il suo appuntamento per la gestione dell'assistenza è stato fissato per il giorno: ";
	public static final String END_MAINTENANCE_APPOINTMENT_TITLE = "Appuntamento fine manutenzione";
	public static final String END_MAINTENANCE_APPOINTMENT_TEXT = "Abbiamo risolto i problemi del veicolo. La consegna del veicolo è stata fissata per il giorno: ";
	public static final String END_MAINTENANCE_APPOINTMENT_TEXT_SUBSTITUTE = "Le abbiamo assegnato un veicolo sostituitvo. La consegna del veicolo è stata fissata per il giorno: ";

}
