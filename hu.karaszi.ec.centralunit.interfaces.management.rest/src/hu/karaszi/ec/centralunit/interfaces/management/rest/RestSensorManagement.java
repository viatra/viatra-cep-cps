package hu.karaszi.ec.centralunit.interfaces.management.rest;

import java.util.List;

import hu.karaszi.ec.centralunit.interfaces.management.rest.dto.SensorDTO;

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
public interface RestSensorManagement {
	@GET
	public List<SensorDTO> getSensors();
	
	@GET
	@Path("/{id}")
	public SensorDTO getSensor(@PathParam("id") long sensorId);
	
	@POST
	public SensorDTO newSensor(final SensorDTO sensor);
	
	@PUT
	@Path("/{id}")
	public SensorDTO updateSensor(
			@PathParam("id") long sensorId,
			final SensorDTO sensor);
	
	@DELETE
	@Path("/{id}")
	public Response deleteSensor(@PathParam("id") long sensorId);
}
