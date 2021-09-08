package it.univaq.disim.oop.bhertz.business;

import java.time.LocalDate;
import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public interface ContractService {
	
	//int type; 0: sostuituteVeicle = false - 1: sostuituteVeicle = true - 2: sostuituteVeicle = false || true 
	
	List<Contract> getAllContracts(int type);
	
	Contract getContractByID(int id);
	
	Contract getContractByDate(Veicle veicle, LocalDate date);

	List<Contract> getContractsByUser(int type, User user);
	
	void addContract(Contract contract);
	
	void setContract(Contract contract);
	
	void setPaid(Integer id, boolean value);
	
	void removeContract(Integer id);
	
	List<Contract> getContractsByVeicle(int type, Integer idVeicle);
	
}
