package hu.karaszi.ec.centralunit.interfaces.devices.rest;

import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.MeasurementDataBean;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.OperationalStatusBean;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.ThresholdEventBean;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/device")
public interface RestDeviceDataReceiver {
	@POST
	@Consumes({"application/json"})
	@Path("/operationalstatus")
	public void receiveOperationalStatus(final OperationalStatusBean data);
	
	@POST
	@Consumes({"application/json"})
	@Path("/thresholdevent/")
	public void receiveThresholdEvent(final ThresholdEventBean data);
	
	@POST
	@Consumes({"application/json"})
	@Path("/measurementdata/")
	public void receiveMeasurementData(final MeasurementDataBean data);
}
