package it.univaq.disim.oop.bhertz.domain;

import java.util.HashSet;
import java.util.Set;

public class Type {

	private String name;
	private double priceForKm;
	private double priceForDay;
	private Integer id;

	private Set<Veicle> veicles = new HashSet<Veicle>();

	public Type() {}
	
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

	public Set<Veicle> getVeicles() {
		return veicles;
	}

	public void setVeicles(Set<Veicle> veicles) {
		this.veicles = veicles;
	}
}
