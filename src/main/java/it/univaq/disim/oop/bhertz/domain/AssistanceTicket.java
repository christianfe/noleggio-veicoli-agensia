package it.univaq.disim.oop.bhertz.domain;

import java.time.LocalDate;

public class AssistanceTicket {

	private TicketState state;
	private Integer id;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;

	private Veicle sostituteVeicle;
	private Contract contract;

	public TicketState getState() {
		return state;
	}

	public void setState(TicketState state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

}
