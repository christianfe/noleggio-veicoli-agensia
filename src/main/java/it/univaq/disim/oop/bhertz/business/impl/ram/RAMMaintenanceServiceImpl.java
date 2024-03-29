package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public class RAMMaintenanceServiceImpl implements MaintenanceService {

	private Map<Integer, AssistanceTicket> tickets = new HashMap<>();
	private int counter = 1;

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

	@Override
	public void addTicket(AssistanceTicket ticket) throws BusinessException {
		ticket.setId(counter++);
		tickets.put(ticket.getId(), ticket);
	}

	@Override
	public void setTicket(AssistanceTicket ticket) throws BusinessException {
		tickets.put(ticket.getId(), ticket);
	}

	@Override
	public List<AssistanceTicket> getTicketByVeicle(Integer idVeicle) throws BusinessException {
		List<AssistanceTicket> result = new ArrayList<>();
		for (AssistanceTicket v : tickets.values())
			if (v.getContract().getVeicle().getId() == idVeicle)
				result.add(v);
		return result;
	}

	@Override
	public void removeMaintenance(Integer id) throws BusinessException {
		tickets.remove(id);
	}

	@Override
	public AssistanceTicket getTicketByDate(Veicle veicle, LocalDate date) throws BusinessException {
		for (AssistanceTicket t : tickets.values()) {
			try {
				if (veicle.getId() == t.getContract().getVeicle().getId()
						&& (date.isEqual(t.getStartDate()) || date.isEqual(t.getEndDate())
								|| (date.isAfter(t.getStartDate()) && date.isBefore(t.getEndDate()))))
					return t;
			} catch (NullPointerException e) {
				continue;
			}
		}
		return null;
	}

}
