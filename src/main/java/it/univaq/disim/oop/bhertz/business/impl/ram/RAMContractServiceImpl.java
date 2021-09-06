package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.ContractType;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.domain.User;

public class RAMContractServiceImpl implements ContractService  {

	private Map<Integer, Contract> contracts = new HashMap<>();
	private VeiclesService veicleService;
	private UserService userService;
	private int counter = 1;

	public RAMContractServiceImpl() throws BusinessException {
		this.veicleService = new RAMVeicleserviceImpl();
		this.userService = new RAMUserServiceImpl();

		Contract contract1 = new Contract();
		contract1.setCustomer((Customer) userService.getusersByID(5));
		contract1.setVeicle(veicleService.getVeicleByID(1));
		contract1.setStart( LocalDate.of(2014, Month.SEPTEMBER, 10) );
		contract1.setEnd(LocalDate.of(2014, Month.SEPTEMBER, 20) );
		contract1.setPaid(false);
		contract1.setId(counter++);
		contract1.setState(ContractState.ACTIVE);
		contract1.setDeliverDateTime("ora");
		contract1.setType(ContractType.TIME);
		contracts.put(contract1.getId(), contract1);

		Contract contract2 = new Contract();
		contract2.setCustomer((Customer) userService.getusersByID(4));
		contract2.setVeicle(veicleService.getVeicleByID(2));
		contract2.setStart( LocalDate.of(2014, Month.SEPTEMBER, 10) );
		contract2.setEnd(LocalDate.of(2014, Month.SEPTEMBER, 20) );
		contract2.setPaid(false);
		contract2.setId(counter++);
		contract2.setState(ContractState.ENDED);
		contract2.setDeliverDateTime("ora");
		contract2.setType(ContractType.KM);
		contracts.put(contract2.getId(), contract2);

		Contract contract3 = new Contract();
		contract3.setCustomer((Customer) userService.getusersByID(5));
		contract3.setVeicle(veicleService.getVeicleByID(4));
		contract3.setStart( LocalDate.of(2021, Month.SEPTEMBER, 12) );
		contract3.setEnd(LocalDate.of(2021, Month.SEPTEMBER, 13) );
		contract3.setPaid(false);
		contract3.setId(counter++);
		contract3.setState(ContractState.ACTIVE);
		contract3.setDeliverDateTime("ora");
		contract3.setType(ContractType.KM);
		contracts.put(contract3.getId(), contract3);
		
		Contract contract4 = new Contract();
		contract4.setCustomer((Customer) userService.getusersByID(5));
		contract4.setVeicle(veicleService.getVeicleByID(1));
		contract4.setStart( LocalDate.of(2014, Month.SEPTEMBER, 10) );
		contract4.setEnd(LocalDate.of(2014, Month.SEPTEMBER, 20) );
		contract4.setPaid(false);
		contract4.setId(counter++);
		contract4.setState(ContractState.BOOKED);
		contract4.setType(ContractType.TIME);
		contracts.put(contract4.getId(), contract4);

	}

	@Override
	public List<Contract> getAllContracts() throws BusinessException {
		return new ArrayList<>(contracts.values());
	}

	@Override
	public Contract getContractByID(int id) {
		return contracts.get(id);
	}

	@Override
	public List<Contract> getContractsByUser(User user) throws BusinessException {
		List<Contract> result = new ArrayList<>();
		for (Contract c : contracts.values())
			if (c.getCustomer().getId() == user.getId())
				result.add(c);
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
	public List<Contract> getContractsByVeicle(Integer idVeicle) {
		List<Contract> result = new ArrayList<>();
		for (Contract c : contracts.values())
			if (c.getVeicle().getId() == idVeicle)
				result.add(c);
		return result;
	}
	
	@Override
	public void setContract(Contract contract) {
		contracts.put(contract.getId(), contract);
	}	
}
