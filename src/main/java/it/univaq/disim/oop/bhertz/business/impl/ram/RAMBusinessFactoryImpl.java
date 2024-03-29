package it.univaq.disim.oop.bhertz.business.impl.ram;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.NotificationsService;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;

public class RAMBusinessFactoryImpl extends BhertzBusinessFactory {

	private UserService userService;
	private ContractService contractService;
	private TypesService typeService;
	private VeiclesService veicleService;
	private MaintenanceService maintenanceService;
	private FeedbackService feedbackService;
	private NotificationsService notificationService;

	public RAMBusinessFactoryImpl() {
		veicleService = new RAMVeicleServiceImpl();
		maintenanceService = new RAMMaintenanceServiceImpl();
		feedbackService = new RAMFeedbackServiceImpl();
		typeService = new RAMTypesServiceImpl();
		contractService = new RAMContractServiceImpl();
		userService = new RAMUserServiceImpl();
		notificationService = new RAMNotificationServiceImpl();
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
		return feedbackService;
	}

	@Override
	public NotificationsService getNotificationsService() {
		return notificationService;
	}

}
