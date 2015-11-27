package hu.karaszi.ec.testadapter.rest.proxy;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import hu.karaszi.ec.centralunit.data.dto.devices.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.ThresholdEventDTO;

@Path("/device")
public interface RestDeviceDataReceiver {
	@POST
	@Consumes({"application/json"})
	@Path("/operationalstatus")
	public void receiveOperationalStatus(final DeviceHealthDTO data);
	
	@POST
	@Consumes({"application/json"})
	@Path("/thresholdevent/")
	public void receiveThresholdEvent(final ThresholdEventDTO data);
	
	@POST
	@Consumes({"application/json"})
	@Path("/measurementdata/")
	public void receiveMeasurementData(final MeasurementDataDTO data);
}
