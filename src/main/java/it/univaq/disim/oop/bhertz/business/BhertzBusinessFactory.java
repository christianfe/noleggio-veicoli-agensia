package it.univaq.disim.oop.bhertz.business;

import it.univaq.disim.oop.bhertz.business.impl.file.FileBusinessFactoryImpl;

public abstract class BhertzBusinessFactory {
	
	//private static BhertzBusinessFactory factory = new RAMBusinessFactoryImpl();
	private static BhertzBusinessFactory factory = new FileBusinessFactoryImpl();
	
	public static BhertzBusinessFactory getInstance() {
		return factory;
	}

	public abstract ContractService getContractService();
	public abstract MaintenanceService getMaintenanceService();
	public abstract TypesService getTypesService();
	public abstract UserService getUserService();
	public abstract VeiclesService getVeiclesService();
	public abstract FeedbackService getFeedbackService();
	public abstract NotificationsService getNotificationsService();

	
}
