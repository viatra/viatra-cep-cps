package hu.karaszi.ec.centralunit.interfaces.devices.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;

import hu.karaszi.ec.centralunit.data.dto.devices.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.ActuatorStateDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.ThresholdEventDTO;

@Path("/device")
public interface RestDeviceDataReceiver {
	@POST
	@Consumes({"application/json"})
	@Path("/operationalstatus")
	public void receiveDeviceHealth(final DeviceHealthDTO data);
	
	@POST
	@Consumes({"application/json"})
	@Path("/thresholdevent/")
	public void receiveThresholdEvent(final ThresholdEventDTO data);
	
	@POST
	@Consumes({"application/json"})
	@Path("/measurementdata/")
	public void receiveMeasurementData(final MeasurementDataDTO data);
	
	@POST
	@Consumes({"application/json"})
	@Path("/actuatorstate/")
	public void receiveActuatorState(final ActuatorStateDTO data);
	
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	@Path("/command/{adapter}")
	public EventOutput getDeviceCommand(@PathParam(value = "adapter") String adapter); 
}
