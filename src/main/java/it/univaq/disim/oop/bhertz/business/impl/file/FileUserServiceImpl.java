package it.univaq.disim.oop.bhertz.business.impl.file;

import java.io.IOException;
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
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;

public class FileUserServiceImpl implements UserService {

	private String userFilename;
	private Map<Integer, User> users = new HashMap<>();

	public FileUserServiceImpl(String userFilename) {
		this.userFilename = userFilename;
		try {
			this.readList();
		} catch (BusinessException e) {
			ViewDispatcher.getInstance().renderError(e);
		}
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
					case "0":
						user = new Admin();
						break;
					case "1":
						user = new Staff();
						break;
					case "2":
						user = new Customer();
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
	public User getUsersByID(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUserByRole(int r) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUser(Integer id, String name, String username, String password) throws BusinessException {
		//TODO
		this.saveList();
	}

	@Override
	public void addUser(User user) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(Integer id) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsernameSet(String username) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUsernameSet(Integer currentUserId, String username) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	private void saveList() throws BusinessException {
		FileUtility f = new FileUtility();
		List<String[]> list = new ArrayList<>() ;
		for (User u : users.values()) {
			String[] s = new String[5];
			s[0]= u.getId().toString();
			s[1]= u.getRole() + "";
			s[2]= u.getName();
			s[3]= u.getUsername();
			s[4]= u.getPassword();
			list.add(s);
		}
		f.setAllByFile(this.userFilename, list);
	}
	
	private void readList() throws BusinessException {
		FileUtility f = new FileUtility();
		List<String[]> list = f.getAllByFile(userFilename);
		this.users = new HashMap<Integer, User>();
		for (String[] row : list) {
			User user = null;
			switch (row[1]) {
				case "0":
					user = new Admin();
					break;
				case "1":
					user = new Staff();
					break;
				case "2":
					user = new Customer();
					break;
			}
			if (user != null) {
				user.setId(Integer.parseInt(row[0]));
				user.setUsername(row[3]);
				user.setPassword(row[4]);
				user.setName(row[2]);
			} else
				throw new BusinessException("Errore nella lettura del file");
			this.users.put(user.getId(), user);
		}
	}
	
}
