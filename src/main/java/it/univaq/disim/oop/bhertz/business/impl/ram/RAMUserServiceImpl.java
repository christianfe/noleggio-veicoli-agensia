package it.univaq.disim.oop.bhertz.business.impl.ram;

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

public class RAMUserServiceImpl implements UserService {

	private Map<Integer, User> users = new HashMap<>();
	private int counter = 1; 

	public RAMUserServiceImpl() {
		User admin = new Admin(counter++,"Administrator", "admin", "admin");
		users.put(admin.getId(), admin);
		User staff = new Staff(counter++,"Operatore", "staff", "staff");
		users.put(staff.getId(), staff);
		User mrossi = new Staff(counter++,"Rossi Mario", "mrossi", "mrossi");
		users.put(mrossi.getId(), mrossi);
		User user = new Customer(counter++, "Cliente", "user", "user");
		users.put(user.getId(), user);
		User user1 = new Customer(counter++, "Cliente1", "user1", "user1");
		users.put(user1.getId(), user1);
		User user2 = new Customer(counter++, "Cliente2", "user2", "user2");
		users.put(user2.getId(), user2);
		User user3 = new Customer(counter++, "Cliente3", "user3", "user3");
		users.put(user3.getId(), user2);
	}

	@Override
	public User authenticate(String username, String password) throws UserNotFoundException, BusinessException{
		for (User u : users.values())
			if (u.getUsername().equalsIgnoreCase(username) /*&& u.getPassword().equalsIgnoreCase(password)*/)
				return u;
		throw new UserNotFoundException();
	}

	@Override
	public User getUsersByID(int id) {
		return users.get(id);
	}

	@Override
	public List<User> getUserByRole(int r) throws BusinessException {
		List<User> result = new ArrayList<>();
		for (User u : users.values())
			if (u.getRole() == r)
				result.add(u);
		return result;
	}

	@Override
	public void setUser(Integer id, String name, String username, String password) throws BusinessException{
		User u = this.getUsersByID(id);
		u.setName(name);
		u.setUsername(username);
		u.setPassword(password);
		this.users.put(id, u);
	}

	@Override
	public void addUser(User user) throws BusinessException{
		user.setId(counter++);
		this.users.put(user.getId(), user);
	}

	@Override
	public boolean isUsernameSet(String username) throws BusinessException{
		for (User u : users.values())
			if (u.getUsername().equals(username))
				return true;
		return false;
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
	}

	@Override
	public boolean isUsernameSet(Integer currentUserId, String username) throws BusinessException {
		for (User u : users.values()) {
			if (u.getId() == currentUserId) continue;
			if (u.getUsername().equals(username)) return true;
		}
		return false;
	}
}
