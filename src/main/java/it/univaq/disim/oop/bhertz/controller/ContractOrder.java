package it.univaq.disim.oop.bhertz.controller;

import java.util.Comparator;

import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;

public class ContractOrder implements Comparator<Contract> {

	@Override
	public int compare(Contract o1, Contract o2) {
		return getValue(o1.getState())-getValue(o2.getState()) ;
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

}
