package it.univaq.disim.oop.bertz.domain;

public class Veicle {
	
	private String model;
	private String plate;
	private VeicleState state;
	private int km;
	private double consumption;
	private Integer id;
	

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public VeicleState getState() {
		return state;
	}
	public void setState(VeicleState state) {
		this.state = state;
	}
	public int getKm() {
		return km;
	}
	public void setKm(int km) {
		this.km = km;
	}
	public double getConsumption() {
		return consumption;
	}
	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	
	
}
