package it.univaq.disim.oop.bhertz.domain;

public class Notification {
	private String title;
	private String text;
	private Integer id;
	private Customer customer;

	public Notification() {
	}

	public Notification(Customer customer, String title, String text) {
		this.setCustomer(customer);
		this.setTitle(title);
		this.setText(text);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
