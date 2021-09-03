package it.univaq.disim.oop.bhertz.domain;

import java.util.HashSet;
import java.util.Set;

public class Veicle {

	private String model;
	private String plate;
	private VeicleState state;
	private int km;
	private double consumption;
	private Integer id;

	private Type type;
	private Set<Contract> contracts = new HashSet<Contract>();

	public Veicle() {}

	public Veicle(Integer id, Type type, String model, String plate, int km, double consuption) {
		this.state = VeicleState.FREE;
		this.setId(id);;
		this.setType(type);
		this.setModel(model);
		this.setPlate(plate);
		this.setKm(km);
		this.setConsumption(consuption);
	}

	
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
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Set<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}
}
