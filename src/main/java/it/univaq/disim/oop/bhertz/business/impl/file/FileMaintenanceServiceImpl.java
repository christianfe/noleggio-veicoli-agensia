package it.univaq.disim.oop.bhertz.business.impl.file;

import java.time.LocalDate;
import java.util.List;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public class FileMaintenanceServiceImpl implements MaintenanceService {

	public FileMaintenanceServiceImpl(String maintenanceFileName) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<AssistanceTicket> getAllTickets() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AssistanceTicket getTicketByID(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AssistanceTicket> getTicketByUser(User user) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AssistanceTicket> getTicketByVeicle(Integer idVeicle) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTicket(AssistanceTicket ticket) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTicket(AssistanceTicket ticket) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMaintenance(Integer id) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AssistanceTicket getTicketByDate(Veicle veicle, LocalDate date) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}}
