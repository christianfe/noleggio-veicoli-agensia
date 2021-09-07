package it.univaq.disim.oop.bhertz.domain;

import java.time.LocalDate;

public class AssistanceTicket {

	private TicketState state;
	private Integer id;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private String timeStart;
	private String timeEnd;
	private double veicleKm;

	private Veicle sostituteVeicle;
	private Contract contract;

	public Veicle getSostituteVeicle() {
		return sostituteVeicle;
	}

	public void setSostituteVeicle(Veicle sostituteVeicle) {
		this.sostituteVeicle = sostituteVeicle;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

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

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public double getVeicleKm() {
		return veicleKm;
	}

	public void setVeicleKm(double veicleKm) {
		this.veicleKm = veicleKm;
	}

}
