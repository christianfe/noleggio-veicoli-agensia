package it.univaq.disim.oop.bhertz.business.impl.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.NotificationsService;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.domain.Notification;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;

public class FileNotificationServiceImpl implements NotificationsService {

	private Map<Integer, Notification> notifications = new HashMap<>();
	private int counter = 1;
	private String filename;

	public FileNotificationServiceImpl(String notificationFileName) {
		this.filename = notificationFileName;
		try {
			this.readList();
		} catch (BusinessException e) {
			ViewDispatcher.getInstance().renderError(e);
		} catch (NullPointerException e) {
			
		}
	}

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
		notification.setId(counter++);
		this.notifications.put(notification.getId(), notification);
		this.saveList();
	}

	private void saveList() throws BusinessException {
		FileUtility fileUtility = new FileUtility();
		List<String[]> list = new ArrayList<>();
		for (Notification n : notifications.values()) {
			String[] s = new String[4];
			s[0]= n.getId().toString();
			s[1]= n.getTitle();
			s[2]= n.getText();
			s[3]= n.getCustomer().getId().toString();
			list.add(s);
		}
		fileUtility.setAllByFile(this.filename, new FileData(this.counter, list));
	}

	private void readList() throws BusinessException {
		FileUtility fileUtility = new FileUtility();
		FileData fileData = fileUtility.getAllByFile(filename);
		UserService userService = BhertzBusinessFactory.getInstance().getUserService();
		this.notifications = new HashMap<Integer, Notification>();
		for (String[] row : fileData.getRows()) {
			Notification notification = new Notification();
			notification.setId(Integer.parseInt(row[0]));
			notification.setTitle(row[1]);
			notification.setText(row[2]);
			notification.setCustomer((Customer) userService.getUsersByID(Integer.parseInt(row[3])));
			this.counter = (int) fileData.getCount();
			this.notifications.put(notification.getId(), notification);
		}
	}
}
