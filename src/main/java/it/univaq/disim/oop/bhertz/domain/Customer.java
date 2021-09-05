package it.univaq.disim.oop.bhertz.domain;

public class Customer extends User {
	
	
	public Customer(int id, String name, String username, String pass) {
		super(id, name, username, pass);
	}

	public Customer() {}

	public int getRole() {
		return 2;
	}
			
}
