package it.univaq.disim.oop.bhertz.business.impl.ram;

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
import it.univaq.disim.oop.bhertz.controller.ContractOrderByDate;
import it.univaq.disim.oop.bhertz.controller.ViewUtility;
import it.univaq.disim.oop.bhertz.domain.AssistanceTicket;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.ContractState;
import it.univaq.disim.oop.bhertz.domain.Feedback;
import it.univaq.disim.oop.bhertz.domain.TicketState;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;

public class RAMVeicleServiceImpl implements VeiclesService {

	private Map<Integer, Veicle> veicles = new HashMap<>();
	private TypesService typeService;
	private int counter;

	public RAMVeicleServiceImpl() {
		this.typeService = new RAMTypesServiceImpl();

		Type auto = null;
		Type moto = null;
		try {
			auto = typeService.getTypeByID(1);
			moto = typeService.getTypeByID(2);
		} catch (BusinessException e) {
			ViewDispatcher.getInstance().renderError(e);
		}

		Veicle panda = new Veicle(counter++, auto, "Fiat Panda", "GG999RT", 9545, 14.1, "GPL");
		Veicle punto = new Veicle(counter++, auto, "Fiat Punto", "FA054KM", 54785, 13.1, "Diesel");
		Veicle fiesta = new Veicle(counter++, auto, "Ford Fiesta", "ED125FR", 12244, 15.1, "GPL");
		;
		fiesta.setPriceForDay(45);
		Veicle malaguti = new Veicle(counter++, moto, "Runner", "RE12548", 1354, 11.8, "Benzina");
		;
		Veicle aprilia = new Veicle(counter++, moto, "Aprilia RS", "DX00486", 1245, 8.4, "Miscela");
		aprilia.setPriceForDay(60);
		aprilia.setPriceForKm(0.40);
		Veicle jeep = new Veicle(counter++, auto, "JEEP Ranegade", "FF667FF", 1125, 10.5, "Ibrida");
		jeep.setPriceForKm(0.25);

		Veicle yamaha = new Veicle(counter++, moto, "YAMAHA Xcity", "AD13257", 125, 12.5, "Benzina");
		yamaha.setPriceForKm(29);

		veicles.put(panda.getId(), panda);
		veicles.put(punto.getId(), punto);
		veicles.put(fiesta.getId(), fiesta);
		veicles.put(malaguti.getId(), malaguti);
		veicles.put(aprilia.getId(), aprilia);
		veicles.put(jeep.getId(), jeep);
		veicles.put(yamaha.getId(), yamaha);
	}

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
	public List<Veicle> getVeiclesByStateAndType(Type type, boolean free, boolean busy, boolean maintenance)
			throws BusinessException {
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
	}

	@Override
	public void deleteVeicle(Integer id) throws BusinessException {
		ContractService contractService = BhertzBusinessFactory.getInstance().getContractService();
		FeedbackService feedbackService = BhertzBusinessFactory.getInstance().getFeedbackService();
		MaintenanceService maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();

		List<Contract> cc = contractService.getContractsByVeicle(2, id);
		List<Feedback> ff = feedbackService.getFeedbackByVeicle(this.getVeicleByID(id));
		List<AssistanceTicket> mm = maintenanceService.getTicketByVeicle(id);

		for (Feedback f : ff)
			feedbackService.removeFeedback(f.getId());
		for (Contract c : cc)
			contractService.removeContract(c.getId());
		for (AssistanceTicket m : mm)
			maintenanceService.removeMaintenance(m.getId());

		veicles.remove(id);
	}

	@Override
	public void setVeicle(Integer id, String model, double km, double consuption, String fuel)
			throws BusinessException {
		Veicle v = veicles.get(id);
		v.setModel(model);
		v.setConsumption(consuption);
		v.setKm(km);
		v.setFuel(fuel);
		this.veicles.put(id, v);
	}

	@Override
	public void setVeicle(Veicle veicle) {
		this.veicles.put(veicle.getId(), veicle);
	}

	@Override
	public boolean isVeicleFree(LocalDate startDate, LocalDate endDate, List<Contract> contractOfVeicle)
			throws BusinessException {

		for (Contract c : contractOfVeicle) {
			if (!(startDate.isAfter(c.getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT))
					|| endDate.plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT).isBefore(c.getStart())))
				return false;
		}
		return true;
	}

	@Override
	public void updatePrices(double oldPriceForDay, double oldPriceForKm, double newPriceForDay, double newPriceForKm)
			throws BusinessException {
		for (Veicle v : veicles.values()) {
			if (v.getPriceForDay() == oldPriceForDay)
				v.setPriceForDay(newPriceForDay);
			if (v.getPriceForKm() == oldPriceForKm)
				v.setPriceForKm(newPriceForKm);
			veicles.put(v.getId(), v);
		}
	}

	@Override
	public String FindAviableDays(List<Contract> contractOfVeicle) throws BusinessException {

		if (contractOfVeicle.isEmpty())
			return "veicolo disponibile per qualunque periodo";

		Collections.sort(contractOfVeicle, new ContractOrderByDate()); // ordinamento gestito nella classe
																		// ContractOrderByDaye che implementa Comparator
		String body;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (LocalDate.now().isBefore(contractOfVeicle.get(0).getStart().minusDays(3)))
			body = "prima del " + contractOfVeicle.get(0).getStart().minusDays(3).format(formatter) + ", \n";
		else
			body = "";
		for (int i = 0; i < contractOfVeicle.size() - 1; i++)
			if (!(contractOfVeicle.get(i).getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT)
					.isEqual(contractOfVeicle.get(i + 1).getStart())))
				body += "dal "
						+ contractOfVeicle.get(i).getEnd().plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT)
								.format(formatter)
						+ " al " + contractOfVeicle.get(i + 1).getStart()
								.minusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT).format(formatter)
						+ ", \n";
		body += "dopo il " + contractOfVeicle.get(contractOfVeicle.size() - 1).getEnd()
				.plusDays(ViewUtility.DAYS_VEICLE_BUSY_AFTER_RENT).format(formatter);
		return body;
		/*
		 * Questo metodo genera una stringa che poi verrà stampata nel textfield nella
		 * pagina di creazione di un nuovo contratto. questa stringa elenca i giorni
		 * disponibili.
		 */
	}

	@Override
	public void refreshAllStates() throws BusinessException {
		ContractService contractService = BhertzBusinessFactory.getInstance().getContractService();
		MaintenanceService maintenanceService = BhertzBusinessFactory.getInstance().getMaintenanceService();
		for (Veicle v : veicles.values()) {
			AssistanceTicket a = maintenanceService.getTicketByDate(v, LocalDate.now());
			Contract c = contractService.getContractByDate(v, LocalDate.now());
			if (a != null && a.getState() != TicketState.ENDED) {
				v.setState(VeicleState.MAINTENANCE);
				c.setState(ContractState.MAINTENANCE);
				contractService.setContract(c);
			} else if (c != null) {
				v.setState(VeicleState.BUSY);
				c.setState(ContractState.ACTIVE);
				contractService.setContract(c);
			} else
				v.setState(VeicleState.FREE);
		}

		/*
		 * per ogni veicolo fa controlli sui ticket di assistenza associati e sui
		 * contratti, leggendone stato e data. modificano di conseguenza lo stato del
		 * veicolo
		 */

	}
}
