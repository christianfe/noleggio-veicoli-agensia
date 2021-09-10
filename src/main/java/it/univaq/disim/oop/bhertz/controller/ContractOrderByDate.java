package it.univaq.disim.oop.bhertz.controller;

import java.util.Comparator;

import it.univaq.disim.oop.bhertz.domain.Contract;

public class ContractOrderByDate implements Comparator<Contract> {

	@Override
	public int compare(Contract o1, Contract o2) {

		if (o1.getStart().isBefore(o2.getStart()))
			return -1;
		else
			return 1;
	}

	/*
	 * classe che implementa l'interfaccia "Comparator<T>", questa specifica classe
	 * ha il compito di dare un ordine ai contratti in base alle loro date. La lista
	 * ordina i contratti in ordine cronologico. Questo ordinamento è
	 * particolarmente utile, in quanto è alla base del corretto funzionamento del
	 * meccanismo che trova i periodi in cui un veicolo è disponibile per il
	 * noleggio
	 */

}
