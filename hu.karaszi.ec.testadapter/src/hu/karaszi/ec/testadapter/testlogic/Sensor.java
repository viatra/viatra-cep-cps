package hu.karaszi.ec.testadapter.testlogic;

import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.data.dto.management.SensorDTO;

public interface Sensor {
	public MeasurementDataDTO getNextMeasurement();
	public String getCurrentThreshold();
	public DeviceHealthDTO getDeviceHealth();
	public SensorDTO getDTO();
	public String getId();
	public String getAddress();
}
