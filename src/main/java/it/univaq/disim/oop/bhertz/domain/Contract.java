package it.univaq.disim.oop.bhertz.domain;

import java.time.LocalDate;

public class Contract {

	private LocalDate start;
	private LocalDate end;
	private double startKm;
	private double endKm;
	private ContractType type;
	private ContractState state;
	private Integer id;
	private boolean paid;
	private Veicle veicle;

	private Feedback feedback;
	private AssistanceTicket assistance;
	private Customer customer;
	
	public Contract() {
		this.setState(ContractState.ACTIVE);
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
		String stateString = null;
		
		if (state == ContractState.ACTIVE )
			stateString = "Attivo";
		else if (state == ContractState.ENDED )
			stateString = "Chiuso";
		else if (state == ContractState.MAINTENANCE )
			stateString = "Manutenzione";
		else if (state == ContractState.BOOKED )
			stateString = "Prenotato";
		
		
		return stateString;
	}
	
	public ContractType getContractType() {
		return this.type;
	}
	
	public void setState(ContractState state) {
		this.state = state;
	}
	
	public ContractState getState() {
		return state;
		
	}

}
