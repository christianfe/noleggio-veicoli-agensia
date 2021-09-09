package it.univaq.disim.oop.bhertz.business.impl.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.business.UserNotFoundException;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.domain.Admin;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Staff;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;

public class FileUserServiceImpl implements UserService {

	private String userFilename;
	private Map<Integer, User> users = new HashMap<>();
	private long counter;

	public FileUserServiceImpl(String userFilename) {
		this.userFilename = userFilename;
		try {
			this.readList();
		} catch (BusinessException e) {
			ViewDispatcher.getInstance().renderError(e);
		}
	}

	@Override
	public User authenticate(String username, String password) throws UserNotFoundException, BusinessException {
		//ID, role, nome, username; password
		try {
			FileData fileData = FileUtility.readAllRows(userFilename);
			for (String[] cols : fileData.getRows()) {
				if (!(cols[3].equals(username) && cols[4].equals(password)))
					continue;
				User user = null;
				switch (cols[1]) {
					case "0":
						user = new Admin();
						break;
					case "1":
						user = new Staff();
						break;
					case "2":
						user = new Customer();
						break;
				}
				if (user != null) {
					user.setId(Integer.parseInt(cols[0]));
					user.setUsername(username);
					user.setPassword(password);
					user.setName(cols[2]);
				} else
					throw new BusinessException("errore nella lettura del file");
				return user;
			}
			throw new UserNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public User getUsersByID(int id) throws BusinessException {
		return users.get(id);
	}

	@Override
	public List<User> getUserByRole(int r) throws BusinessException {
		List<User> result = new ArrayList<>();
		for (User u : users.values())
			if (u.getRole() == r)
				result.add(u);
		return result;	}

	@Override
	public void setUser(Integer id, String name, String username, String password) throws BusinessException {
		User u = this.getUsersByID(id);
		u.setName(name);
		u.setUsername(username);
		u.setPassword(password);
		this.users.put(id, u);
		this.saveList();
	}

	@Override
	public void addUser(User user) throws BusinessException {
		user.setId((int) counter++);
		this.users.put(user.getId(), user);
		this.saveList();
	}

	@Override
	public void deleteUser(Integer id) throws BusinessException {
		ContractService contractService = BhertzBusinessFactory.getInstance().getContractService();
		UserService userService = BhertzBusinessFactory.getInstance().getUserService();
		FeedbackService feedbackService = BhertzBusinessFactory.getInstance().getFeedbackService();
		List <Contract> cc = contractService.getContractsByUser(2, userService.getUsersByID(id));
		List <Feedback> ff = feedbackService.getFeedbackByUser(id);
		for (Feedback f : ff)
			feedbackService.removeFeedback(f.getId());
		for (Contract c : cc)
			contractService.removeContract(c.getId());
		users.remove(id);
		this.saveList();
	}

	@Override
	public boolean isUsernameSet(String username) throws BusinessException {
		for (User u : users.values())
			if (u.getUsername().equals(username))
				return true;
		return false;
	}

	@Override
	public boolean isUsernameSet(Integer currentUserId, String username) throws BusinessException {
		for (User u : users.values()) {
			if (u.getId() == currentUserId) continue;
			if (u.getUsername().equals(username)) return true;
		}
		return false;
	}

	private void saveList() throws BusinessException {
		FileUtility f = new FileUtility();
		List<String[]> list = new ArrayList<>();
		for (User u : users.values()) {
			String[] s = new String[5];
			s[0]= u.getId().toString();
			s[1]= u.getRole() + "";
			s[2]= u.getName();
			s[3]= u.getUsername();
			s[4]= u.getPassword();
			list.add(s);
		}
		f.setAllByFile(this.userFilename, new FileData(this.counter, list));
	}
	
	private void readList() throws BusinessException {
		FileUtility f = new FileUtility();
		FileData fileData = f.getAllByFile(userFilename);
		this.users = new HashMap<Integer, User>();
		for (String[] row : fileData.getRows()) {
			User user = null;
			switch (row[1]) {
				case "0":
					user = new Admin();
					break;
				case "1":
					user = new Staff();
					break;
				case "2":
					user = new Customer();
					break;
			}
			if (user != null) {
				user.setId(Integer.parseInt(row[0]));
				user.setUsername(row[3]);
				user.setPassword(row[4]);
				user.setName(row[2]);
			} else
				throw new BusinessException("Errore nella lettura del file");
			this.counter = fileData.getCount();
			this.users.put(user.getId(), user);
		}
	}
	
}
