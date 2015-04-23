package hu.karaszi.ec.centralunit.dal;

import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.Sensor;

public interface DeviceManager {
	public Device getDevice(String name);
	public Sensor getSensor(long id);
	public Actuator getActuator(long id);
	public void updateSensor(Sensor sensor);
	public void updateActuator(Actuator actuator);
}
