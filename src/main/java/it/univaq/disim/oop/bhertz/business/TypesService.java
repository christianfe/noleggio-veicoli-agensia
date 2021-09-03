package it.univaq.disim.oop.bhertz.business;

import java.util.List;
import it.univaq.disim.oop.bhertz.domain.Type;

public interface TypesService {

	List<Type> getAllTypes() throws BusinessException;

	Type getTypeByID(int id);
	
	void deleteType(Integer id) throws TypeNotEmptyException;
	
	void setType(Type type);

}

