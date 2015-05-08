package hu.karaszi.ec.centralunit.event.forwarder.api;

import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Sensor;

public interface ManagementEventForwarder {
	public void forwardSensorInsertEvent(Sensor sensor);
	public void forwardSensorUpdateEvent(Sensor sensor);
	public void forwardSensorDeleteEvent(long id);
	
	public void forwardActuatorInsertEvent(Actuator actuator);
	public void forwardActuatorUpdateEvent(Actuator actuator);
	public void forwardActuatorDeleteEvent(long id);
}
