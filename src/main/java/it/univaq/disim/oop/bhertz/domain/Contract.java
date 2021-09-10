package it.univaq.disim.oop.bhertz.domain;

import java.time.LocalDate;

public class Contract {

	private LocalDate start;
	private LocalDate end;
	private double startKm;
	private double endKm;
	private double price;
	private ContractType type;
	private ContractState state;
	private Integer id;
	private boolean paid;
	private String returnDateTime = null;
	private String deliverDateTime = null;
	private boolean sostistuteContract;
	
	private Veicle veicle = null;
	private Feedback feedback = null;
	private AssistanceTicket assistance = null;
	private Customer customer = null;

	public Contract() {
		this.setState(ContractState.BOOKED);
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public AssistanceTicket getAssistance() {
		return assistance;
	}

	public void setAssistance(AssistanceTicket assistance) {
		this.assistance = assistance;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getStart() {
		return start;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public double getStartKm() {
		return startKm;
	}

	public void setStartKm(double d) {
		this.startKm = d;
	}

	public double getEndKm() {
		return endKm;
	}

	public void setEndKm(int endKm) {
		this.endKm = endKm;
	}

	public ContractType getType() {
		return type;
	}

	public void setType(ContractType type) {
		this.type = type;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Veicle getVeicle() {
		return veicle;
	}

	public void setVeicle(Veicle veicle) {
		this.veicle = veicle;
	}

	public String getStateString() {
		if (state == ContractState.ACTIVE)
			return "Attivo";
		else if (state == ContractState.ENDED)
			return "Chiuso";
		else if (state == ContractState.MAINTENANCE)
			return "Manutenzione";
		else if (state == ContractState.BOOKED)
			return "Prenotato";

		return null;
	}

	public void setState(ContractState state) {
		this.state = state;
	}

	public ContractState getState() {
		return state;

	}

	public String getReturnDateTime() {
		return returnDateTime;
	}

	public void setReturnDateTime(String returnDateTime) {
		this.returnDateTime = returnDateTime;
	}

	public void setEndKm(double endKm) {
		this.endKm = endKm;
	}

	public String getDeliverDateTime() {
		return deliverDateTime;
	}

	public void setDeliverDateTime(String deliverDateTime) {
		this.deliverDateTime = deliverDateTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isSostistuteContract() {
		return sostistuteContract;
	}

	public void setSostistuteContract(boolean sostistuteContract) {
		this.sostistuteContract = sostistuteContract;
	}
	
	

}
