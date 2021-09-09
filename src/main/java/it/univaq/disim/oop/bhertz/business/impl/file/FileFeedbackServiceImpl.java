package it.univaq.disim.oop.bhertz.business.impl.file;

import java.util.List;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public class FileFeedbackServiceImpl implements FeedbackService  {

	@Override
	public List<Feedback> getAllFeedbacks() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Feedback> getFeedbackByVeicle(Veicle veicle) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Feedback> getFeedbackByUser(Integer id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFeedback(Feedback feedback) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFeedback(Integer id) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Feedback getFeedbackByID(Integer id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFeedBackSet(Contract contract) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}}
