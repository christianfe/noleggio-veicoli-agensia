package it.univaq.disim.oop.bhertz.domain;

public class Type {

	private String name;
	private double priceForKm;
	private double priceForDay;
	private Integer id;

	public Type() {
	}

	public Type(Integer id, String name, double priceForKm, double priceForDay) {
		this.setId(id);
		this.setName(name);
		this.setPriceForDay(priceForDay);
		this.setPriceForKm(priceForKm);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPriceForKm() {
		return priceForKm;
	}

	public void setPriceForKm(double priceForKm) {
		this.priceForKm = priceForKm;
	}

	public double getPriceForDay() {
		return priceForDay;
	}

	public void setPriceForDay(double priceForDay) {
		this.priceForDay = priceForDay;
	}

}
