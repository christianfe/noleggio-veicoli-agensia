package it.univaq.disim.oop.bhertz.business.impl.file;

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

public class FileMaintenanceServiceImpl implements MaintenanceService {

	private Map<Integer, AssistanceTicket> tickets = new HashMap<>();
	private int counter = 1;
	private String maintenanceFileName;

	public FileMaintenanceServiceImpl(String maintenanceFileName) {
		this.maintenanceFileName = maintenanceFileName;
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
	}

	private void saveList() throws BusinessException {
		FileUtility f = new FileUtility();
		List<String[]> list = new ArrayList<>();
		for (AssistanceTicket t : tickets.values()) {
			String[] s = new String[10];
			s[0] = t.getId() + "";
			// REQUIRED: 0 WORKING: 1 ENDED: 2 READY: 3
			switch (t.getState()) {
			case REQUIRED:
				s[1] = "0";
				break;
			case WORKING:
				s[1] = "1";
				break;
			case ENDED:
				s[1] = "2";
				break;
			case READY:
				s[1] = "3";
				break;
			}
			s[2] = t.getDescription();
			s[3] = t.getStartDate()+"";
			s[4] = t.getEndDate()+"";
			s[5] = t.getTimeStart();
			s[6] = t.getTimeEnd();
			s[7] = t.getVeicleKm()+"";
			s[8] = t.getContract().getId()+"";
			
			if (t.getSubstituteContract() == null)
				s[9] = "0";
			else 
				s[9] = t.getSubstituteContract().getId()+"";
			
			
			

			list.add(s);
		}
		f.setAllByFile(this.maintenanceFileName, new FileData(this.counter, list));
	}
}
