package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public class RAMFeedbackServiceImpl implements FeedbackService {

	private Map<Integer, Feedback> feeds = new HashMap<>();
	private Integer counter = 1;
	
	@Override
	public List<Feedback> getAllFeedbacks() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void addFeedback(Feedback feedback) {
		feedback.setId(counter++);
		feeds.put(feedback.getId(), feedback);
	}
	@Override
	public List<Feedback> getFeedbackByVeicle(Veicle veicle) {
		
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
	public Feedback getFeedbackByID(Integer id) {
		return feeds.get(id);
	}


	@Override
	public boolean isFeedBackSet(Contract contract) {
		for (Feedback f : feeds.values())
			if (f.getContract().getId() == contract.getId())
				return true;
		return false;
	}
}
