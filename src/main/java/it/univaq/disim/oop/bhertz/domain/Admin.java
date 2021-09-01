package it.univaq.disim.oop.bhertz.domain;

public class Admin extends User {
	public Admin(int id, String name, String username, String pass) {
		super(id, name, username, pass);
	}

	public int getRole() {
		return 0;
	}
}
