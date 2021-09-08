package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public interface FeedbackService {

	List<Feedback> getAllFeedbacks();

	List<Feedback> getFeedbackByVeicle(Veicle veicle);
	
	List<Feedback> getFeedbackByUser(Integer id);

	void addFeedback(Feedback feedback);
	
	void removeFeedback(Integer id);

	Feedback getFeedbackByID(Integer id);
	
	boolean isFeedBackSet(Contract contract);

}
