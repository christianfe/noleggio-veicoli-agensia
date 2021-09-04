package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public interface FeedbackService {

	List<Feedback> getAllFeedbacks() throws BusinessException;

	Feedback getFeedbackByID(int id) throws BusinessException;

	List<Feedback> getFeedbackByVeicle(Veicle veicle) throws BusinessException;

	void addFeedback(Feedback feedback);

}
