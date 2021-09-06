package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public interface FeedbackService {

	List<Feedback> getAllFeedbacks();

	List<Feedback> getFeedbackByVeicle(Veicle veicle);

	void addFeedback(Feedback feedback);

	Feedback getFeedbackByID(Integer id);
	
	boolean isFeedBackSet(Contract contract);

}
