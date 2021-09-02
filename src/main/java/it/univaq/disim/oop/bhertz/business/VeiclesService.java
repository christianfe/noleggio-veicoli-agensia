package it.univaq.disim.oop.bhertz.business;

import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;

public interface VeiclesService {

	Veicle getVeicleByID(int id) throws BusinessException;

	List<Veicle> getVeiclesByType(Type t) throws BusinessException;
	
	List<Veicle> getVeiclesByState(VeicleState state) throws BusinessException;
	
	void setVeicle(Veicle veicle) throws BusinessException;
	
}
