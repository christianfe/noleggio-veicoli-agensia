package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public interface FeedbackService {

	List<Feedback> getFeedbackByVeicle(Veicle veicle) throws BusinessException;

	List<Feedback> getFeedbackByUser(Integer id) throws BusinessException;

	void addFeedback(Feedback feedback) throws BusinessException;

	void removeFeedback(Integer id) throws BusinessException;

	Feedback getFeedbackByID(Integer id) throws BusinessException;

	boolean isFeedBackSet(Contract contract) throws BusinessException;

}
