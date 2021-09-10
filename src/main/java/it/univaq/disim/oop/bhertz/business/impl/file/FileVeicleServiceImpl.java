package it.univaq.disim.oop.bhertz.business.impl.file;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.FeedbackService;
import it.univaq.disim.oop.bhertz.business.MaintenanceService;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
import it.univaq.disim.oop.bhertz.view.ContractOrderByDate;
import it.univaq.disim.oop.bhertz.view.ViewUtility;

public class FileVeicleServiceImpl implements VeiclesService {

	private long counter = 1;
	private String veicleFileName;

	public FileVeicleServiceImpl(String veicleFileName) {
		this.veicleFileName = veicleFileName;
	}

	@Override
	public List<Veicle> getVeiclesByType(Type t) throws BusinessException {
		Map<Integer, Veicle> veicles = this.readList();
		List<Veicle> result = new ArrayList<>();
		for (Veicle v : veicles.values())
			if (v.getType().getId() == t.getId())
				result.add(v);
		return result;
	}

	@Override
	public Veicle getVeicleByID(int id) throws BusinessException {
		Map<Integer, Veicle> veicles = this.readList();
		return veicles.get(id);
	}

	@Override
	public List<Veicle> getVeiclesByState(VeicleState state) throws BusinessException {
		Map<Integer, Veicle> veicles = this.readList();
		List<Veicle> result = new ArrayList<>();
		for (Veicle v : veicles.values())
			if (v.getState() == state)
				result.add(v);
		return result;
	}

	@Override
	public List<Veicle> getVeiclesByStateAndType(Type type, boolean free, boolean busy, boolean maintenance) throws BusinessException {
		List<Veicle> result = new ArrayList<>();
		if (free) {
			List<Veicle> resultF = this.getVeiclesByState(VeicleState.FREE);
			for (Veicle v : resultF)
				if (v.getType().getId() == type.getId())
					result.add(v);
		}
		if (maintenance) {
			List<Veicle> resultM = this.getVeiclesByState(VeicleState.MAINTENANCE);
			for (Veicle v : resultM)
				if (v.getType().getId() == type.getId())
					result.add(v);
		}
		if (busy) {
			List<Veicle> resultB = this.getVeiclesByState(VeicleState.BUSY);
			for (Veicle v : resultB)
				if (v.getType().getId() == type.getId())
					result.add(v);
		}
		return result;
	}

	@Override
	public void addVeicle(Veicle veicle) throws BusinessException {
		Map<Integer, Veicle> veicles = this.readList();
		veicle.setId((int) counter++);
		veicles.put(veicle.getId(), veicle);
		this.saveList(veicles);
	}

	@Override
	public void deleteVeicle(Integer id) throws BusinessException {
		Map<Integer, Veicle> veicles = this.readList();
		ContractService contractService = BhertzBusinessFactory.getInstance().getContractService();
		FeedbackService feedbackService = BhertzBusinessFactory.getInstance().getFeedbackService();
		MaintenanceService maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();

		List <Contract> cc = contractService.getContractsByVeicle(2, id);
		List <Feedback> ff = feedbackService.getFeedbackByVeicle(this.getVeicleByID(id));
		List <AssistanceTicket> mm = maintenanceService.getTicketByVeicle(id);

		for (Feedback f : ff)
			feedbackService.removeFeedback(f.getId());
		for (Contract c : cc)
			contractService.removeContract(c.getId());
		for (AssistanceTicket m : mm)
			maintenanceService.removeMaintenance(m.getId());

		veicles.remove(id);
		this.saveList(veicles);
	}

	@Override
	public void setVeicle(Integer id, String model, double km, double consuption, String fuel) throws BusinessException {
		Map<Integer, Veicle> veicles = this.readList();
		Veicle v = getVeicleByID(id);
		v.setModel(model);
		v.setConsumption(consuption);
		v.setKm(km);
		v.setFuel(fuel);
		veicles.put(id, v);
		this.saveList(veicles);
	}

	@Override
	public void setVeicle(Veicle veicle) throws BusinessException {
		Map<Integer, Veicle> veicles = this.readList();
		veicles.put(veicle.getId(), veicle);
		this.saveList(veicles);
	}

	@Override
	public boolean isVeicleFree(LocalDate startDate, LocalDate endDate, List<Contract> contractOfVeicle) throws BusinessException {
		for (Contract c : contractOfVeicle) {
			if (!(startDate.isAfter(c.getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT)) || endDate.plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT).isBefore(c.getStart())))
				return false;
		}
		return true;
	}

	@Override
	public String FindAviableDays(List<Contract> contractOfVeicle) throws BusinessException {
		if (contractOfVeicle.isEmpty())
			return "veicolo disponibile in qualunque periodo";

		Collections.sort(contractOfVeicle, new ContractOrderByDate());
		String body;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (LocalDate.now().isBefore(contractOfVeicle.get(0).getStart().minusDays(3)))
			body = "prima del " + contractOfVeicle.get(0).getStart().minusDays(3).format(formatter) + ", \n";
		else
			body = "";
		for (int i = 0; i < contractOfVeicle.size() - 1; i++)
			if (!(contractOfVeicle.get(i).getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT).isEqual(contractOfVeicle.get(i + 1).getStart())))
				body += "dal " + contractOfVeicle.get(i).getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT).format(formatter) + " al "
						+ contractOfVeicle.get(i + 1).getStart().minusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT).format(formatter) + ", \n";
		body += "dopo il " + contractOfVeicle.get(contractOfVeicle.size() - 1).getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT).format(formatter);
		return body;
	}

	@Override
	public void refreshAllStates() throws BusinessException {
		Map<Integer, Veicle> veicles = this.readList();
		ContractService contractService = BhertzBusinessFactory.getInstance().getContractService();
		MaintenanceService maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();
		for (Veicle v : veicles.values()) {
			AssistanceTicket a = maintenanceService.getTicketByDate(v, LocalDate.now());
			Contract c = contractService.getContractByDate(v, LocalDate.now());
			if (a != null && a.getState() != TicketState.ENDED) {
				v.setState(VeicleState.MAINTENANCE);
				c.setState(ContractState.MAINTENANCE);
				contractService.setContract(c);
			} else if (c != null && c.getState() != ContractState.ENDED && c.getState() != ContractState.BOOKED) {
				v.setState(VeicleState.BUSY);
				c.setState(ContractState.ACTIVE);
				contractService.setContract(c);
			}
			else {
				v.setState(VeicleState.FREE);
			}
		}
		this.saveList(veicles);
	}
	

	@Override
	public void updatePrices(double oldPriceForDay, double oldPriceForKm, double newPriceForDay, double newPriceForKm) throws BusinessException {
		Map<Integer, Veicle> veicles = this.readList();
		for (Veicle v : veicles.values()) {
			if (v.getPriceForDay() == oldPriceForDay)
				v.setPriceForDay(newPriceForDay);
			if (v.getPriceForKm() == oldPriceForKm)
				v.setPriceForKm(newPriceForKm);
			veicles.put(v.getId(), v);
		}
		this.saveList(veicles);
	}


	private void saveList(Map<Integer, Veicle> veicles) throws BusinessException {
		FileUtility f = new FileUtility();
		List<String[]> list = new ArrayList<>();
		for (Veicle v : veicles.values()) {
			String[] s = new String[10];
			s[0] = v.getId().toString();
			s[1] = v.getModel();
			s[2] = v.getPlate();
			s[3] = v.getState() + "";
			s[4] = v.getKm() + "";
			s[5] = v.getConsumption() + "";
			s[6] = v.getFuel();
			s[7] = v.getPriceForKm() + "";
			s[8] = v.getPriceForDay() + "";
			s[9] = v.getType().getId().toString();
			list.add(s);
		}
		f.setAllByFile(this.veicleFileName, new FileData(this.counter, list));
	}

	private Map<Integer, Veicle> readList() throws BusinessException {
		FileUtility f = new FileUtility();
		FileData fileData = f.getAllByFile(veicleFileName);
		TypesService typeService = BhertzBusinessFactory.getInstance().getTypesService();
		Map<Integer, Veicle> veicles = new HashMap<>();
		for (String[] row : fileData.getRows()) {
			Veicle veicle = new Veicle();
			veicle.setId(Integer.parseInt(row[0]));
			veicle.setModel(row[1]);
			veicle.setPlate(row[2]);
			switch (row[3]) {
			case "FREE":
				veicle.setState(VeicleState.FREE);
				break;
			case "MAINTENANCE":
				veicle.setState(VeicleState.MAINTENANCE);
				break;
			case "BUSY":
				veicle.setState(VeicleState.BUSY);
				break;
			}
			veicle.setKm(Double.parseDouble(row[4]));
			veicle.setConsumption(Double.parseDouble(row[5]));
			veicle.setFuel(row[6]);
			veicle.setPriceForKm(Double.parseDouble(row[7]));
			veicle.setPriceForDay(Double.parseDouble(row[8]));
			veicle.setType(typeService.getTypeByID(Integer.parseInt(row[9])));
			this.counter = fileData.getCount();
			veicles.put(veicle.getId(), veicle);
		}
		return veicles;
	}

}
