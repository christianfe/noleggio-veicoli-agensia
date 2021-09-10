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

}
