package hu.karaszi.ec.testadapter.testlogic;

import hu.karaszi.ec.centralunit.data.dto.devices.ActuatorStateDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.management.ActuatorDTO;

public interface Actuator {
	public DeviceHealthDTO getDeviceHealth();
	public ActuatorDTO getDTO();
	public ActuatorStateDTO getStateDTO();
	public String getId();
	public String getAddress();
	public void setState(String state);
	public void setPerformance(int performance);
}
