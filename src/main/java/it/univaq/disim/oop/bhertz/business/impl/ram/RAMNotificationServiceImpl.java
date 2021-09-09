package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.NotificationsService;
import it.univaq.disim.oop.bhertz.domain.Notification;

public class RAMNotificationServiceImpl implements NotificationsService {

	private Map<Integer, Notification> notifications = new HashMap<>();
	
	@Override
	public List<Notification> getAllNotifications() throws BusinessException {
		return new ArrayList<>(notifications.values());
	}

	@Override
	public Notification getNotificationByID(int id) throws BusinessException {
		return notifications.get(id);
	}

	@Override
	public List<Notification> getNotificationByUser(Integer id) throws BusinessException {
		List<Notification> result = new ArrayList<>();
		for (Notification n : notifications.values())
			if (n.getCustomer().getId() == id)
				result.add(n);
		return result;
	}

	@Override
	public void addNotification(Notification notification) throws BusinessException {
		Integer max = 0;
		for (Notification n : notifications.values())
			max = (max > n.getId())? max : n.getId();
		notification.setId(max + 1);
		this.notifications.put(notification.getId(), notification);
	}

}
