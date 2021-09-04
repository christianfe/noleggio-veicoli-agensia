package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BhertzBusinessFactory;
import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.TypeNotEmptyException;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Type;

public class RAMTypesServiceImpl implements TypesService{

	private Map<Integer, Type> types = new HashMap<>();
	
	public  RAMTypesServiceImpl() {
		Type auto = new Type();
		auto.setName("Automobili");
		auto.setId(1);
		auto.setPriceForDay(50);
		auto.setPriceForKm(0.31);
		types.put(auto.getId(), auto);
		
		Type moto = new Type();
		moto.setName("Moto");
		moto.setId(2);
		moto.setPriceForDay(21);
		moto.setPriceForKm(0.25);
		types.put(moto.getId(), moto);
	}
	
	@Override
	public List<Type> getAllTypes() throws BusinessException {
		return new ArrayList<>(types.values());
		
	}

	@Override
	public Type getTypeByID(int id){
		return types.get(id);
	}

	@Override
	public void deleteType(Integer id) throws BusinessException, TypeNotEmptyException{
		VeiclesService veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
		Type t = types.get(id);
		if (!veiclesService.getVeiclesByType(t).isEmpty()) throw new TypeNotEmptyException();
		else types.remove(id);
	}


	@Override
	public void addType(Type type) {
		System.out.println(types);
		Integer max = 0;
		for (Type t : types.values())
			max = (max > t.getId())? max : t.getId();
		type.setId(max + 1);
		this.types.put(type.getId(), type);
		System.out.println(types);
	}

	@Override
	public void setType(Integer id, String name, double priceForKm, double priceForDay) {
		System.out.println(types);
		Type t = types.get(id);
		t.setName(name);
		t.setPriceForDay(priceForDay);
		t.setPriceForKm(priceForKm);
		this.types.put(id, t);
		System.out.println(types);
	}
}
