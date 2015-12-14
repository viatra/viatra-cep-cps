package hu.karaszi.ec.piadapter.centralunitproxy;

import java.util.List;

import javax.ws.rs.core.Response;

import hu.karaszi.ec.piadapter.centralunitproxy.dto.UnitDTO;

public interface RestUnitManagement {
	public List<UnitDTO> getUnits();
	public UnitDTO getUnit(String unitId);
	public Response newUnit(final UnitDTO unit);
	public Response updateUnit(String unitId, final UnitDTO unit);
	public Response deleteUnit(String unitId);
}