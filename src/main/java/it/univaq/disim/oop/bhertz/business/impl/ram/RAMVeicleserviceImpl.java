package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
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
		panda.setKm(1546);
		panda.setPlate("AA000AA");
		panda.setState(VeicleState.FREE);
		panda.setConsumption(12);
		panda.setType(auto);
		panda.setFuel("gpl");
		Veicle punto = new Veicle();
		punto.setId(2);
		punto.setModel("Fiat Punto");
		punto.setKm(16154);
		punto.setPlate("AA000AA");
		punto.setState(VeicleState.FREE);
		punto.setConsumption(12);
		punto.setType(auto);
		punto.setFuel("gasolio");
		Veicle fiesta = new Veicle();
		fiesta.setId(3);
		fiesta.setModel("Ford Fiesta");
		fiesta.setKm(31515);
		fiesta.setPlate("AA000AA");
		fiesta.setState(VeicleState.BUSY);
		fiesta.setConsumption(12);
		fiesta.setType(auto);
		fiesta.setFuel("gpl");
		Veicle malaguti = new Veicle();
		malaguti.setId(4);
		malaguti.setModel("Vasca da Bagno");
		malaguti.setKm(1354);
		malaguti.setPlate("AA000AA");
		malaguti.setState(VeicleState.FREE);
		malaguti.setConsumption(12);
		malaguti.setType(moto);
		malaguti.setFuel("benzina");
		Veicle aprilia = new Veicle();
		aprilia.setId(5);
		aprilia.setModel("Aprilia RS");
		aprilia.setKm(135);
		aprilia.setPlate("AA000AA");
		aprilia.setState(VeicleState.MAINTENANCE);
		aprilia.setConsumption(12);
		aprilia.setFuel("bestemmie");
		aprilia.setType(moto);

		veicles.put(panda.getId(), panda);
		veicles.put(punto.getId(), punto);
		veicles.put(fiesta.getId(), fiesta);
		veicles.put(malaguti.getId(), malaguti);
		veicles.put(aprilia.getId(), aprilia);
	}

	@Override
	public List<Veicle> getVeiclesByType(Type t) throws BusinessException {
		List<Veicle> result = new ArrayList<>();
		for (Veicle v : veicles.values())
			if (v.getType().getId() == t.getId())
				result.add(v);
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
	public void setVeicle(Integer id, Integer typeId, String model, String plate, VeicleState veicleState, int km,
			double consuption) {
		Veicle v = veicles.get(id);
		v.setModel(model);
		v.setConsumption(consuption);
		v.setKm(km);
		v.setPlate(plate);
		v.setType(typeService.getTypeByID(typeId));
		v.setState(veicleState);
		this.veicles.put(id, v);
	}

	@Override
	public void addVeicle(Veicle veicle) {
		Integer max = 0;
		for (Veicle v : veicles.values())
			max = (max > v.getId())? max : v.getId();
		veicle.setId(max + 1);
		this.veicles.put(veicle.getId(), veicle);
	}

	@Override
	public void deleteVeicle(Integer id) {
		veicles.remove(id);
	}

}
