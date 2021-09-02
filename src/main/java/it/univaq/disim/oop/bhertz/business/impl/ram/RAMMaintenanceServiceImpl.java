package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;

public class RAMMaintenanceServiceImpl implements MaintenanceService{

	private Map<Integer, AssistanceTicket> tickets = new HashMap<>(); 
	private ViewDispatcher dispatcher;
	
	public RAMMaintenanceServiceImpl() throws BusinessException {
		dispatcher =  ViewDispatcher.getInstance();
		
		AssistanceTicket t1 = new AssistanceTicket();
		t1.setDescription("gomma bucata");
		t1.setState(TicketState.WORKING);
		t1.setId(1);
		
		AssistanceTicket t2 = new AssistanceTicket();
		t2.setDescription("graffio");
		t2.setState(TicketState.REQUIRED);
		t2.setId(2);
	}
	
	
	@Override
	public List<AssistanceTicket> getAllTickets() throws BusinessException {
		return new ArrayList<>(tickets.values());
	}

	@Override
	public AssistanceTicket getTicketByID(int id) throws BusinessException {
		return tickets.get(id);
	}

	@Override
	public List<AssistanceTicket> getTicketByUser(User user) throws BusinessException {
		List<AssistanceTicket> result = new ArrayList<>();
		for (AssistanceTicket v : tickets.values())
			if (v.getContract().getCustomer().getId() == user.getId())
				result.add(v);
		
		return result;
	}

}
