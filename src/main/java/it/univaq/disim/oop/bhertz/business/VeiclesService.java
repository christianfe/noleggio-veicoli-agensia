package it.univaq.disim.oop.bhertz.business;

import java.time.LocalDate;
import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;

public interface VeiclesService {

	Veicle getVeicleByID(int id);

	List<Veicle> getVeiclesByType(Type t) throws BusinessException;

	List<Veicle> getVeiclesByState(VeicleState state) throws BusinessException;

	void setVeicle(Integer id, String model, double km, double consuption, String fuel);

	void addVeicle(Veicle veicle);

	void deleteVeicle(Integer id);

	void setVeicle(Veicle veicle);

	boolean isVeicleFree(LocalDate startDate, LocalDate endDate, List<Contract> contractOfVeicle);

}
