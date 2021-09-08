package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.ContractType;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public class RAMContractServiceImpl implements ContractService {

	private Map<Integer, Contract> contracts = new HashMap<>();
	private VeiclesService veicleService;
	private UserService userService;
	private int counter = 1;

	public RAMContractServiceImpl() {
		this.veicleService = new RAMVeicleServiceImpl();
		this.userService = new RAMUserServiceImpl();

		Contract contract1 = new Contract();
		contract1.setCustomer((Customer) userService.getUsersByID(5));
		contract1.setVeicle(veicleService.getVeicleByID(1));
		contract1.setStart(LocalDate.of(2021, Month.SEPTEMBER, 10));
		contract1.setEnd(LocalDate.of(2021, Month.SEPTEMBER, 20));
		contract1.setPaid(false);
		contract1.setId(counter++);
		contract1.setState(ContractState.ACTIVE);
		contract1.setDeliverDateTime("ora");
		contract1.setReturnDateTime("ora");
		contract1.setType(ContractType.TIME);
		contracts.put(contract1.getId(), contract1);

		Contract contract2 = new Contract();
		contract2.setCustomer((Customer) userService.getUsersByID(4));
		contract2.setVeicle(veicleService.getVeicleByID(2));
		contract2.setStart(LocalDate.of(2021, Month.SEPTEMBER, 10));
		contract2.setEnd(LocalDate.of(2021, Month.SEPTEMBER, 20));
		contract2.setPaid(true);
		contract2.setId(counter++);
		contract2.setState(ContractState.ENDED);
		contract2.setDeliverDateTime("ora");
		contract2.setType(ContractType.KM);
		contracts.put(contract2.getId(), contract2);

		Contract contract3 = new Contract();
		contract3.setCustomer((Customer) userService.getUsersByID(5));
		contract3.setVeicle(veicleService.getVeicleByID(4));
		contract3.setStart(LocalDate.of(2021, Month.SEPTEMBER, 12));
		contract3.setEnd(LocalDate.of(2021, Month.SEPTEMBER, 13));
		contract3.setPaid(false);
		contract3.setId(counter++);
		contract3.setState(ContractState.ACTIVE);
		contract3.setDeliverDateTime("ora");
		contract3.setType(ContractType.KM);
		contracts.put(contract3.getId(), contract3);


		Contract contract4 = new Contract();
		contract4.setCustomer((Customer) userService.getUsersByID(5));
		contract4.setVeicle(veicleService.getVeicleByID(1));
		contract4.setStart(LocalDate.of(2021, Month.SEPTEMBER, 30));
		contract4.setEnd(LocalDate.of(2021, Month.OCTOBER, 5));
		contract4.setPaid(false);
		contract4.setId(counter++);
		contract4.setState(ContractState.ACTIVE);
		contract4.setDeliverDateTime("ora");
		contract4.setReturnDateTime("ora");
		contract4.setType(ContractType.TIME);
		contracts.put(contract4.getId(), contract4);

	}

	@Override
	public List<Contract> getAllContracts(int type){
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
	public Contract getContractByID(int id) {
		return contracts.get(id);
	}

	@Override
	public List<Contract> getContractsByUser(int type,User user) {
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
	public void addContract(Contract contract) {
		contract.setId(counter);
		contracts.put(counter++, contract);
	}

	@Override
	public void setPaid(Integer id, boolean value) {
		Contract c = contracts.get(id);
		c.setPaid(value);
		contracts.put(id, c);
	}

	@Override
	public List<Contract> getContractsByVeicle(int type,Integer idVeicle) {
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
	public void setContract(Contract contract) {
		contracts.put(contract.getId(), contract);
	}

	@Override
	public void removeContract(Integer id) {
		contracts.remove(id);
	}

	@Override
	public Contract getContractByDate(Veicle veicle, LocalDate date) {
		for (Contract c : contracts.values())
			if (c.getVeicle().getId() == veicle.getId() && (date.isEqual(c.getStart()) || (date.isAfter(c.getStart()) && date.isBefore(c.getEnd().plusDays(2)))))
				return c;
		return null;
	}

}

