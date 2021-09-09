package it.univaq.disim.oop.bhertz.business.impl.file;

import java.util.List;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.business.TypeNotEmptyException;
import it.univaq.disim.oop.bhertz.business.TypesService;
import it.univaq.disim.oop.bhertz.domain.Type;

public class FileTypesServiceImpl implements TypesService {

	@Override
	public List<Type> getAllTypes() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getTypeByID(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteType(Integer id) throws TypeNotEmptyException, BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setType(Integer id, String name, double priceForKm, double priceForDay) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addType(Type type) throws BusinessException {
		// TODO Auto-generated method stub
		
	}}
