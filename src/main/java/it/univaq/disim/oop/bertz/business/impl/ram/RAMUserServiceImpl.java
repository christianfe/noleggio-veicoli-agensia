package it.univaq.disim.oop.bertz.business.impl.ram;

import it.univaq.disim.oop.bertz.business.BusinessException;
import it.univaq.disim.oop.bertz.business.UserNotFoundException;
import it.univaq.disim.oop.bertz.business.UserService;
import it.univaq.disim.oop.bertz.domain.User;
import it.univaq.disim.oop.bertz.domain.Admin;
import it.univaq.disim.oop.bertz.domain.Customer;
import it.univaq.disim.oop.bertz.domain.Staff;

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
