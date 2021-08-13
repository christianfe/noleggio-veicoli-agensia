package it.univaq.disim.oop.bertz.domain;

import java.time.LocalDate;

public class Contract {

	private LocalDate start;
	private LocalDate end;
	private int startKm;
	private int endKm;
	private ContractType type;
	private Integer id;
	private boolean paid;

	private Feedback feedback;
	private AssistanceTicket assistance;
	private Customer customer;
	private Veicle veicle;

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

	public int getStartKm() {
		return startKm;
	}

	public void setStartKm(int startKm) {
		this.startKm = startKm;
	}

	public int getEndKm() {
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

}
