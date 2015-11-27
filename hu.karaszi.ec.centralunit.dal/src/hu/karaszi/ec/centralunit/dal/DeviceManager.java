package hu.karaszi.ec.centralunit.dal;

import java.util.List;

import hu.karaszi.ec.centralunit.data.persistence.Actuator;
import hu.karaszi.ec.centralunit.data.persistence.Device;
import hu.karaszi.ec.centralunit.data.persistence.Sensor;

public interface DeviceManager {
	public Device getDevice(String deviceId);
	public Device updateDevice(Device device);
	
	public Sensor getSensor(long id);
	public Sensor getSensor(String deviceId);
	public List<Sensor> getSensors();
	public Sensor insertSensor(Sensor sensor);
	public Sensor updateSensor(Sensor sensor);
	public void deleteSensor(long id);
	public void deleteSensor(String deviceId);
	
	public Actuator getActuator(long id);
	public Actuator getActuator(String deviceId);
	public List<Actuator> getActuators();
	public Actuator insertActuator(Actuator actuator);
	public Actuator updateActuator(Actuator actuator);
	public void deleteActuator(long id);
	public void deleteActuator(String deviceId);

}
