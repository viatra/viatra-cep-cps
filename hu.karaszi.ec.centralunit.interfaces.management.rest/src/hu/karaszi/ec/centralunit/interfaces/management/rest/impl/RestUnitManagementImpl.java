package hu.karaszi.ec.centralunit.interfaces.management.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import hu.karaszi.ec.centralunit.dal.UnitManager;
import hu.karaszi.ec.centralunit.data.dto.management.UnitDTO;
import hu.karaszi.ec.centralunit.data.persistence.Unit;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventForwarder;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventSource;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventType;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestUnitManagement;

public class RestUnitManagementImpl implements RestUnitManagement {
	private UnitManager unitManager;
	private EventForwarder eventForwarder;

	public void setUnitManager(UnitManager um) {
		unitManager = um;
	}

	public void unsetUnitManager(UnitManager um) {
		unitManager = null;
	}
	
	public void setEventForwarder(EventForwarder mef) {
		eventForwarder = mef;
	}

	public void unsetEventForwarder(EventForwarder mef) {
		eventForwarder = null;
	}
	
	@Override
	public List<UnitDTO> getUnits() {
		List<UnitDTO> unitDTOList = new ArrayList<UnitDTO>();
		for (Unit unit : unitManager.getUnits()) {
			unitDTOList.add(unit.toDTO());
		} 
		return unitDTOList;
	}

	@Override
	public UnitDTO getUnit(String unitName) {
		Unit unit = unitManager.getUnit(unitName);
		return unit.toDTO();
	}

	@Override
	public Response newUnit(UnitDTO unit) {
		eventForwarder.sendEvent(unit, EventSource.MANAGEMENT, EventType.UNIT_INSERT);
		return Response.ok().build();
	}
	
	@Override
	public Response updateUnit(String unitName, UnitDTO unit) {
		eventForwarder.sendEvent(unit, EventSource.MANAGEMENT, EventType.UNIT_UPDATE);
		return Response.ok().build();
	}

	@Override
	public Response deleteUnit(String unitName) {
		eventForwarder.sendEvent(unitName, EventSource.MANAGEMENT, EventType.UNIT_DELETE);
		return Response.ok().build();
	}
}
