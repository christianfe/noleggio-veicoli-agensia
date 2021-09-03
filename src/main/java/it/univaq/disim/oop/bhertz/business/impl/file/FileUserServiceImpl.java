package it.univaq.disim.oop.bhertz.business.impl.file;

import java.io.IOException;
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

public class FileUserServiceImpl implements UserService {

	private String userFilename;
	private Map<Integer, User> users = new HashMap<>();

	public FileUserServiceImpl(String userFilename) {
		this.userFilename = userFilename;
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
					case "admin":
						user = new Admin();
						break;
					case "staff":
						user = new Staff();
						break;
					case "user":
						user = new Customer();
						break;
					default:
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
	public User getusersByID(int id) throws BusinessException {
		return null;
	}

	@Override
	public List<User> getUserByRole(int r) throws BusinessException {
		return null;
	}

	@Override
	public void setUser(User user) throws BusinessException {
	}

	@Override
	public void addUser(User user) throws BusinessException {
	}

}
