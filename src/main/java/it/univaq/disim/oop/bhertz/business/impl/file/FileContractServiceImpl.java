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
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.ContractType;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;

public class FileContractServiceImpl implements ContractService {

	private Map<Integer, Contract> contracts = new HashMap<>();
	private int counter = 1;
	private String filename;

	public FileContractServiceImpl(String contractsFileName) {
		this.filename = contractsFileName;
		try {
			this.readList();
		} catch (BusinessException e) {
			ViewDispatcher.getInstance().renderError(e);
		}
	}

	@Override
	public List<Contract> getAllContracts(int type) throws BusinessException {
		List<Contract> result = new ArrayList<>();
		switch (type) {
		case 0:
			for (Contract c : contracts.values())
				if (c.isSostistuteContract() == false)
					result.add(c);
			break;
		case 1:
			for (Contract c : contracts.values())
				if (c.isSostistuteContract() == true)
					result.add(c);
			break;

		case 2:
			for (Contract c : contracts.values())
				result.add(c);
			break;
		}
		return result;
	}

	@Override
	public Contract getContractByID(int id) throws BusinessException {
		return contracts.get(id);
	}

	@Override
	public Contract getContractByDate(Veicle veicle, LocalDate date) throws BusinessException {
		for (Contract c : contracts.values())
			if (c.getVeicle().getId() == veicle.getId() && (date.isEqual(c.getStart()) || (date.isAfter(c.getStart()) && date.isBefore(c.getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT + 1)))))
				return c;
		return null;
	}

	@Override
	public List<Contract> getContractsByUser(int type, User user) throws BusinessException {
		List<Contract> result = new ArrayList<>();
		for (Contract c : contracts.values())
			if (c.getCustomer().getId() == user.getId())
				switch (type) {
				case 0:
					if (!c.isSostistuteContract())
						result.add(c);
					break;
				case 1:
					if (c.isSostistuteContract())
						result.add(c);
					break;
				case 2:
					result.add(c);
					break;
				}

		return result;
	}

	@Override
	public void addContract(Contract contract) throws BusinessException {
		contract.setId(counter);
		contracts.put(counter++, contract);
		this.saveList();
	}

	@Override
	public void setContract(Contract contract) throws BusinessException {
		contracts.put(contract.getId(), contract);
		this.saveList();
	}

	@Override
	public void setPaid(Integer id, boolean value) throws BusinessException {
		Contract c = contracts.get(id);
		c.setPaid(value);
		contracts.put(id, c);
		this.saveList();
	}

	@Override
	public void removeContract(Integer id) throws BusinessException {
		contracts.remove(id);
		this.saveList();
	}

	@Override
	public List<Contract> getContractsByVeicle(int type, Integer idVeicle) throws BusinessException {
		List<Contract> result = new ArrayList<>();
		for (Contract c : contracts.values())
			if (c.getVeicle().getId() == idVeicle)
				switch (type) {
				case 0:
					if (!c.isSostistuteContract())
						result.add(c);
					break;
				case 1:
					if (c.isSostistuteContract())
						result.add(c);
					break;
				case 2:
					result.add(c);
					break;
				}
		return result;
	}
	private void saveList() throws BusinessException {
		FileUtility fileUtility = new FileUtility();
		List<String[]> list = new ArrayList<>();
		for (Contract c : contracts.values()) {
			String[] s = new String[15];
			s[0]  = c.getId().toString();
			s[1]  = c.getStart().toString();
			s[2]  = c.getEnd().toString();
			s[3]  = c.getStartKm() + "";
			s[4]  = c.getEndKm() + "";
			s[5]  = c.getType() + "";
			s[6]  = c.getPrice() + "";
			s[7]  = c.getState() + "";
			s[8]  = c.isPaid() ? "1" : "0";
			s[9]  = c.getReturnDateTime();
			s[10] = c.getDeliverDateTime();
			s[11] = c.isSostistuteContract() ? "1" : "0";
			s[12] = c.getCustomer().getId().toString();
			s[13] = c.getVeicle().getId().toString();
			s[14] = c.getAssistance().getId().toString() != null ? c.getAssistance().getId().toString() : "0";
			list.add(s);
		}
		fileUtility.setAllByFile(this.filename, new FileData(this.counter, list));
	}

	private void readList() throws BusinessException {
		FileUtility fileUtility = new FileUtility();
		FileData fileData = fileUtility.getAllByFile(filename);
		VeiclesService veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
		UserService userService = BhertzBusinessFactory.getInstance().getUserService();
		MaintenanceService maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();
		this.contracts = new HashMap<Integer, Contract>();
		for (String[] row : fileData.getRows()) {
			Contract contract = new Contract();
			contract.setId(Integer.parseInt(row[0]));
			contract.setStart(LocalDate.parse(row[1]));
			contract.setEnd(LocalDate.parse(row[2]));
			contract.setStartKm(Double.parseDouble(row[3]));
			contract.setEndKm(Double.parseDouble(row[4]));
			switch (row[5]) {
			case "KM":
				contract.setType(ContractType.KM);
				break;
			case "TIME":
				contract.setType(ContractType.TIME);
				break;
			}
			contract.setPrice(Double.parseDouble(row[6]));
			switch (row[7]) {
			case "BOOKED":
				contract.setState(ContractState.BOOKED);
				break;
			case "ACTIVE":
				contract.setState(ContractState.ACTIVE);
				break;
			case "MAINTENANCE":
				contract.setState(ContractState.MAINTENANCE);
				break;
			case "ENDED":
				contract.setState(ContractState.ENDED);
				break;
			}
			contract.setPaid(row[8] == "1");
			contract.setReturnDateTime(row[9]);
			contract.setDeliverDateTime(row[10]);
			contract.setSostistuteContract(row[11] == "1");
			contract.setCustomer((Customer)userService.getUsersByID(Integer.parseInt(row[12])));
			contract.setVeicle(veiclesService.getVeicleByID(Integer.parseInt(row[13])));
			if (!row[14].equals("0")) contract.setAssistance(maintenanceService.getTicketByID(Integer.parseInt(row[14])));
			this.counter = (int) fileData.getCount();
			this.contracts.put(contract.getId(), contract);
		}
	}	
}
