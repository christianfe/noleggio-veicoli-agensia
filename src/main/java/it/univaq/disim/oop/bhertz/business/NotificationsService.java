package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Notification;

public interface NotificationsService {

	List<Notification> getAllNotifications() throws BusinessException;
	
	Notification getNotificationByID(int id) throws BusinessException;
	
	List<Notification> getNotificationByUser(Integer id) throws BusinessException;
	
	void addNotification (Notification notification) throws BusinessException;
}
	