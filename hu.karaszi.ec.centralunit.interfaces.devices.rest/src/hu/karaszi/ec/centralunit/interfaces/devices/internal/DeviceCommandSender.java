package hu.karaszi.ec.centralunit.interfaces.devices.internal;

public interface DeviceCommandSender {
	public void readDeviceHealth(String deviceId);
	
	public void readSensor(String sensorId);
	
	public void readActuatorState(String actuatorId);
	public void turnOnActuator(String actuatorId);
	public void turnOffActuator(String actuatorId);
	public void setActuatorPerformance(String actuatorId, int performance);
}
