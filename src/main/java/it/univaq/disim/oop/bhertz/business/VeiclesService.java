package it.univaq.disim.oop.bhertz.business;

import java.time.LocalDate;
import java.util.List;

import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;

public interface VeiclesService {

	Veicle getVeicleByID(int id) throws BusinessException;

	List<Veicle> getVeiclesByType(Type t) throws BusinessException;

	List<Veicle> getVeiclesByState(VeicleState state) throws BusinessException;
	
	public List<Veicle> getVeiclesByStateAndType(Type type, boolean free, boolean busy, boolean maintenance) throws BusinessException;

	void setVeicle(Integer id, String model, double km, double consuption, String fuel) throws BusinessException;

	void addVeicle(Veicle veicle) throws BusinessException;

	void deleteVeicle(Integer id) throws BusinessException;

	void setVeicle(Veicle veicle) throws BusinessException;

	boolean isVeicleFree(LocalDate startDate, LocalDate endDate, List<Contract> contractOfVeicle) throws BusinessException;

	public String FindAviableDays(List<Contract> contractOfVeicle) throws BusinessException;

	void refreshAllStates() throws BusinessException;

	void updatePrices(double oldPriceForDay, double oldPriceForKm, double newPriceForDay, double newPriceForKm) throws BusinessException;
}
