package it.univaq.disim.oop.bhertz.business.impl.ram;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;

public class RAMBusinessFactoryImpl extends BhertzBusinessFactory {

	private UserService userService;
	private ContractService contractService;
	private TypesService typeService;
	private VeiclesService veicleService;
	private MaintenanceService maintenanceService;
	
	public RAMBusinessFactoryImpl() {
		userService = new RAMUserServiceImpl();
		try {
			contractService = new RAMContractServiceImpl();
		} catch (BusinessException e2) {
			e2.printStackTrace();
		}
		typeService = new RAMTypesServiceImpl();
		try {
			veicleService = new RAMVeicleserviceImpl();
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
		try {
			maintenanceService = new RAMMaintenanceServiceImpl();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ContractService getContractService() {
		return contractService;
	}

	@Override
	public MaintenanceService getMaintenanceService() {
		return maintenanceService;
	}

	@Override
	public TypesService getTypesService() {
		return typeService;
	}

	@Override
	public UserService getUserService() {
		return userService;
	}

	@Override
	public VeiclesService getVeiclesService() {
		return veicleService;
	}

}
