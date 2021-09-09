package it.univaq.disim.oop.bhertz.domain;

public class Veicle {

	private String model;
	private String plate;
	private VeicleState state;
	private double km;
	private double consumption;
	private Integer id;
	private String fuel;
	private double priceForKm;
	private double priceForDay;

	private Type type;

	public Veicle() {
	}

	public Veicle(Integer id, Type type, String model, String plate, double km, double consuption, String fuel) {
		this.setState(VeicleState.FREE);
		this.setId(id);
		this.setType(type);
		this.setModel(model);
		this.setPlate(plate);
		this.setKm(km);
		this.setConsumption(consuption);
		this.setFuel(fuel);
		this.setPriceForKm(type.getPriceForKm());
		this.setPriceForDay(type.getPriceForDay());

	}
	
	@Override
    public String toString() {
		return this.getModel() + " - " + this.getPlate();
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

	public double getKm() {
		return km;
	}

	public void setKm(double km) {
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

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
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
	
	public void getStringState () {
		String S;
		
		switch (state) {
		case FREE:
			S = "libero";
			break;
		case BUSY:
			S = "Occupato";
		case MAINTENANCE:
			S = "manutenzione";
		
		System.out.println(S);
		
		}
		
	}

}
