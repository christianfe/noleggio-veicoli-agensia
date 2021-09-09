package it.univaq.disim.oop.bhertz.business.impl.file;

import java.time.LocalDate;
import java.util.List;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Contract;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;

public class FileVeicleserviceImpl implements VeiclesService {

	@Override
	public Veicle getVeicleByID(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Veicle> getVeiclesByType(Type t) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Veicle> getVeiclesByState(VeicleState state) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Veicle> getVeiclesByStateAndType(Type type, boolean free, boolean busy, boolean maintenance)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVeicle(Integer id, String model, double km, double consuption, String fuel)
			throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addVeicle(Veicle veicle) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteVeicle(Integer id) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVeicle(Veicle veicle) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVeicleFree(LocalDate startDate, LocalDate endDate, List<Contract> contractOfVeicle)
			throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String FindAviableDays(List<Contract> contractOfVeicle) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshAllStates() throws BusinessException {
		// TODO Auto-generated method stub
		
	}}
