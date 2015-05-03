package hu.karaszi.ec.centralunit.management.rest.impl;

import hu.karaszi.ec.centralunit.dal.UnitManager;
import hu.karaszi.ec.centralunit.data.Unit;
import hu.karaszi.ec.centralunit.management.rest.RestUnitManagement;
import hu.karaszi.ec.centralunit.management.rest.dto.UnitDTO;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

public class RestUnitManagementImpl implements RestUnitManagement {
	private UnitManager unitManager;

	public void setUnitManager(UnitManager um) {
		unitManager = um;
	}

	public void unsetUnitManager(UnitManager um) {
		unitManager = null;
	}
	
	@Override
	public List<UnitDTO> getUnits() {
		List<UnitDTO> unitDTOList = new ArrayList<UnitDTO>();
		for (Unit unit : unitManager.getUnits()) {
			unitDTOList.add(unitToDTO(unit));
		} 
		return unitDTOList;
	}

	@Override
	public UnitDTO getUnit(String unitId) {
		Unit unit = unitManager.getUnit(Long.parseLong(unitId));
		return unitToDTO(unit);
	}

	@Override
	public Response newUnit(UnitDTO unit) {
		Unit newUnit = DTOToUnit(unit);
		unitManager.insertUnit(newUnit);
		//TODO
		return null;
	}

	@Override
	public Response updateUnit(String unitId, UnitDTO unit) {
		Unit newUnit = DTOToUnit(unit);
		unitManager.updateUnit(newUnit);
		return Response.ok().build();
	}

	@Override
	public Response deleteUnit(String unitId) {
		unitManager.deleteUnit(Long.parseLong(unitId));
		return Response.ok().build();
	}

	private UnitDTO unitToDTO(Unit unit){
		UnitDTO dto = new UnitDTO();
		dto.id = unit.getId();
		dto.name = unit.getName();
		dto.unit = unit.getUnit();
		return dto;
	}
	
	private Unit DTOToUnit(UnitDTO dto){
		Unit unit = new Unit();
		unit.setId(dto.id);
		unit.setName(dto.name);
		unit.setUnit(dto.unit);		
		return unit;
	}
}
