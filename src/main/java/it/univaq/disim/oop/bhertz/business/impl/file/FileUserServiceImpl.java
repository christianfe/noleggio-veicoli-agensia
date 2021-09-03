package it.univaq.disim.oop.bhertz.business.impl.file;

import java.util.List;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.UserNotFoundException;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.domain.User;

public class FileUserServiceImpl implements UserService {

	@Override
	public User authenticate(String username, String password) throws UserNotFoundException, BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getusersByID(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUserByRole(int r) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUser(User user) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUser(User user) throws BusinessException {
		// TODO Auto-generated method stub

	}

}
