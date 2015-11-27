package hu.karaszi.ec.centralunit.controller.cep;

import hu.karaszi.ec.centralunit.controller.cep.eventmodel.CepFactory;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.DeviceHealthReadSent_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.SensorReadSent_Event;

public class ScheduleEventHandler extends EventHandlerBase {
	public static void handleSensorRead(String sensorId) {
		dcSender.readSensor(sensorId);
		SensorReadSent_Event sensorReadSentEvent = CepFactory.getInstance().createSensorReadSent_Event();
		sensorReadSentEvent.setId(sensorId);
		eventstream.push(sensorReadSentEvent);
	}
	
	public static void handleDeviceHealthRead(String deviceId) {
		dcSender.readDeviceHealth(deviceId);
		DeviceHealthReadSent_Event deviceHealthReadSentEvent = CepFactory.getInstance().createDeviceHealthReadSent_Event();
		deviceHealthReadSentEvent.setId(deviceId);
		eventstream.push(deviceHealthReadSentEvent);
	}
}
