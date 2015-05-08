package hu.karaszi.ec.testadapter.testlogic;

import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.interfaces.management.rest.dto.SensorDTO;

public interface Sensor {
	public MeasurementDataDTO getNextMeasurement();
	public String getCurrentThreshold();
	public String getOperationalStatus();
	public SensorDTO getDTO();
	public void setId(long id);
	public long getId();
}
