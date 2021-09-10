package it.univaq.disim.oop.bhertz.controller;

import java.util.Comparator;

import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;

public class ContractOrder implements Comparator<Contract> {

	@Override
	public int compare(Contract o1, Contract o2) {
		return getValue(o1.getState()) - getValue(o2.getState());
	}

	public int getValue(ContractState state) {

		switch (state) {
			case BOOKED:
				return 1;
			case MAINTENANCE:
				return 2;
			case ACTIVE:
				return 3;
			case ENDED:
				return 4;
		}

		return 0;
	}

	/*
	 * Classe che implemeta l'interfaccia "Comparator<T>", questa specifica classe
	 * ha il compito di ordinare i contratti in base al loro stato, i contratti
	 * prenotati vanno per primi, mentre quelli terminati venno per ultimi. In
	 * questo modo nella tabella dei contratti verranno visualizzati per primi i
	 * contratti che necessitano di maggiori attenzioni da parte dell'operatore,
	 * lasciando per ultimi i contratti chiusi, di minor interesse.
	 */
}
