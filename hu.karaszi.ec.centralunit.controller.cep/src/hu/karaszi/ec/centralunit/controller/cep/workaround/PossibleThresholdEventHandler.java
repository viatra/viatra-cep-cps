package hu.karaszi.ec.centralunit.controller.cep.workaround;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hu.karaszi.ec.centralunit.controller.cep.EventHandlerBase;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.CepFactory;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.AtomicStableHighCriticalMeasurement_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.AtomicStableHighFatalMeasurement_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.AtomicStableLowCriticalMeasurement_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.AtomicStableLowFatalMeasurement_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.AtomicStableNormalMeasurement_Event;

public class PossibleThresholdEventHandler extends EventHandlerBase {
	private static final long STABLE_TIME = 9000;
	private static Map<String, Long> sensorLastEnterEvent = new HashMap<>();

	public static void handleSensorEntersThreshold(String sensorId) {
		sensorLastEnterEvent.put(sensorId, new Date().getTime());
	}
	
	private static boolean isStable(String id) {
		long lastEnter = sensorLastEnterEvent.get(id);
		if(new Date().getTime() - lastEnter > STABLE_TIME)
			return true;
		return false;
	}
	
	public static void handleStableLowFatal(String sensorId) {
		if (isStable(sensorId)) {
			AtomicStableLowFatalMeasurement_Event stableEvent = CepFactory.getInstance().createAtomicStableLowFatalMeasurement_Event();
			stableEvent.setId(sensorId);
			eventstream.push(stableEvent);
		}
	}
	
	public static void handleStableLowCritical(String sensorId) {
		if (isStable(sensorId)) {
			AtomicStableLowCriticalMeasurement_Event stableEvent = CepFactory.getInstance().createAtomicStableLowCriticalMeasurement_Event();
			stableEvent.setId(sensorId);
			eventstream.push(stableEvent);
		}
	}
	
	public static void handleStableNormal(String sensorId) {
		if (isStable(sensorId)) {
			AtomicStableNormalMeasurement_Event stableEvent = CepFactory.getInstance().createAtomicStableNormalMeasurement_Event();
			stableEvent.setId(sensorId);
			eventstream.push(stableEvent);
		}
	}
	
	public static void handleStableHighCritical(String sensorId) {
		if (isStable(sensorId)) {
			AtomicStableHighCriticalMeasurement_Event stableEvent = CepFactory.getInstance().createAtomicStableHighCriticalMeasurement_Event();
			stableEvent.setId(sensorId);
			eventstream.push(stableEvent);
		}
	}
	
	public static void handleStableHighFatal(String sensorId) {
		if (isStable(sensorId)) {
			AtomicStableHighFatalMeasurement_Event stableEvent = CepFactory.getInstance().createAtomicStableHighFatalMeasurement_Event();
			stableEvent.setId(sensorId);
			eventstream.push(stableEvent);
		}
	}
}
