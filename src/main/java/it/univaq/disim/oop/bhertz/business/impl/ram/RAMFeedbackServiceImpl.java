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
	public void addFeedback(Feedback feedback) throws BusinessException {
		feedback.setId(counter++);
		feeds.put(feedback.getId(), feedback);
	}
	
	@Override
	public List<Feedback> getFeedbackByVeicle(Veicle veicle) throws BusinessException {
		List<Feedback> result = new ArrayList<>();
		for (Feedback f : feeds.values())
			if (f.getContract().getVeicle().getId() == veicle.getId())
				result.add(f);

		return result;
	}


	@Override
	public Feedback getFeedbackByID(Integer id) throws BusinessException {
		return feeds.get(id);
	}


	@Override
	public boolean isFeedBackSet(Contract contract) throws BusinessException {
		for (Feedback f : feeds.values())
			if (f.getContract().getId() == contract.getId())
				return true;
		return false;
	}

	@Override
	public List<Feedback> getFeedbackByUser(Integer id) throws BusinessException {
		List<Feedback> result = new ArrayList<>();
		for (Feedback f : feeds.values())
			if (f.getContract().getCustomer().getId() == id)
				result.add(f);
		return result;

	}


	@Override
	public void removeFeedback(Integer id) throws BusinessException {
		feeds.remove(id);		
	}
}
