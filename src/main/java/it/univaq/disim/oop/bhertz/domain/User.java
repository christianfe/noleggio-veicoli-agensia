package it.univaq.disim.oop.bhertz.domain;

public abstract class User {

	private Integer id;
	private String username;
	private String password;
	private String name;

	public User() {}
	
	public User(int id, String name, String username, String pass) {
		this.setId(id);
		this.setName(name);
		this.setUsername(username);
		this.setPassword(pass);
	}

	public abstract int getRole();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
