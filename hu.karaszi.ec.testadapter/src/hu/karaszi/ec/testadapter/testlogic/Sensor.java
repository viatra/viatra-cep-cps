package hu.karaszi.ec.testadapter.testlogic;

import hu.karaszi.ec.testadapter.dto.data.MeasurementDataDTO;
import hu.karaszi.ec.testadapter.dto.management.SensorDTO;

public interface Sensor {
	public MeasurementDataDTO getNextMeasurement();
	public String getCurrentThreshold();
	public String getOperationalStatus();
	public SensorDTO getDTO();
	public void setId(long id);
}
