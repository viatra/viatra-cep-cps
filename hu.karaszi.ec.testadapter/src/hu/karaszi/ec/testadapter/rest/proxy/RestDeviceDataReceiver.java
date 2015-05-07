package hu.karaszi.ec.testadapter.rest.proxy;

import hu.karaszi.ec.testadapter.dto.data.MeasurementDataDTO;
import hu.karaszi.ec.testadapter.dto.data.OperationalStatusDTO;
import hu.karaszi.ec.testadapter.dto.data.ThresholdEventDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/device")
public interface RestDeviceDataReceiver {
	@POST
	@Consumes({"application/json"})
	@Path("/operationalstatus")
	public void receiveOperationalStatus(final OperationalStatusDTO data);
	
	@POST
	@Consumes({"application/json"})
	@Path("/thresholdevent/")
	public void receiveThresholdEvent(final ThresholdEventDTO data);
	
	@POST
	@Consumes({"application/json"})
	@Path("/measurementdata/")
	public void receiveMeasurementData(final MeasurementDataDTO data);
}
