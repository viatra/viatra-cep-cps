package hu.karaszi.ec.centralunit.controller.cep;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.viatra.cep.core.streams.EventStream;

import hu.karaszi.ec.centralunit.event.forwarder.api.EventForwarder;
import hu.karaszi.ec.centralunit.interfaces.devices.internal.DeviceCommandSender;
import systemmodel.Actuator;
import systemmodel.Sensor;

public abstract class EventHandlerBase {
	protected static DeviceCommandSender dcSender;
	protected static EventStream eventstream;
	protected static EventForwarder eventForwarder;
	
	protected static Map<String, Sensor> sensors = new HashMap<>();
	protected static Map<String, Actuator> actuators = new HashMap<>();
	
	public static void setEventstream(EventStream eventstream) {
		EventHandlerBase.eventstream = eventstream;
	}
	
	public static void setDcSender(DeviceCommandSender dcSender) {
		EventHandlerBase.dcSender = dcSender;
	}
	
	public static void setEventForwarder(EventForwarder eventForwarder) {
		EventHandlerBase.eventForwarder = eventForwarder;
	}
	
	public static void setActuators(Map<String, Actuator> actuators) {
		EventHandlerBase.actuators = actuators;
	}
	
	public static void setSensors(Map<String, Sensor> sensors) {
		EventHandlerBase.sensors = sensors;
	}
}
