package it.univaq.disim.oop.bhertz.domain;

import java.util.HashSet;
import java.util.Set;

public class Customer extends User {
	
	private Set<Notification> notifications = new HashSet<Notification>();
	
	public Customer(int id, String name, String username, String pass) {
		super(id, name, username, pass);
	}

	public Customer() {}

	public int getRole() {
		return 2;
	}

	public Notification getNotificationByID(Integer id) throws NotificationNotFoundExeption {
		for(Notification n : notifications)
			if (n.getId() == id)
				return n;
		throw new NotificationNotFoundExeption();
	}

	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}
		
}
