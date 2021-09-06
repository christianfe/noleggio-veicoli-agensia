package it.univaq.disim.oop.bhertz.domain;

import java.time.LocalDate;

public class Feedback {

	private LocalDate date;
	private Integer id;
	private String body;
	private int valutation;
	private Contract contract;

	public Feedback() {
		this.setDate(LocalDate.now());
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getValutation() {
		return valutation;
	}

	public void setValutation(int valutation) {
		this.valutation = valutation;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

}
