package it.univaq.disim.oop.bhertz.domain;

public class Staff extends User {
	public Staff(int id, String name, String username, String pass) {
		super(id, name, username, pass);
	}

	public int getRole() {
		return 1;
	}
}
