package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.User;

public interface UserService {

	User authenticate(String username, String password) throws UserNotFoundException, BusinessException;
	
	User getUsersByID(int id) throws BusinessException;

	List<User> getUserByRole(int r) throws BusinessException;

	void setUser(Integer id, String name, String username, String password) throws BusinessException;
	
	void addUser(User user) throws BusinessException;
	
	void deleteUser(Integer id) throws BusinessException;
	
	boolean isUsernameSet(String username) throws BusinessException;
	
	boolean isUsernameSet(Integer currentUserId, String username) throws BusinessException;
	
}
	