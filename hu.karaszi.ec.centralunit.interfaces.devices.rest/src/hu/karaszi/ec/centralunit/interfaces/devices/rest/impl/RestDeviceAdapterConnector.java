package hu.karaszi.ec.centralunit.interfaces.devices.rest.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.osgi.service.component.ComponentContext;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.data.dto.devices.ActuatorStateDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.CommandDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.ThresholdEventDTO;
import hu.karaszi.ec.centralunit.data.persistence.Device;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventForwarder;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventSource;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventType;
import hu.karaszi.ec.centralunit.interfaces.devices.internal.DeviceCommandSender;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.RestDeviceDataReceiver;

public class RestDeviceAdapterConnector implements RestDeviceDataReceiver, DeviceCommandSender {
	private EventForwarder forwarder;
	private DeviceManager deviceManager;

	private Map<String, EventOutput> adapterMap = new HashMap<>();

	public void setEventForwarder(EventForwarder ef) {
		forwarder = ef;
	}

	public void unsetEventForwarder(EventForwarder ef) {
		forwarder = null;
	}

	public void setDeviceManager(DeviceManager dm) {
		deviceManager = dm;
	}

	public void unsetDeviceManager(DeviceManager dm) {
		deviceManager = null;
	}
	
	protected void deactivate(ComponentContext context) {
		adapterMap.values().forEach((eo) -> {
			try {
				eo.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void receiveDeviceHealth(DeviceHealthDTO data) {
		if (forwarder != null) {
			forwarder.sendEvent(data, EventSource.DEVICE, EventType.HEALTH);
		} else {
			// TODO: log missing service
		}
	}

	@Override
	public void receiveThresholdEvent(ThresholdEventDTO data) {
		if (forwarder != null) {
			forwarder.sendEvent(data, EventSource.DEVICE, EventType.THRESHOLD);
		} else {
			// TODO: log missing service
		}
	}

	@Override
	public void receiveMeasurementData(MeasurementDataDTO data) {
		System.out.println("Measurement arrived from: " + data.deviceId);
		if (forwarder != null) {
			forwarder.sendEvent(data, EventSource.DEVICE, EventType.MEASUREMENT);
		} else {
			// TODO: log missing service
		}
	}
	
	@Override
	public void receiveActuatorState(ActuatorStateDTO data) {
		if (forwarder != null) {
			forwarder.sendEvent(data, EventSource.DEVICE, EventType.ACTUATOR_STATE);
		} else {
			// TODO: log missing service
		}
	}

	@Override
	public EventOutput getDeviceCommand(String adapter) {
		final EventOutput eventOutput = new EventOutput();
		adapterMap.put(adapter, eventOutput);
		System.out.println("SSE registered for: " + adapter);
		return eventOutput;
	}

	@Override
	public void readDeviceHealth(String deviceId) {
		sendSimpleCommand(deviceId, "read-health");
	}

	@Override
	public void readSensor(String sensorId) {
		sendSimpleCommand(sensorId, "read-measurement");
	}

	@Override
	public void readActuatorState(String actuatorId) {
		sendSimpleCommand(actuatorId, "read-actuatorstate");
	}

	@Override
	public void turnOnActuator(String actuatorId) {
		sendSimpleCommand(actuatorId, "set-actuatorstate-on");
	}

	@Override
	public void turnOffActuator(String actuatorId) {
		sendSimpleCommand(actuatorId, "set-actuatorstate-off");
	}

	@Override
	public void setActuatorPerformance(String actuatorId, int performance) {
		sendSimpleCommand(actuatorId, "set-performance-" + performance);
	}
	
	private void sendSimpleCommand(String deviceId, String commandString) {
		Device device = deviceManager.getDevice(deviceId);
		EventOutput eventOutput = adapterMap.get(device.getProtocol());
		if (eventOutput != null) {
			try {
				CommandDTO command = new CommandDTO();
				command.deviceId = device.getDeviceId();
				command.address = device.getAddress();
				command.command = commandString;
				final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
				eventBuilder.name("command");
				eventBuilder.mediaType(MediaType.APPLICATION_JSON_TYPE);
				eventBuilder.data(CommandDTO.class, command);
				final OutboundEvent event = eventBuilder.build();
				eventOutput.write(event);
				System.out.println("Command sent: " + commandString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// TODO: log missing adapter/connection
		}
	}
}
