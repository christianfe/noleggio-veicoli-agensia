package it.univaq.disim.oop.bhertz.business;

import it.univaq.disim.oop.bhertz.domain.User;

public interface UserService {
	User authenticate(String username, String password) throws UserNotFoundException, BusinessException;
}
