package it.univaq.disim.oop.bhertz.business.impl.file;

import java.io.File;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
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
	private FeedbackService feedbackService;
	
	private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "data";
	private static final String USER_FILE_NAME = REPOSITORY_BASE + File.separator + "users.txt";
	private static final String TYPES_FILE_NAME = REPOSITORY_BASE + File.separator + "types.txt";
	private static final String VEICLE_FILE_NAME = REPOSITORY_BASE + File.separator + "veicles.txt";
	private static final String MAINTENANCE_FILE_NAME = REPOSITORY_BASE + File.separator + "maintenances.txt";
	private static final String CONTRACTS_FILE_NAME = REPOSITORY_BASE + File.separator + "contracts.txt";
	
	public FileBusinessFactoryImpl() {
		userService = new FileUserServiceImpl(USER_FILE_NAME);
		contractService = new FileContractServiceImpl();
		typeService = new FileTypesServiceImpl();
		veicleService = new FileVeicleserviceImpl();
		maintenanceService = new FileMaintenanceServiceImpl();
		feedbackService = new FileFeedbackServiceImpl();
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

	@Override
	public FeedbackService getFeedbackService() {
		// TODO Auto-generated method stub
		return null;
	}

}
