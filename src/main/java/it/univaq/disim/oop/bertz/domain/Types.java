package it.univaq.disim.oop.bertz.domain;

public class Types {

	private String name;
	private License licenseRequired;
	private double priceForKm;
	private double priceForDay;
	private Integer id;

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

	public License getLicenseRequired() {
		return licenseRequired;
	}

	public void setLicenseRequired(License licenseRequired) {
		this.licenseRequired = licenseRequired;
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