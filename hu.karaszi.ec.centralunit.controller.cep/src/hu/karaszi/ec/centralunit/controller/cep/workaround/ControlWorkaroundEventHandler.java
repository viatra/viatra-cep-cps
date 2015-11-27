package hu.karaszi.ec.centralunit.controller.cep.workaround;

import java.util.HashSet;
import java.util.Set;

import hu.karaszi.ec.centralunit.controller.cep.EventHandlerBase;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.CepFactory;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.AtomicRetryDeviceHealthReadNeeded_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.AtomicRetrySensorReadNeeded_Event;

public class ControlWorkaroundEventHandler extends EventHandlerBase {
	private static Set<String> deviceHealthReadSentEvent = new HashSet<>();
	private static Set<String> sensorReadSentEvent = new HashSet<>();

	public static void handleDeviceHealthReadSent(String deviceId) {
		deviceHealthReadSentEvent.add(deviceId);
	}
	
	public static void handleDeviceHealthArrived(String deviceId) {
		deviceHealthReadSentEvent.remove(deviceId);
	}
	
	public static void handleSensorReadSent(String deviceId) {
		sensorReadSentEvent.add(deviceId);
	}
	
	public static void handleSensorReadArrived(String deviceId) {
		sensorReadSentEvent.remove(deviceId);
	}
	
	private static boolean isDeviceHealthReadTimeout(String id) {
		return deviceHealthReadSentEvent.remove(id);
	}
	
	private static boolean isSensorReadTimeout(String id) {
		return sensorReadSentEvent.remove(id);
	}
	
	public static void handlePossibleDeviceHealthReadTimeout(String deviceId) {
		if (isDeviceHealthReadTimeout(deviceId)) {
			AtomicRetryDeviceHealthReadNeeded_Event retryNeeded = CepFactory.getInstance().createAtomicRetryDeviceHealthReadNeeded_Event();
			retryNeeded.setId(deviceId);
			eventstream.push(retryNeeded);
		}
	}
	
	public static void handlePossibleSensorReadTimeout(String sensorId) {
		if (isSensorReadTimeout(sensorId)) {
			AtomicRetrySensorReadNeeded_Event retryNeeded = CepFactory.getInstance().createAtomicRetrySensorReadNeeded_Event();
			retryNeeded.setId(sensorId);
			eventstream.push(retryNeeded);
		}
	}
}
