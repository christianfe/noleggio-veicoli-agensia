package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.RentalService;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractType;
import it.univaq.disim.oop.bhertz.domain.Customer;

public class RAMRentalServiceImpl implements RentalService  {

	private Map<Integer, Contract> contracts = new HashMap<>();
	private VeiclesService veicleService;
	private UserService userService;


	public RAMRentalServiceImpl() throws BusinessException {
		this.veicleService = new RAMVeicleserviceImpl();
		this.userService = new RAMUserServiceImpl();

		Contract contract1 = new Contract();
		contract1.setCustomer(userService.getusersByID(2));
		//contract1.setCustomer(new Customer(1, "gigio", "cacca", "pupù"));
		contract1.setVeicle(veicleService.getVeicleByID(1));
		contract1.setStart( LocalDate.of(2014, Month.SEPTEMBER, 10) );
		contract1.setEnd(LocalDate.of(2014, Month.SEPTEMBER, 20) );
		contract1.setPaid(false);
		contract1.setId(1);
		contracts.put(contract1.getId(), contract1);

		Contract contract2 = new Contract();
		contract2.setCustomer(userService.getusersByID(3));
		//contract1.setCustomer(new Customer(1, "gigio", "cacca", "pupù"));
		contract2.setVeicle(veicleService.getVeicleByID(2));
		contract2.setStart( LocalDate.of(2014, Month.SEPTEMBER, 10) );
		contract2.setEnd(LocalDate.of(2014, Month.SEPTEMBER, 20) );
		contract2.setPaid(true);
		contract2.setId(2);
		contracts.put(contract2.getId(), contract2);

		Contract contract3 = new Contract();
		contract3.setCustomer(userService.getusersByID(3));
		//contract1.setCustomer(new Customer(1, "gigio", "cacca", "pupù"));
		contract3.setVeicle(veicleService.getVeicleByID(4));
		contract3.setStart( LocalDate.of(2014, Month.SEPTEMBER, 12) );
		contract3.setEnd(LocalDate.of(2014, Month.SEPTEMBER, 13) );
		contract3.setPaid(true);
		contract3.setId(3);
		contracts.put(contract3.getId(), contract3);

	}

	@Override
	public List<Contract> getAllContracts() throws BusinessException {
		return new ArrayList<>(contracts.values());
	}

	@Override
	public Contract getContractByID(int id) throws BusinessException {
		return contracts.get(id);
	}
	
	//TODO public List<Contract> getContractByClient(User user){}

}
