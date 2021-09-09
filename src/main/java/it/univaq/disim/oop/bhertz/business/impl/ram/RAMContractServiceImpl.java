package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ViewUtility;

public class RAMContractServiceImpl implements ContractService {

	private Map<Integer, Contract> contracts = new HashMap<>();
	private int counter = 1;

	@Override
	public List<Contract> getAllContracts(int type) throws BusinessException {
		List<Contract> result = new ArrayList<>();
		switch (type) {
		case 0:
			for (Contract c : contracts.values())
				if (c.isSostistuteContract() == false)
					result.add(c);
			break;
		case 1:
			for (Contract c : contracts.values())
				if (c.isSostistuteContract() == true)
					result.add(c);
			break;

		case 2:
			for (Contract c : contracts.values())
				result.add(c);
			break;
		}
		return result;
	}

	@Override
	public Contract getContractByID(int id) throws BusinessException {
		return contracts.get(id);
	}

	@Override
	public List<Contract> getContractsByUser(int type,User user) throws BusinessException {
		List<Contract> result = new ArrayList<>();
		for (Contract c : contracts.values())
			if (c.getCustomer().getId() == user.getId())
				switch (type) {
				case 0:
					if (!c.isSostistuteContract())
						result.add(c);
					break;
				case 1:
					if (c.isSostistuteContract())
						result.add(c);
					break;
				case 2:
					result.add(c);
					break;
				}

		return result;
	}

	@Override
	public void addContract(Contract contract) throws BusinessException {
		contract.setId(counter);
		contracts.put(counter++, contract);
	}

	@Override
	public void setPaid(Integer id, boolean value) throws BusinessException {
		Contract c = contracts.get(id);
		c.setPaid(value);
		contracts.put(id, c);
	}

	@Override
	public List<Contract> getContractsByVeicle(int type,Integer idVeicle) throws BusinessException {
		List<Contract> result = new ArrayList<>();
		for (Contract c : contracts.values())
			if (c.getVeicle().getId() == idVeicle)
				switch (type) {
				case 0:
					if (!c.isSostistuteContract())
						result.add(c);
					break;
				case 1:
					if (c.isSostistuteContract())
						result.add(c);
					break;
				case 2:
					result.add(c);
					break;
				}
		return result;
	}

	@Override
	public void setContract(Contract contract) throws BusinessException {
		contracts.put(contract.getId(), contract);
	}

	@Override
	public void removeContract(Integer id) throws BusinessException {
		contracts.remove(id);
	}

	@Override
	public Contract getContractByDate(Veicle veicle, LocalDate date) throws BusinessException {
		for (Contract c : contracts.values())
			if (c.getVeicle().getId() == veicle.getId() && (date.isEqual(c.getStart()) || (date.isAfter(c.getStart()) && date.isBefore(c.getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT + 1)))))
				return c;
		return null;
	}

}

