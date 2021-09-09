package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;

public class RAMMaintenanceServiceImpl implements MaintenanceService {

	private Map<Integer, AssistanceTicket> tickets = new HashMap<>();
	private ViewDispatcher dispatcher;
	private ContractService contractService;
	private VeiclesService veicleService;
	private int counter = 1;

	public RAMMaintenanceServiceImpl() throws BusinessException {

		dispatcher = ViewDispatcher.getInstance();
		contractService = new RAMContractServiceImpl();
		veicleService = new RAMVeicleServiceImpl();

		AssistanceTicket t1 = new AssistanceTicket();
		t1.setDescription("gomma bucata");
		t1.setState(TicketState.WORKING);
		t1.setContract(contractService.getContractByID(1));
		t1.setId(counter++);
		tickets.put(t1.getId(), t1);

		AssistanceTicket t2 = new AssistanceTicket();
		t2.setDescription("graffio");
		t2.setState(TicketState.ENDED);
		t2.setContract(contractService.getContractByID(2));
		t2.setId(counter++);
		tickets.put(t2.getId(), t2);

		AssistanceTicket t3 = new AssistanceTicket();
		t3.setDescription("cambio olio");
		t3.setState(TicketState.ENDED);
		t3.setContract(contractService.getContractByID(3));
		t3.setId(counter++);
		tickets.put(t3.getId(), t3);
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
						&& (date.isEqual(t.getStartDate()) || date.isEqual(t.getStartDate())
								|| (date.isAfter(t.getStartDate()) && date.isBefore(t.getEndDate()))))
					return t;
			} catch (NullPointerException e) {
				continue;
			}
		}
		return null;
	}

}
