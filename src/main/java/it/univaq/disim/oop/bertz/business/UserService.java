package it.univaq.disim.oop.bertz.business;

import it.univaq.disim.oop.bertz.domain.User;

public interface UserService {
	User authenticate(String username, String password) throws UserNotFoundException, BusinessException;
}
