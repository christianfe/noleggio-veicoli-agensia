package it.univaq.disim.oop.bhertz.business.impl.file;

import java.util.List;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.VeiclesService;
import it.univaq.disim.oop.bhertz.domain.Type;
import it.univaq.disim.oop.bhertz.domain.Veicle;
import it.univaq.disim.oop.bhertz.domain.VeicleState;

public class FileVeicleserviceImpl implements VeiclesService {

	@Override
	public Veicle getVeicleByID(int id) {
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
	public void setVeicle(Integer id, Integer typeId, String model, String plate, VeicleState veicleState, int km,
			double consuption, String fuel
			) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addVeicle(Veicle veicle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteVeicle(Integer id) {
		// TODO Auto-generated method stub
		
	}


}
