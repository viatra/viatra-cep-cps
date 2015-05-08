package hu.karaszi.ec.centralunit.event.processor.api;

import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.Measurement;
import hu.karaszi.ec.centralunit.data.Sensor;

public interface EventProcessor {
	public void processDeviceStatus(Device device);
	public void processThresholdEvent(Sensor sensor);
	public void processMeasurementData(Measurement measurement);
	
	public void processSensorDeleteEvent(long id);
	public void processSensorUpdateEvent(Sensor sensor);
	public void processSensorInsertEvent(Sensor sensor);
	
	public void processActuatorDeleteEvent(long id);
	public void processActuatorUpdateEvent(Actuator actuator);
	public void processActuatorInsertEvent(Actuator actuator);
	
	
}
