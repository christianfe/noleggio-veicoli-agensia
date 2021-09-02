package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Contract;

public interface RentalService {
	
	List<Contract> getAllContracts() throws BusinessException;
	
	Contract getContractByID(int id) throws BusinessException;

	
}
