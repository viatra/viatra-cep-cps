package hu.karaszi.ec.centralunit.interfaces.management.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import hu.karaszi.ec.centralunit.data.dto.management.MeasurementDTO;

@Path("/measurement")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RestMeasurementManagement {
	@GET
	@Path("/{id}")
	public List<MeasurementDTO> getMeasurement(@PathParam("id") long sensorId);
}