package hu.karaszi.ec.centralunit.management.rest;

import java.util.List;

import hu.karaszi.ec.centralunit.management.rest.dto.ActuatorDTO;

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

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RestActuatorManagement {
	@GET
	public List<ActuatorDTO> getActuators();
	
	@GET
	@Path("/{id}")
	public ActuatorDTO getActuator(@PathParam("id") String actuatorId);
	
	@POST
	public Response newActuator(final ActuatorDTO actuator);
	
	@PUT
	@Path("/{id}")
	public Response updateActuator(
			@PathParam("id") String actuatorId,
			final ActuatorDTO actuator);
	
	@DELETE
	@Path("/{id}")
	public Response deleteActuator(@PathParam("id") String actuatorId);
}
