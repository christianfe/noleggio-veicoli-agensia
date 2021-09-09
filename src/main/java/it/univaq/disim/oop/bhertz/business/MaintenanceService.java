package it.univaq.disim.oop.bhertz.business;

import java.time.LocalDate;
import java.util.List;

import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public interface MaintenanceService {

	List<AssistanceTicket> getAllTickets() throws BusinessException;
	
	AssistanceTicket getTicketByID(int id) throws BusinessException;
	
	List<AssistanceTicket> getTicketByUser(User user) throws BusinessException;
	
	List<AssistanceTicket> getTicketByVeicle(Integer idVeicle) throws BusinessException;
	
	void addTicket (AssistanceTicket ticket) throws BusinessException;
	
	void setTicket(AssistanceTicket ticket) throws BusinessException;

	void removeMaintenance(Integer id) throws BusinessException;
	
	AssistanceTicket getTicketByDate(Veicle veicle, LocalDate date) throws BusinessException;
}
