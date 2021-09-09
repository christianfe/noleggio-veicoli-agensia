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

public class FileTypesServiceImpl implements TypesService {

	private String typesFileName;
	private long counter = 0;

	public FileTypesServiceImpl(String typesFileName) {
		this.typesFileName = typesFileName;
	}

	@Override
	public List<Type> getAllTypes() throws BusinessException {
		Map<Integer, Type> types = this.readList();
		return new ArrayList<>(types.values());

	}

	@Override
	public Type getTypeByID(int id) throws BusinessException{
		Map<Integer, Type> types = this.readList();
		return types.get(id);
	}

	@Override
	public void deleteType(Integer id) throws BusinessException, TypeNotEmptyException{
		Map<Integer, Type> types = this.readList();
		VeiclesService veiclesService = BhertzBusinessFactory.getInstance().getVeiclesService();
		Type t = types.get(id);
		if (!veiclesService.getVeiclesByType(t).isEmpty()) throw new TypeNotEmptyException();
		else 
			types.remove(id);
		saveList(types);
	}


	@Override
	public void addType(Type type) throws BusinessException {
		Map<Integer, Type> types = this.readList();
		type.setId((int) counter++);
		types.put(type.getId(), type);
		saveList(types);
	}

	@Override
	public void setType(Integer id, String name, double priceForKm, double priceForDay) throws BusinessException {
		Map<Integer, Type> types = this.readList();
		System.out.println(types);
		Type t = types.get(id);
		t.setName(name);
		t.setPriceForDay(priceForDay);
		t.setPriceForKm(priceForKm);
		types.put(id, t);
		saveList(types);
	}

	private void saveList(Map<Integer, Type> types ) throws BusinessException {
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

	private Map<Integer, Type> readList() throws BusinessException {
		FileUtility f = new FileUtility();
		FileData fileData = f.getAllByFile(typesFileName);
		Map<Integer, Type> types = new HashMap<Integer, Type>();
		for (String[] row : fileData.getRows()) {
			Type type = new Type();
			type.setId(Integer.parseInt(row[0]));
			type.setName(row[1]);
			type.setPriceForKm(Double.parseDouble(row[2]));
			type.setPriceForDay(Double.parseDouble(row[3]));
			this.counter = fileData.getCount();
			types.put(type.getId(), type);
		}
		return types;
	}

}
