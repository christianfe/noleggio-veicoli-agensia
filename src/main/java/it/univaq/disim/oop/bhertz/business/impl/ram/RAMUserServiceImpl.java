package it.univaq.disim.oop.bhertz.business.impl.ram;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.UserNotFoundException;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.domain.Admin;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.domain.Staff;
import it.univaq.disim.oop.bhertz.domain.User;

public class RAMUserServiceImpl implements UserService {

	/*
	 * private CorsoDiLaureaService corsoDiLaureaService;
	 * 
	 * public RAMUtenteServiceImpl(CorsoDiLaureaService corsoDiLaureaService) {
	 * this.corsoDiLaureaService = corsoDiLaureaService; }
	 * 
	 */

	@Override
	public User authenticate(String username, String password) throws BusinessException {
		if ("admin".equalsIgnoreCase(username)) {
			Admin user = new Admin();
			user.setUsername(username);
			user.setPassword(password);
			user.setName("Admin");
			return user;
		} else if ("staff".equalsIgnoreCase(username)) {
			Staff user = new Staff();
			user.setUsername(username);
			user.setPassword(password);
			user.setName("Operatore");
			return user;
		} else if ("customer".equalsIgnoreCase(username)) {
			Customer user = new Customer();
			user.setUsername(username);
			user.setPassword(password);
			user.setName("Cliente");
			return user;
		}
		throw new UserNotFoundException();
	}

}
