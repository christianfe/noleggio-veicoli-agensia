package it.univaq.disim.oop.bertz.domain;

public class Customer extends User {
	
	private License license;

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}
	

}
