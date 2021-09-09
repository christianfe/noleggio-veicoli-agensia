package it.univaq.disim.oop.bhertz.business.impl.file;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;

public class FileMaintenanceServiceImpl implements MaintenanceService {

	private Map<Integer, AssistanceTicket> tickets = new HashMap<>();
	private int counter = 1;
	private String filename;

	public FileMaintenanceServiceImpl(String maintenanceFileName) {
		this.filename = maintenanceFileName;
		try {
			this.readList();
		} catch (BusinessException e) {
			ViewDispatcher.getInstance().renderError(e);
		}
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
	public List<AssistanceTicket> getTicketByVeicle(Integer idVeicle) throws BusinessException {
		List<AssistanceTicket> result = new ArrayList<>();
		for (AssistanceTicket v : tickets.values())
			if (v.getContract().getVeicle().getId() == idVeicle)
				result.add(v);
		return result;
	}

	@Override
	public void addTicket(AssistanceTicket ticket) throws BusinessException {
		ticket.setId(counter++);
		tickets.put(ticket.getId(), ticket);
		this.saveList();
	}

	@Override
	public void setTicket(AssistanceTicket ticket) throws BusinessException {
		tickets.put(ticket.getId(), ticket);
		this.saveList();
	}

	@Override
	public void removeMaintenance(Integer id) throws BusinessException {
		tickets.remove(id);
		this.saveList();

	}

	@Override
	public AssistanceTicket getTicketByDate(Veicle veicle, LocalDate date) throws BusinessException {
		for (AssistanceTicket t : tickets.values()) {
			try {
				if (veicle.getId() == t.getContract().getVeicle().getId() && (date.isEqual(t.getStartDate()) || date.isEqual(t.getEndDate()) || (date.isAfter(t.getStartDate()) && date.isBefore(t.getEndDate()))))
					return t;
			} catch (NullPointerException e) {
				continue;
			}
		}
		return null;
	}

	private void saveList() throws BusinessException {
		FileUtility f = new FileUtility();
		List<String[]> list = new ArrayList<>();
		for (AssistanceTicket a : tickets.values()) {
			String[] s = new String[10];
			s[0] = a.getId().toString();
			s[1] = a.getState() + "";
			s[2] = a.getDescription();
			s[3] = a.getStartDate().toString();
			s[4] = a.getEndDate().toString();
			s[5] = a.getTimeStart();
			s[6] = a.getTimeEnd();
			s[7] = a.getVeicleKm() + "";
			s[8] = a.getContract().getId().toString();
			s[9] = (a.getSubstituteContract() == null) ? "0" : a.getSubstituteContract().getId().toString();
			list.add(s);
		}
		f.setAllByFile(this.filename, new FileData(this.counter, list));
	}
	
	private void readList() throws BusinessException {
		FileUtility fileUtility = new FileUtility();
		FileData fileData = fileUtility.getAllByFile(filename);
		ContractService contractService = BhertzBusinessFactory.getInstance().getContractService();
		this.tickets = new HashMap<Integer, AssistanceTicket>();
		for (String[] row : fileData.getRows()) {
			AssistanceTicket ticket = new AssistanceTicket();
			ticket.setId(Integer.parseInt(row[0]));
			switch (row[1]) {
			case "REQUIRED":
				ticket.setState(TicketState.REQUIRED);
				break;
			case "WORKING":
				ticket.setState(TicketState.WORKING);
				break;
			case "READY":
				ticket.setState(TicketState.READY);
				break;
			case "ENDED":
				ticket.setState(TicketState.ENDED);
				break;
			}
			ticket.setDescription(row[2]);
			ticket.setStartDate(LocalDate.parse(row[3]));
			ticket.setEndDate(LocalDate.parse(row[4]));
			ticket.setTimeStart(row[5]);
			ticket.setTimeEnd(row[6]);
			ticket.setVeicleKm(Double.parseDouble(row[7]));
			ticket.setContract(contractService.getContractByID(Integer.parseInt(row[8])));
			ticket.setSubstituteContract(row[9].equals("0") ? null : contractService.getContractByID(Integer.parseInt(row[9])));
			this.counter = (int) fileData.getCount();
			this.tickets.put(ticket.getId(), ticket);
		}
	}
}
