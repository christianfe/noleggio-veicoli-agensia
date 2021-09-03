package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.User;

public interface UserService {

	User authenticate(String username, String password) throws UserNotFoundException, BusinessException;
	
	User getusersByID(int id) throws BusinessException;

	List<User> getUserByRole(int r) throws BusinessException;

	void setUser(Integer id, String name, String username, String password);
	
	void addUser(User user);
	
	void deleteUser(Integer id);
	
	boolean isUsernameSet(String username);
	
	boolean isUsernameSet(Integer currentUserId, String username);
	
}
	