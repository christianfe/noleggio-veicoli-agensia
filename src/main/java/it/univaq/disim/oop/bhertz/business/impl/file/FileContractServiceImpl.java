package it.univaq.disim.oop.bhertz.business.impl.file;

import java.time.LocalDate;
import java.util.List;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public class FileContractServiceImpl implements ContractService {

	@Override
	public List<Contract> getAllContracts(int type) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contract getContractByID(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contract getContractByDate(Veicle veicle, LocalDate date) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contract> getContractsByUser(int type, User user) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addContract(Contract contract) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContract(Contract contract) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPaid(Integer id, boolean value) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeContract(Integer id) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Contract> getContractsByVeicle(int type, Integer idVeicle) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}}
