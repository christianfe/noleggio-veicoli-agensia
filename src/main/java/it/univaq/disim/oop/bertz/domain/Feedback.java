package it.univaq.disim.oop.bertz.domain;

import java.time.LocalDate;

public class Feedback {

	private LocalDate date;
	private Integer id;
	private String body;
	private int valutation;
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
		// deve essere tra 1 e 5
		this.valutation = valutation;
	}
	
	
	
	
}
