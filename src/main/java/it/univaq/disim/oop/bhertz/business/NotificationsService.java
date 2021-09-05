package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Notification;

public interface NotificationsService {

	List<Notification> getAllNotifications();
	
	Notification getNotificationByID(int id);
	
	List<Notification> getNotificationByUser(Integer id);
	
	void addNotification (Notification notification);	
}
	