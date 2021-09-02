package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.User;

public interface ContractService {
	
	List<Contract> getAllContracts() throws BusinessException;
	
	Contract getContractByID(int id) throws BusinessException;

	List<Contract> getContractsByUser(User user) throws BusinessException;
}
