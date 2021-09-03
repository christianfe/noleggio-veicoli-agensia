package it.univaq.disim.oop.bhertz.business.impl.file;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;

public class FileBusinessFactoryImpl extends BhertzBusinessFactory {
	private UserService userService;
	private ContractService contractService;
	private TypesService typeService;
	private VeiclesService veicleService;
	private MaintenanceService maintenanceService;
	
	public FileBusinessFactoryImpl() {
		userService = new FileUserServiceImpl();
		contractService = new FileContractServiceImpl();
		typeService = new FileTypesServiceImpl();
		veicleService = new FileVeicleserviceImpl();
		maintenanceService = new FileMaintenanceServiceImpl();
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
