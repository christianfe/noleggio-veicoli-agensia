package it.univaq.disim.oop.bhertz.business.impl.file;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.UserService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.controller.ViewUtility;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.ContractType;
import it.univaq.disim.oop.bhertz.domain.Customer;
import it.univaq.disim.oop.bhertz.domain.User;
import it.univaq.disim.oop.bhertz.domain.Veicle;

public class FileContractServiceImpl implements ContractService {

	private int counter = 1;
	private String filename;

	public FileContractServiceImpl(String contractsFileName) {
		this.filename = contractsFileName;
	}

	@Override
	public List<Contract> getAllContracts(int type) throws BusinessException {
		Map<Integer, Contract> contracts = this.readList();
		List<Contract> result = new ArrayList<>();
		switch (type) {
			case 0:
				for (Contract c : contracts.values())
					if (!c.isSostistuteContract())
						result.add(c);
				break;
			case 1:
				for (Contract c : contracts.values())
					if (c.isSostistuteContract())
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
		Map<Integer, Contract> contracts = this.readList();
		return contracts.get(id);
	}

	@Override
	public Contract getContractByDate(Veicle veicle, LocalDate date) throws BusinessException {
		Map<Integer, Contract> contracts = this.readList();
		for (Contract c : contracts.values())
			if (c.getVeicle().getId() == veicle.getId() && (date.isEqual(c.getStart()) || (date.isAfter(c.getStart())
					&& date.isBefore(c.getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT + 1)))))
				return c;
		return null;
	}

	@Override
	public List<Contract> getContractsByUser(int type, User user) throws BusinessException {
		Map<Integer, Contract> contracts = this.readList();
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
		Map<Integer, Contract> contracts = this.readList();
		contract.setId(counter++);
		contracts.put(contract.getId(), contract);
		this.saveList(contracts);
	}

	@Override
	public void setContract(Contract contract) throws BusinessException {
		Map<Integer, Contract> contracts = this.readList();
		contracts.put(contract.getId(), contract);
		this.saveList(contracts);
	}

	@Override
	public void setPaid(Integer id, boolean value) throws BusinessException {
		Map<Integer, Contract> contracts = this.readList();
		Contract c = contracts.get(id);
		c.setPaid(value);
		setContract(c);
	}

	@Override
	public void removeContract(Integer id) throws BusinessException {
		Map<Integer, Contract> contracts = this.readList();
		contracts.remove(id);
		this.saveList(contracts);
	}

	@Override
	public List<Contract> getContractsByVeicle(int type, Integer idVeicle) throws BusinessException {
		Map<Integer, Contract> contracts = this.readList();
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

	/*
	 * in ogni File....Service abbiamo implementato i metodi "save list" e
	 * "readList" che hanno rispettivamente il compito di creare il file prendendo
	 * in input la mappa e viceversa. Questi metodi verranno poi richiamati dagli
	 * altri metodi della classe che devono leggere i dati e/o modificarli. Il
	 * metodo "ReadList" restituisce una mappa, ciò ci ha permesso di riutilizzare
	 * per gli altri metodi un codice molto simile a quello utilizzato
	 * nell'implementazione su RAM
	 */

	private void saveList(Map<Integer, Contract> contracts) throws BusinessException {
		FileUtility fileUtility = new FileUtility();
		List<String[]> list = new ArrayList<>();
		for (Contract c : contracts.values()) {
			String[] s = new String[15];
			s[0] = c.getId().toString();
			s[1] = c.getStart().toString();
			s[2] = c.getEnd().toString();
			s[3] = c.getStartKm() + "";
			s[4] = c.getEndKm() + "";
			s[5] = c.getType() + "";
			s[6] = c.getPrice() + "";
			s[7] = c.getState() + "";
			s[8] = c.isPaid() ? "1" : "0";
			s[9] = c.getReturnDateTime();
			s[10] = c.getDeliverDateTime();
			s[11] = c.isSostistuteContract() ? "1" : "0";
			s[12] = c.getCustomer().getId().toString();
			s[13] = c.getVeicle().getId().toString();
			list.add(s);
		}
		fileUtility.setAllByFile(this.filename, new FileData(this.counter, list));
	}

	private Map<Integer, Contract> readList() throws BusinessException {
		FileUtility fileUtility = new FileUtility();
		FileData fileData = fileUtility.getAllByFile(filename);
		VeiclesService veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
		UserService userService = BhertzBusinessFactory.getInstance().getUserService();
		Map<Integer, Contract> contracts = new HashMap<Integer, Contract>();
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
			contract.setPaid(row[8].equals("1"));
			if (!row[9].equals("null"))
				contract.setReturnDateTime(row[9]);
			if (!row[10].equals("null"))
				contract.setDeliverDateTime(row[10]);
			contract.setSostistuteContract(row[11].equals("1"));
			contract.setCustomer((Customer) userService.getUsersByID(Integer.parseInt(row[12])));
			contract.setVeicle(veiclesService.getVeicleByID(Integer.parseInt(row[13])));
			this.counter = (int) fileData.getCount();
			contracts.put(contract.getId(), contract);
		}
		return contracts;
	}
}
