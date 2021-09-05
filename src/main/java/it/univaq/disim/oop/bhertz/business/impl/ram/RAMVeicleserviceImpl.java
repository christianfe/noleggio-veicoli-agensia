package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.ContractService;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;

public class RAMVeicleserviceImpl implements VeiclesService {

	private Map<Integer, Veicle> veicles = new HashMap<>();
	private TypesService typeService;


	public RAMVeicleserviceImpl() throws BusinessException {
		this.typeService = new RAMTypesServiceImpl();

		Type auto = typeService.getTypeByID(1);
		Type moto = typeService.getTypeByID(2);

		Veicle panda = new Veicle();
		panda.setId(1);
		panda.setModel("Fiat Panda");
		panda.setKm(10);
		panda.setPlate("AA000AA");
		panda.setState(VeicleState.FREE);
		panda.setConsumption(12);
		panda.setType(auto);
		panda.setFuel("gpl");
		panda.setPriceForDay(panda.getType().getPriceForDay());
		panda.setPriceForKm(panda.getType().getPriceForKm());
		Veicle punto = new Veicle();
		punto.setId(2);
		punto.setModel("Fiat Punto");
		punto.setKm(16154);
		punto.setPlate("AA000AA");
		punto.setState(VeicleState.FREE);
		punto.setConsumption(12);
		punto.setType(auto);
		punto.setFuel("gasolio");
		punto.setPriceForDay(punto.getType().getPriceForDay());
		punto.setPriceForKm(punto.getType().getPriceForKm());
		Veicle fiesta = new Veicle();
		fiesta.setId(3);
		fiesta.setModel("Ford Fiesta");
		fiesta.setKm(31515);
		fiesta.setPlate("AA000AA");
		fiesta.setState(VeicleState.BUSY);
		fiesta.setConsumption(12);
		fiesta.setType(auto);
		fiesta.setFuel("gpl");
		fiesta.setPriceForDay(fiesta.getType().getPriceForDay());
		fiesta.setPriceForKm(fiesta.getType().getPriceForKm());
		Veicle malaguti = new Veicle();
		malaguti.setId(4);
		malaguti.setModel("Vasca da Bagno");
		malaguti.setKm(1354);
		malaguti.setPlate("AA000AA");
		malaguti.setState(VeicleState.FREE);
		malaguti.setConsumption(12);
		malaguti.setType(moto);
		malaguti.setFuel("benzina");
		malaguti.setPriceForDay(malaguti.getType().getPriceForDay());
		malaguti.setPriceForKm(malaguti.getType().getPriceForKm());
		Veicle aprilia = new Veicle();
		aprilia.setId(5);
		aprilia.setModel("Aprilia RS");
		aprilia.setKm(135);
		aprilia.setPlate("AA000AA");
		aprilia.setState(VeicleState.MAINTENANCE);
		aprilia.setConsumption(12);
		aprilia.setFuel("bestemmie");
		aprilia.setType(moto);
		aprilia.setPriceForDay(aprilia.getType().getPriceForDay());
		aprilia.setPriceForKm(aprilia.getType().getPriceForKm());

		veicles.put(panda.getId(), panda);
		veicles.put(punto.getId(), punto);
		veicles.put(fiesta.getId(), fiesta);
		veicles.put(malaguti.getId(), malaguti);
		veicles.put(aprilia.getId(), aprilia);
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
	public Veicle getVeicleByID(int id) {
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
	public void addVeicle(Veicle veicle) {
		Integer max = 0;
		for (Veicle v : veicles.values())
			max = (max > v.getId()) ? max : v.getId();
		veicle.setId(max + 1);
		this.veicles.put(veicle.getId(), veicle);
	}

	@Override
	public void deleteVeicle(Integer id) {
		veicles.remove(id);
	}

	@Override
	public void setVeicle(Integer id, String model, int km, double consuption, String fuel) {
		Veicle v = veicles.get(id);
		v.setModel(model);
		v.setConsumption(consuption);
		v.setKm(km);
		v.setFuel(fuel);
		this.veicles.put(id, v);
	}

	@Override
	public boolean isVeicleFree(Integer idVeicle, LocalDate startDate, LocalDate endDate) {
		/*List<Contract> contractOfVeicle = contractService.getContractsByVeicle(idVeicle);
		for (Contract c : contractOfVeicle)
			if (true)
				return false;
		return true;*/
		return false;
	}

}
