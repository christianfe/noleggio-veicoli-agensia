package it.univaq.disim.oop.bhertz.business.impl.file;

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
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;

public class FileTypesServiceImpl implements TypesService {

	private Map<Integer, Type> types = new HashMap<>();
	private String typesFileName;
	private long counter = 0;

	public FileTypesServiceImpl(String typesFileName) {
		this.typesFileName = typesFileName;
		try {
			this.readList();
		} catch (BusinessException e) {
			ViewDispatcher.getInstance().renderError(e);
		}
	}

	/***********************************************************/

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
		else 
			{
			types.remove(id);
			saveList();
			}
	}


	@Override
	public void addType(Type type) throws BusinessException {
		Integer max = 0;
		for (Type t : types.values())
			max = (max > t.getId())? max : t.getId();
		type.setId(max + 1);
		this.types.put(type.getId(), type);
		saveList();
	}

	@Override
	public void setType(Integer id, String name, double priceForKm, double priceForDay) throws BusinessException {
		System.out.println(types);
		Type t = types.get(id);
		t.setName(name);
		t.setPriceForDay(priceForDay);
		t.setPriceForKm(priceForKm);
		this.types.put(id, t);
		saveList();
	}

	/***********************************************************/

	private void saveList() throws BusinessException {
		FileUtility f = new FileUtility();
		List<String[]> list = new ArrayList<>();
		for (Type t : types.values()) {
			String[] s = new String[4];
			s[0] = t.getId().toString();
			s[1] = t.getName();
			s[2] = t.getPriceForKm() + "";
			s[3] = t.getPriceForDay() + "";
			list.add(s);
		}
		f.setAllByFile(this.typesFileName, new FileData(this.counter, list));
	}

	private void readList() throws BusinessException {
		FileUtility f = new FileUtility();
		FileData fileData = f.getAllByFile(typesFileName);
		this.types = new HashMap<Integer, Type>();
		for (String[] row : fileData.getRows()) {
			Type type = new Type();
			type.setId(Integer.parseInt(row[0]));
			type.setName(row[1]);
			type.setPriceForKm(Double.parseDouble(row[2]));
			type.setPriceForDay(Double.parseDouble(row[3]));
			this.counter = fileData.getCount();
			this.types.put(type.getId(), type);
		}
	}

}
