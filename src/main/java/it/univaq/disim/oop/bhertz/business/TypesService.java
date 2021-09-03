package it.univaq.disim.oop.bhertz.business;

import java.util.List;
import it.univaq.disim.oop.bhertz.domain.Type;

public interface TypesService {

	List<Type> getAllTypes() throws BusinessException;

	Type getTypeByID(int id);
	
	void deleteType(Integer id) throws TypeNotEmptyException, BusinessException;
	
	void setType(Integer id, String name, double priceForKm, double priceForDay);
	
	void addType(Type type);

}

