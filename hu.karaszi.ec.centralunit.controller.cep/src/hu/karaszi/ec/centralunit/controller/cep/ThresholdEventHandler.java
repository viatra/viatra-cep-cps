package hu.karaszi.ec.centralunit.controller.cep;

import hu.karaszi.ec.centralunit.event.forwarder.api.EventSource;
import systemmodel.Actuator;
import systemmodel.ActuatorEffect;
import systemmodel.ActuatorState;
import systemmodel.Sensor;

public class ThresholdEventHandler extends EventHandlerBase {

	private static final int PERFORMANCE_STEP = 10;

	public static void handleStableLowFatal(String sensorId) {
		sendTrace("TRACE.SLF-DETECTED", sensorId);
		if (tryIncreaseValue(sensorId) == false) {
			//TODO: log error unsuccesful
			sendTrace("TRACE.SLF-HANDLING_FAILED", sensorId);
		}
	}
	
	public static void handleStableLowCritical(String sensorId) {
		sendTrace("TRACE.SLC-DETECTED", sensorId);
		if (tryIncreaseValue(sensorId) == false) {
			//TODO: log error unsuccesful
			sendTrace("TRACE.SLC-HANDLING_FAILED", sensorId);
		}
	}
	
	public static void handleStableNormal(String sensorId) {
		sendTrace("TRACE.SLN-DETECTED", sensorId);
	}
	
	public static void handleStableHighCritical(String sensorId) {
		sendTrace("TRACE.SHC-DETECTED", sensorId);
		if (tryDecreaseValue(sensorId) == false) {
			//TODO: log error unsuccesful
			sendTrace("TRACE.SHC-HANDLING_FAILED", sensorId);
		}
	}
	
	public static void handleStableHighFatal(String sensorId) {
		sendTrace("TRACE.SHF-DETECTED", sensorId);
		if (tryDecreaseValue(sensorId) == false) {
			//TODO: log error unsuccesful
			sendTrace("TRACE.SHF-HANDLING_FAILED", sensorId);
		}
	}
	
	public static void handleRapidIncrease(String sensorId) {
		sendTrace("TRACE.RI-DETECTED", sensorId);
		if (tryDecreaseValue(sensorId) == false) {
			//TODO: log error unsuccesful
			sendTrace("TRACE.RI-HANDLING_FAILED", sensorId);
		}
	}
	
	public static void handleRapidFatalIncrease(String sensorId) {
		sendTrace("TRACE.RFI-DETECTED", sensorId);
		if (tryDecreaseValue(sensorId) == false) {
			//TODO: log error unsuccesful
			sendTrace("TRACE.RFI-HANDLING_FAILED", sensorId);
		}
	}
	
	public static void handleRapidDecrease(String sensorId) {
		sendTrace("TRACE.RD-DETECTED", sensorId);
		if (tryIncreaseValue(sensorId) == false) {
			//TODO: log error unsuccesful
			sendTrace("TRACE.RD-HANDLING_FAILED", sensorId);
		}
	}
	
	public static void handleRapidFatalDecrease(String sensorId) {
		sendTrace("TRACE.RFD-DETECTED", sensorId);
		if (tryIncreaseValue(sensorId) == false) {
			//TODO: log error unsuccesful
			sendTrace("TRACE.RFD-HANDLING_FAILED", sensorId);
		}
	}
	
	private static void sendTrace(String event, String id){
		System.out.println(event + ": " + id);
//		eventForwarder.sendEvent(id, EventSource.INTERNAL, event);
	}
	
	private static boolean tryDecreaseValue(String sensorId){
		Sensor sensor = sensors.get(sensorId);
		Actuator actuator = getActuatorToDecrease(sensor, ActuatorEffect.POSITIVE);
		if (actuator != null) {
			int newPerformance = actuator.getPerformance() - PERFORMANCE_STEP;
			if (newPerformance > 0) {
				dcSender.setActuatorPerformance(actuator.getDeviceId(), newPerformance);
				return true;
			} else {
				dcSender.turnOffActuator(actuator.getDeviceId());
				return true;
			}			
		} else {
			actuator = getActuatorToTurnOff(sensor, ActuatorEffect.POSITIVE);
			if (actuator != null) {
				dcSender.turnOffActuator(actuator.getDeviceId());
				return true;
			} else {
				actuator = getActuatorToIncrease(sensor, ActuatorEffect.NEGATIVE);
				if (actuator != null) {
					int newPerformance = actuator.getPerformance() + PERFORMANCE_STEP;
					dcSender.setActuatorPerformance(actuator.getDeviceId(), newPerformance < 100 ? newPerformance : 100);
					return true;
				} else {
					actuator = getActuatorToTurnOn(sensor, ActuatorEffect.NEGATIVE);
					if (actuator != null) {
						dcSender.turnOnActuator(actuator.getDeviceId());
						return true;
					} else {
						return false;
					}
				}
			}
		}
	}
	
	private static boolean tryIncreaseValue(String sensorId){
		Sensor sensor = sensors.get(sensorId);
		Actuator actuator = getActuatorToDecrease(sensor, ActuatorEffect.NEGATIVE);
		if (actuator != null) {
			int newPerformance = actuator.getPerformance() - PERFORMANCE_STEP;
			if (newPerformance > 0) {
				dcSender.setActuatorPerformance(actuator.getDeviceId(), newPerformance);
				return true;
			} else {
				dcSender.turnOffActuator(actuator.getDeviceId());
				return true;
			}			
		} else {
			actuator = getActuatorToTurnOff(sensor, ActuatorEffect.NEGATIVE);
			if (actuator != null) {
				dcSender.turnOffActuator(actuator.getDeviceId());
				return true;
			} else {
				actuator = getActuatorToIncrease(sensor, ActuatorEffect.POSITIVE);
				if (actuator != null) {
					int newPerformance = actuator.getPerformance() + PERFORMANCE_STEP;
					dcSender.setActuatorPerformance(actuator.getDeviceId(), newPerformance < 100 ? newPerformance : 100);
					return true;
				} else {
					actuator = getActuatorToTurnOn(sensor, ActuatorEffect.POSITIVE);
					if (actuator != null) {
						dcSender.turnOnActuator(actuator.getDeviceId());
						return true;
					} else {
						return false;
					}
				}
			}
		}
	}
	
	private static Actuator getActuatorToDecrease(Sensor sensor, ActuatorEffect effect){
		for (Actuator actuator : sensor.getAffectedBy()) {
			if (actuator.getEffect().equals(effect)) {
				if (actuator.getState().equals(ActuatorState.ON)) {
					if (actuator.getPerformance() > 0) {
						return actuator;
					}
				}
			}
		}
		return null;
	}
	
	private static Actuator getActuatorToTurnOff(Sensor sensor, ActuatorEffect effect){
		for (Actuator actuator : sensor.getAffectedBy()) {
			if (actuator.getEffect().equals(effect)) {
				if (actuator.getState().equals(ActuatorState.ON)) {
					return actuator;
				}
			}
		}
		return null;
	}

	private static Actuator getActuatorToIncrease(Sensor sensor, ActuatorEffect effect){
		for (Actuator actuator : sensor.getAffectedBy()) {
			if (actuator.getEffect().equals(effect)) {
				if (actuator.getState().equals(ActuatorState.ON)) {
					if (actuator.getPerformance() < 100) {
						return actuator;
					}
				}
			}
		}
		return null;		
	}

	private static Actuator getActuatorToTurnOn(Sensor sensor, ActuatorEffect effect){
		for (Actuator actuator : sensor.getAffectedBy()) {
			if (actuator.getEffect().equals(effect)) {
				if (actuator.getState().equals(ActuatorState.OFF)) {
						return actuator;
				}
			}
		}
		return null;		
	}
}
