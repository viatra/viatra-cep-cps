package hu.karaszi.ec.testadapter.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import hu.karaszi.ec.centralunit.data.dto.devices.CommandDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.data.dto.management.SensorDTO;
import hu.karaszi.ec.centralunit.data.dto.management.UnitDTO;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.RestDeviceDataReceiver;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestActuatorManagement;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestSensorManagement;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestUnitManagement;
import hu.karaszi.ec.testadapter.testlogic.Actuator;
import hu.karaszi.ec.testadapter.testlogic.IncrementalSensor;
import hu.karaszi.ec.testadapter.testlogic.Sensor;
import hu.karaszi.ec.testadapter.testlogic.SimpleActuator;

public class RestAdapter implements Adapter {
	private static final int NUMBER_OF_SENSORS = 2;
	private Map<String,Sensor> sensors = new HashMap<>();
	private Map<String,Actuator> actuators = new HashMap<>();
	
	private SenderThread senderThread;
	private EventSource commandEventSource;
	// private RestDeviceDataReceiver rddr;
	// private WebTarget dataTarget;
	private RestSensorManagement restSensorManagement;
	private RestActuatorManagement restActuatorManagement;
	private RestUnitManagement restUnitManagement;
	private RestDeviceDataReceiver restDeviceDataReceiver;

	// public void activate(ComponentContext context){
	// //RestSensorManagement rsm =
	// JAXRSClientFactory.create("http://localhost:9090/management/sensor",
	// RestSensorManagement.class);
	// //SensorDTO created = rsm.newSensor(sensor.getDTO());
	//// Client client = ClientBuilder.newClient();
	//// WebTarget target =
	// client.target("http://localhost:8080/management/sensor");
	//// SensorDTO created = target.request(MediaType.APPLICATION_JSON_TYPE)
	//// .post(Entity.entity(sensor.getDTO(), MediaType.APPLICATION_JSON_TYPE),
	// SensorDTO.class);
	//// sensor.setId(created.id);
	//// dataTarget = client.target("http://localhost:8080/receiver/device");
	// //rddr = JAXRSClientFactory.create("http://localhost:9090/receive",
	// RestDeviceDataReceiver.class);
	// }

	public void setRestSensorManagement(RestSensorManagement rsm) {
		restSensorManagement = rsm;
	}

	public void unsetRestSensorManagement(RestSensorManagement rsm) {
		restSensorManagement = null;
	}
	
	public void setRestActuatorManagement(RestActuatorManagement ram) {
		restActuatorManagement = ram;
	}

	public void unsetRestActuatorManagement(RestActuatorManagement ram) {
		restActuatorManagement = null;
	}

	public void setRestUnitManagement(RestUnitManagement rum) {
		restUnitManagement = rum;
	}

	public void unsetRestUnitManagement(RestUnitManagement rum) {
		restUnitManagement = null;
	}

	public void setRestDeviceDataReceiver(RestDeviceDataReceiver rddr) {
		restDeviceDataReceiver = rddr;
	}

	public void unsetRestDeviceDataReceiver(RestDeviceDataReceiver rddr) {
		restDeviceDataReceiver = null;
	}

	public void start() {
		unregisterCommandListener();
		registerCommandListener();

		String unitName = createUnit();
		for (int i = 0; i < NUMBER_OF_SENSORS; i++) {
			Sensor sensor = createSensor("sensor-" + i, unitName);
			sensors.put(sensor.getAddress(), sensor);
			
			Actuator actuator = createActuator("actuator-" + i);
			actuators.put(actuator.getAddress(), actuator);
			
			SensorDTO sensorDTO = sensor.getDTO();
			sensorDTO.affectedBy = new ArrayList<>();
			sensorDTO.affectedBy.add(actuator.getId());
			restSensorManagement.updateSensor(sensor.getId(), sensorDTO);
		}
		
		stopSenderThread();
//		senderThread = new SenderThread(this, sensor);
//		senderThread.start();
	}

	public void stop() {
		stopSenderThread();
		for (Sensor sensor : sensors.values()) {
			deleteSensor(sensor);
		}
		sensors.clear();
		unregisterCommandListener();
	}

	private void stopSenderThread() {
		if (senderThread != null) {
			senderThread.interrupt();
		}
	}

	private void registerCommandListener() {
		Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
		WebTarget target = client.target("http://localhost:9090/services/device/command/incadapter");
		commandEventSource = EventSource.target(target).build();
		EventListener listener = new EventListener() {
			@Override
			public void onEvent(InboundEvent inboundEvent) {
				CommandDTO command = inboundEvent.readData(CommandDTO.class, MediaType.APPLICATION_JSON_TYPE);
				switch (command.command) {
				case "read-measurement":
					readSensor(sensors.get(command.address));
					break;
				case "read-health":					
					readDeviceHealth(sensors.get(command.address));
					break;
				default:
					System.out.println("Received command: " + command.command + " for " + command.address);
					break;
				}
			}
		};
		commandEventSource.register(listener, "command");
		commandEventSource.open();
	}

	private void unregisterCommandListener() {
		if (commandEventSource != null) {
			commandEventSource.close();
		}
		commandEventSource = null;
	}
	
	public void readSensor(Sensor sensor) {
		MeasurementDataDTO dto = sensor.getNextMeasurement();
		restDeviceDataReceiver.receiveMeasurementData(dto);
	}
	
	public void readDeviceHealth(Sensor sensor) {
		DeviceHealthDTO dto = sensor.getDeviceHealth();
		restDeviceDataReceiver.receiveDeviceHealth(dto);
	}
	
	private String createUnit() {
		try {
			UnitDTO unit = new UnitDTO();
			unit.name = "Temperature";
			unit.unit = "°C";
			restUnitManagement.newUnit(unit);
			return unit.name;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}


	private Sensor createSensor(String deviceId, String unitName){
		Sensor sensor = new IncrementalSensor(deviceId, "IncrementalRestTestSensor" + UUID.randomUUID(), unitName, -1, 1, 0, 2, 4, 6, 1000, 5000);
		restSensorManagement.newSensor(sensor.getDTO());
		return sensor;
	}
	
	private Actuator createActuator(String deviceId){
		Actuator actuator = new SimpleActuator(deviceId, "SimpleRestTestActuator" + UUID.randomUUID(), 5000, 0, "OFF");
		restActuatorManagement.newActuator(actuator.getDTO());
		return actuator;
	}
		
	private void deleteSensor(Sensor sensor){
		restSensorManagement.deleteSensor(sensor.getId());
	}
}
