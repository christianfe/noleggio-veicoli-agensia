package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public class RAMfeedbackServiceImpl implements FeedbackService {

	private Map<Integer, Feedback> feeds = new HashMap<>();
	private int counter = 1;
	
	
	
	@Override
	public List<Feedback> getAllFeedbacks() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void addFeedback(Feedback feedback) {
		feedback.setId(counter++);
		feeds.put(feedback.getId(), feedback);
	}
	@Override
	public List<Feedback> getFeedbackByVeicle(Veicle veicle) throws BusinessException {
		
		System.out.println("check getfeedbackbyveicle");
		System.out.println(veicle.getModel());
		List<Feedback> result = new ArrayList<>();
		for (Feedback v : feeds.values()) {
			if (v.getContract().getVeicle().getId() == veicle.getId())
				result.add(v);
		}
		return result;
	}


	@Override
	public Feedback getFeedbackByID(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
