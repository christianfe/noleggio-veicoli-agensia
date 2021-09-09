package it.univaq.disim.oop.bhertz.business.impl.file;

import java.io.File;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.NotificationsService;
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
	private NotificationsService notificationService;
	
	private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "data";
	private static final String USER_FILE_NAME = REPOSITORY_BASE + File.separator + "Users.txt";
	private static final String NOTIFICATION_FILE_NAME = REPOSITORY_BASE + File.separator + "Notifications.txt";
	private static final String TYPES_FILE_NAME = REPOSITORY_BASE + File.separator + "Types.txt";
	private static final String VEICLE_FILE_NAME = REPOSITORY_BASE + File.separator + "Veicles.txt";
	private static final String MAINTENANCE_FILE_NAME = REPOSITORY_BASE + File.separator + "Tickets.txt";
	private static final String CONTRACTS_FILE_NAME = REPOSITORY_BASE + File.separator + "Contracts.txt";
	private static final String FEEDBACK_FILE_NAME = REPOSITORY_BASE + File.separator + "Feedbacks.txt";
	
	public FileBusinessFactoryImpl() {
		this.userService = new FileUserServiceImpl(USER_FILE_NAME);
		this.typeService = new FileTypesServiceImpl(TYPES_FILE_NAME);
		this.veicleService = new FileVeicleserviceImpl(VEICLE_FILE_NAME);
		this.notificationService = new FileNotificationServiceImpl(NOTIFICATION_FILE_NAME);
		this.contractService = new FileContractServiceImpl(CONTRACTS_FILE_NAME);
		this.maintenanceService = new FileMaintenanceServiceImpl(MAINTENANCE_FILE_NAME);
		this.feedbackService = new FileFeedbackServiceImpl(FEEDBACK_FILE_NAME);
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
