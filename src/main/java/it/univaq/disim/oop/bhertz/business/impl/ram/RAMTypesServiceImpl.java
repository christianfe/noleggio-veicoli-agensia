package it.univaq.disim.oop.bhertz.business.impl.ram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.domain.Type;

public class RAMTypesServiceImpl implements TypesService{

	private Map<Integer, Type> Types = new HashMap<>();
	
	public  RAMTypesServiceImpl() {
		Type auto = new Type();
		auto.setName("Automobili");
		auto.setId(1);
		auto.setPriceForDay(50);
		auto.setPriceForKm(0.31);
		Types.put(auto.getId(), auto);
		
		Type moto = new Type();
		moto.setName("Moto");
		moto.setId(2);
		moto.setPriceForDay(20);
		moto.setPriceForKm(0.25);
		Types.put(moto.getId(), moto);
		
		
	}
	
	@Override
	public List<Type> getAllTypes() throws BusinessException {
		return new ArrayList<>(Types.values());
	}

	@Override
	public Type getTypeByID(int id) throws BusinessException {
		return Types.get(id);
	}

}
