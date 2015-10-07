package hu.karaszi.ec.centralunit.interfaces.management.rest;

import hu.karaszi.ec.centralunit.interfaces.management.rest.dto.UnitDTO;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/unit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RestUnitManagement {
	@GET
	public List<UnitDTO> getUnits();
	
	@GET
	@Path("/{id}")
	public UnitDTO getUnit(@PathParam("id") String unitId);
	
	@POST
	public UnitDTO newUnit(final UnitDTO unit);
	
	@PUT
	@Path("/{id}")
	public Response updateUnit(
			@PathParam("id") String unitId,
			final UnitDTO unit);
	
	@DELETE
	@Path("/{id}")
	public Response deleteUnit(@PathParam("id") String unitId);
}