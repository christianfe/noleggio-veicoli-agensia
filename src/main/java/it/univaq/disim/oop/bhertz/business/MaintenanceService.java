package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.User;

public interface MaintenanceService {

	List<AssistanceTicket> getAllTickets() throws BusinessException;
	
	AssistanceTicket getTicketByID(int id) throws BusinessException;
	
	List<AssistanceTicket> getTicketByUser(User user) throws BusinessException;
	
	void addTicket (AssistanceTicket ticket);
}
