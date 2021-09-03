package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.UserNotFoundException;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.domain.Admin;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.domain.Staff;
import it.univaq.disim.oop.bhertz.domain.User;

public class RAMUserServiceImpl implements UserService {

	private Map<Integer, User> users = new HashMap<>();

	public RAMUserServiceImpl() {
		User admin = new Admin(1,"Administrator", "admin", "admin");
		users.put(admin.getId(), admin);
		User staff = new Staff(2,"Operatore", "staff", "staff");
		users.put(staff.getId(), staff);
		User mrossi = new Staff(4,"Rossi Mario", "mrossi", "mrossi");
		users.put(mrossi.getId(), mrossi);
		User user = new Customer(3, "Cliente", "user", "user");
		users.put(user.getId(), user);
		User user1 = new Customer(5, "Cliente1", "user1", "user1");
		users.put(user1.getId(), user1);
		User user2 = new Customer(6, "Cliente2", "user2", "user2");
		users.put(user2.getId(), user2);
	}

	@Override
	public User authenticate(String username, String password) throws BusinessException {

		for (User u : users.values())
			if (u.getUsername().equalsIgnoreCase(username) /*&& u.getPassword().equalsIgnoreCase(password)*/)
				return u;
		throw new UserNotFoundException();

	}

	@Override
	public User getusersByID(int id) throws BusinessException {

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
	public void setUser(Integer id, String name, String username, String password){
		User u = users.get(id);
		u.setName(name);
		u.setUsername(username);
		u.setPassword(password);
		this.users.put(id, u);
	}

	@Override
	public void addUser(User user){
		Integer max = 0;
		for (User u : users.values())
			max = (max > u.getId())? max : u.getId();
		user.setId(max + 1);
		this.users.put(user.getId(), user);
	}

	@Override
	public boolean isUsernameSet(String username){
		for (User u : users.values())
			if (u.getUsername().equals(username))
				return true;
		return false;
	}

	@Override
	public void deleteUser(Integer id) {
		users.remove(id);
	}

	@Override
	public boolean isUsernameSet(Integer currentUserId, String username) {
		for (User u : users.values()) {
			if (u.getId() == currentUserId) continue;
			if (u.getUsername().equals(username)) return true;
		}
		return false;
	}
}
