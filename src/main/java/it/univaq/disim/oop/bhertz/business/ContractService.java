package it.univaq.disim.oop.bhertz.business;

import java.time.LocalDate;
import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public interface ContractService {
	
	//int type; 0: sostuituteVeicle = false - 1: sostuituteVeicle = true - 2: sostuituteVeicle = false || true 
	
	List<Contract> getAllContracts(int type) throws BusinessException;
	
	Contract getContractByID(int id) throws BusinessException;
	
	Contract getContractByDate(Veicle veicle, LocalDate date) throws BusinessException;

	List<Contract> getContractsByUser(int type, User user) throws BusinessException;
	
	void addContract(Contract contract) throws BusinessException;
	
	void setContract(Contract contract) throws BusinessException;
	
	void setPaid(Integer id, boolean value) throws BusinessException;
	
	void removeContract(Integer id) throws BusinessException;
	
	List<Contract> getContractsByVeicle(int type, Integer idVeicle) throws BusinessException;
	
}
