package hu.karaszi.ec.centralunit.dal;

import java.util.List;

import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.Sensor;

public interface DeviceManager {
	public Device getDevice(String name);
	
	public Sensor getSensor(long id);
	public Sensor getSensor(String name);
	public List<Sensor> getSensors();
	public Sensor insertSensor(Sensor sensor);
	public void updateSensor(Sensor sensor);
	public void deleteSensor(long id);
	
	public Actuator getActuator(long id);
	public List<Actuator> getActuators();
	public void insertActuator(Actuator actuator);
	public void updateActuator(Actuator actuator);
	public void deleteActuator(long id);
}
