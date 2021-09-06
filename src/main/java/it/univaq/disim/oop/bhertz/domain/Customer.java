package it.univaq.disim.oop.bhertz.domain;

public class Customer extends User {

	public Customer() {
	}

	public Customer(int id, String name, String username, String pass) {
		super(id, name, username, pass);
	}

	public int getRole() {
		return 2;
	}

}
