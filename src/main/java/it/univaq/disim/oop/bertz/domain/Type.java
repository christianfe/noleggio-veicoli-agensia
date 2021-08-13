package it.univaq.disim.oop.bertz.domain;

import java.util.HashSet;
import java.util.Set;

public class Type {

	private String name;
	private double priceForKm;
	private double priceForDay;
	private Integer id;

	private Set<Veicle> veicles = new HashSet<Veicle>();

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
