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
import it.univaq.disim.oop.bhertz.business.impl.ram.RAMTypesServiceImpl;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
import it.univaq.disim.oop.bhertz.view.ContractOrderByDate;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;
import it.univaq.disim.oop.bhertz.view.ViewUtility;

public class FileVeicleserviceImpl implements VeiclesService {

	private Map<Integer, Veicle> veicles = new HashMap<>();
	private TypesService typeService;
	private long counter;
	private String veicleFileName;

	public FileVeicleserviceImpl(String veicleFileName) {
		this.veicleFileName = veicleFileName;
		this.typeService = new RAMTypesServiceImpl();
		try {
			this.readList();
		} catch (BusinessException e) {
			ViewDispatcher.getInstance().renderError(e);
		}
	}

	/*************************************************************************/
	
	
	
	@Override
	public List<Veicle> getVeiclesByType(Type t) throws BusinessException {
		List<Veicle> result = new ArrayList<>();
		for (Veicle v : veicles.values()) {
			if (v.getType().getId() == t.getId())
				result.add(v);
		}
		return result;
	}

	@Override
	public Veicle getVeicleByID(int id) throws BusinessException {
		return veicles.get(id);
	}

	@Override
	public List<Veicle> getVeiclesByState(VeicleState state) throws BusinessException {
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
		Integer max = 0;
		for (Veicle v : veicles.values())
			max = (max > v.getId()) ? max : v.getId();
		veicle.setId(max + 1);
		this.veicles.put(veicle.getId(), veicle);
		saveList();
	}

	@Override
	public void deleteVeicle(Integer id) throws BusinessException {
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
		saveList();
	}

	@Override
	public void setVeicle(Integer id, String model, double km, double consuption, String fuel) throws BusinessException {
		Veicle v = veicles.get(id);
		v.setModel(model);
		v.setConsumption(consuption);
		v.setKm(km);
		v.setFuel(fuel);
		this.veicles.put(id, v);
		saveList();
	}

	@Override
	public void setVeicle(Veicle veicle) throws BusinessException {
		this.veicles.put(veicle.getId(), veicle);
		saveList();
	}

	@Override
	public boolean isVeicleFree(LocalDate startDate, LocalDate endDate, List<Contract> contractOfVeicle) throws BusinessException {

		for (int i = 0; i < contractOfVeicle.size(); i++) {
			Contract c = contractOfVeicle.get(i);
			if (!(startDate.isAfter(c.getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT)) || endDate.plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT).isBefore(c.getStart())))
				return false;
		}
		return true;
	}

	@Override
	public String FindAviableDays(List<Contract> contractOfVeicle) throws BusinessException {

		if (contractOfVeicle.isEmpty())
			return "veicolo disponibile per qualunque periodo";

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
		ContractService contractService = BhertzBusinessFactory.getInstance().getContractService();
		MaintenanceService maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();
		for (Veicle v : veicles.values()) {
			AssistanceTicket a = maintenanceService.getTicketByDate(v, LocalDate.now());
			Contract c = contractService.getContractByDate(v, LocalDate.now());
			if (a != null) v.setState(VeicleState.MAINTENANCE);
			else if (c != null) v.setState(VeicleState.BUSY);
			else v.setState(VeicleState.FREE);
		}
	}

	
	
	/******************************************************************/
	
	
	private void saveList() throws BusinessException {
		FileUtility f = new FileUtility();
		List<String[]> list = new ArrayList<>();
		for (Veicle v : veicles.values()) {
			String[] s = new String[10];
			s[0] = v.getId().toString();
			s[1] = v.getModel();
			s[2] = v.getPlate();
			// FREE: 0 MAINTENANCE: 1 BUSY: 2
			switch (v.getState()) {
			case FREE:
				s[3] = "0";
				break;
			case MAINTENANCE:
				s[3] = "1";
				break;
			case BUSY:
				s[3] = "2";
				break;
			}
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

	private void readList() throws BusinessException {
		FileUtility f = new FileUtility();
		FileData fileData = f.getAllByFile(veicleFileName);
		this.veicles = new HashMap<Integer, Veicle>();
		for (String[] row : fileData.getRows()) {
			Veicle veicle = null;
			switch (row[3]) {
			case "0":
				veicle.setState(VeicleState.FREE);
				break;
			case "1":
				veicle.setState(VeicleState.MAINTENANCE);
				break;
			case "2":
				veicle.setState(VeicleState.BUSY);
				break;
			}

			veicle.setId(Integer.parseInt(row[0]));
			veicle.setModel(row[1]);
			veicle.setPlate(row[2]);
			veicle.setKm(Double.parseDouble(row[4]));
			veicle.setConsumption(Double.parseDouble(row[5]));
			veicle.setFuel(row[6]);
			veicle.setPriceForKm(Double.parseDouble(row[7]));
			veicle.setPriceForDay(Double.parseDouble(row[8]));
			veicle.setType(typeService.getTypeByID(Integer.parseInt(row[9])));
			this.counter = fileData.getCount();
			this.veicles.put(veicle.getId(), veicle);

		}

	}
}
