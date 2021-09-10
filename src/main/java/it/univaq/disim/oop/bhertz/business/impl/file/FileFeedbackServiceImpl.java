package it.univaq.disim.oop.bhertz.business.impl.file;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public class FileFeedbackServiceImpl implements FeedbackService  {

	private int counter = 1;
	private String filename;

	public FileFeedbackServiceImpl(String feedbackFileName) {
		this.filename = feedbackFileName;
	}

	@Override
	public List<Feedback> getFeedbackByUser(Integer id) throws BusinessException {
		Map<Integer, Feedback> feeds = this.readList();
		List<Feedback> result = new ArrayList<>();
		for (Feedback f : feeds.values())
			if (f.getContract().getCustomer().getId() == id)
				result.add(f);
		return result;
	}

	@Override
	public void addFeedback(Feedback feedback) throws BusinessException {
		Map<Integer, Feedback> feeds = this.readList();
		feedback.setId(counter++);
		feeds.put(feedback.getId(), feedback);
		this.saveList(feeds);		
	}

	@Override
	public void removeFeedback(Integer id) throws BusinessException {
		Map<Integer, Feedback> feeds = this.readList();
		feeds.remove(id);
		this.saveList(feeds);
	}

	@Override
	public Feedback getFeedbackByID(Integer id) throws BusinessException {
		Map<Integer, Feedback> feeds = this.readList();
		return feeds.get(id);
	}

	@Override
	public boolean isFeedBackSet(Contract contract) throws BusinessException {
		Map<Integer, Feedback> feeds = this.readList();
		for (Feedback f : feeds.values())
			if (f.getContract().getId() == contract.getId())
				return true;
		return false;
	}

	@Override
	public List<Feedback> getFeedbackByVeicle(Veicle veicle) throws BusinessException {
		Map<Integer, Feedback> feeds = this.readList();
		List<Feedback> result = new ArrayList<>();
		for (Feedback f : feeds.values())
			if (f.getContract().getVeicle().getId() == veicle.getId())
				result.add(f);
		return result;
	}

	private void saveList(Map<Integer, Feedback> feeds) throws BusinessException {
		FileUtility fileUtility = new FileUtility();
		List<String[]> list = new ArrayList<>();
		for (Feedback f : feeds.values()) {
			String[] s = new String[5];
			s[0]= f.getId().toString();
			s[1]= f.getDate().toString();
			s[2]= f.getBody();
			s[3]= f.getValutation() + "";
			s[4]= f.getContract().getId().toString();
			list.add(s);
		}
		fileUtility.setAllByFile(this.filename, new FileData(this.counter, list));
	}

	private Map<Integer, Feedback> readList() throws BusinessException {
		FileUtility fileUtility = new FileUtility();
		FileData fileData = fileUtility.getAllByFile(filename);
		ContractService contractService = BhertzBusinessFactory.getInstance().getContractService();
		Map<Integer, Feedback> feeds = new HashMap<Integer, Feedback>();
		for (String[] row : fileData.getRows()) {
			Feedback feedback = new Feedback();
			feedback.setId(Integer.parseInt(row[0]));
			feedback.setDate(LocalDate.parse(row[1]));
			feedback.setBody(row[2]);
			feedback.setValutation(Integer.parseInt(row[3]));
			feedback.setContract(contractService.getContractByID(Integer.parseInt(row[4])));
			this.counter = (int) fileData.getCount();
			feeds.put(feedback.getId(), feedback);
		}
		return feeds;
	}
}
