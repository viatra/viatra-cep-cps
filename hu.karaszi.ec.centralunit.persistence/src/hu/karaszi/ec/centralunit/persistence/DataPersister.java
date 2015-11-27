package hu.karaszi.ec.centralunit.persistence;

import org.osgi.service.component.ComponentContext;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.dal.MeasurementDataManager;
import hu.karaszi.ec.centralunit.dal.UnitManager;
import hu.karaszi.ec.centralunit.data.dto.devices.ActuatorStateDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.ThresholdEventDTO;
import hu.karaszi.ec.centralunit.data.dto.management.ActuatorDTO;
import hu.karaszi.ec.centralunit.data.dto.management.SensorDTO;
import hu.karaszi.ec.centralunit.data.dto.management.UnitDTO;
import hu.karaszi.ec.centralunit.data.persistence.Actuator;
import hu.karaszi.ec.centralunit.data.persistence.ActuatorEffect;
import hu.karaszi.ec.centralunit.data.persistence.ActuatorState;
import hu.karaszi.ec.centralunit.data.persistence.DeviceState;
import hu.karaszi.ec.centralunit.data.persistence.Measurement;
import hu.karaszi.ec.centralunit.data.persistence.Sensor;
import hu.karaszi.ec.centralunit.data.persistence.Unit;
import hu.karaszi.ec.centralunit.event.consumer.api.EventConsumer;
import hu.karaszi.ec.centralunit.event.consumer.rabbitmq.RabbitMQEventConsumer;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventForwarder;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventSource;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventType;
import hu.karaszi.ec.centralunit.event.processor.api.EventProcessor;

public class DataPersister implements EventProcessor {
	private final String[] topics = { "MANAGEMENT.#", "DEVICE.#" };

	private DeviceManager deviceManager;
	private UnitManager unitManager;
	private MeasurementDataManager measurementDataManager;
	private EventForwarder eventForwarder;

	private EventConsumer consumer = new RabbitMQEventConsumer();

	public void setDeviceManager(DeviceManager dm) {
		deviceManager = dm;
	}
	public void unsetDeviceManager(DeviceManager dm) {
		deviceManager = null;
	}
	public void setMeasurementDataManager(MeasurementDataManager mdm) {
		measurementDataManager = mdm;
	}
	public void unsetMeasurementDataManager(MeasurementDataManager mdm) {
		measurementDataManager = null;
	}
	public void setUnitManager(UnitManager um) {
		unitManager = um;
	}
	public void unsetUnitManager(UnitManager um) {
		unitManager = null;
	}
	public void setEventForwarder(EventForwarder ef) {
		eventForwarder = ef;
	}
	public void unsetEventForwarder(EventForwarder ef) {
		eventForwarder = null;
	}
	
	public void activate(ComponentContext bundleContext) {
		consumer.activate(this, topics);
	}

	public void deactivate(ComponentContext bundleContext) {
		consumer.deactivate();
	}

	@Override
	public void handleEvent(Object message, String eventType) {
		switch (eventType) {
		case "MEASUREMENT":
			processMeasurementData((MeasurementDataDTO) message);
			break;
		case "THRESHOLD":
			processThresholdEvent((ThresholdEventDTO) message);
			break;
		case "HEALTH":
			processDeviceHealthEvent((DeviceHealthDTO) message);
			break;
		case "ACTUATOR_STATE":
			processActuatorStateEvent((ActuatorStateDTO) message);
			break;
		case "SENSOR_INSERT":
			processSensorInsertEvent((SensorDTO) message);
			break;
		case "SENSOR_UPDATE":
			processSensorUpdateEvent((SensorDTO) message);
			break;
		case "SENSOR_DELETE":
			processSensorDeleteEvent((String) message);
			break;
		case "ACTUATOR_INSERT":
			processActuatorInsertEvent((ActuatorDTO) message);
			break;
		case "ACTUATOR_UPDATE":
			processActuatorUpdateEvent((ActuatorDTO) message);
			break;
		case "ACTUATOR_DELETE":
			processActuatorDeleteEvent((String) message);
			break;
		case "UNIT_INSERT":
			processUnitInsertEvent((UnitDTO) message);
			break;
		case "UNIT_UPDATE":
			processUnitUpdateEvent((UnitDTO) message);
			break;
		case "UNIT_DELETE":
			processUnitDeleteEvent((String) message);
			break;
		default:
			break;
		}
	}

	public void processDeviceHealthEvent(DeviceHealthDTO device) {
		// TODO Auto-generated method stub

	}

	public void processThresholdEvent(ThresholdEventDTO thresholdEvent) {
		// TODO Auto-generated method stub

	}

	public void processMeasurementData(MeasurementDataDTO measurement) {
		Sensor sourceSensor = deviceManager.getSensor(measurement.deviceId);
		Measurement newMeasurement = new Measurement();
		newMeasurement.setScale(measurement.scale);
		newMeasurement.setSensor(sourceSensor);
		newMeasurement.setTimestamp(measurement.date);
		newMeasurement.setValue(measurement.measurement);
		measurementDataManager.insertMeasurement(newMeasurement);
	}

	private void processActuatorStateEvent(ActuatorStateDTO message) {
		Actuator sourceActuator = deviceManager.getActuator(message.deviceId);
		sourceActuator.setState(ActuatorState.valueOf(message.state));
		sourceActuator.setPerformance(message.performance);
		deviceManager.updateActuator(sourceActuator);
	}

	public void processSensorDeleteEvent(String deviceId) {
		deviceManager.deleteSensor(deviceId);
		eventForwarder.sendEvent(deviceId, EventSource.DATABASE, EventType.SENSOR_DELETE);
	}

	public void processSensorUpdateEvent(SensorDTO sensor) {
		Sensor updatedSensor = DTOToSensor(sensor);
		deviceManager.updateSensor(updatedSensor);
		eventForwarder.sendEvent(updatedSensor.toDTO(), EventSource.DATABASE, EventType.SENSOR_UPDATE);
	}

	public void processSensorInsertEvent(SensorDTO sensor) {
		System.out.println("Inserting: " + sensor.deviceId);
		Sensor newSensor = DTOToSensor(sensor);
		deviceManager.insertSensor(newSensor);
		eventForwarder.sendEvent(newSensor.toDTO(), EventSource.DATABASE, EventType.SENSOR_INSERT);
	}

	public void processActuatorDeleteEvent(String deviceId) {
		deviceManager.deleteActuator(deviceId);
		eventForwarder.sendEvent(deviceId, EventSource.DATABASE, EventType.ACTUATOR_DELETE);
	}

	public void processActuatorUpdateEvent(ActuatorDTO actuator) {
		Actuator updatedActuator = DTOToActuator(actuator);
		deviceManager.updateActuator(updatedActuator);
		eventForwarder.sendEvent(updatedActuator.toDTO(), EventSource.DATABASE, EventType.ACTUATOR_UPDATE);
	}

	public void processActuatorInsertEvent(ActuatorDTO actuator) {
		Actuator newActuator = DTOToActuator(actuator);
		deviceManager.insertActuator(newActuator);
		eventForwarder.sendEvent(newActuator.toDTO(), EventSource.DATABASE, EventType.ACTUATOR_INSERT);
	}

	public void processUnitDeleteEvent(String unitName) {
		unitManager.deleteUnitByName(unitName);
		eventForwarder.sendEvent(unitName, EventSource.DATABASE, EventType.UNIT_DELETE);
	}

	public void processUnitUpdateEvent(UnitDTO unit) {
		Unit updatedUnit = DTOToUnit(unit);
		unitManager.updateUnit(updatedUnit);
		eventForwarder.sendEvent(updatedUnit.toDTO(), EventSource.DATABASE, EventType.UNIT_UPDATE);
	}

	public void processUnitInsertEvent(UnitDTO unit) {
		Unit newUnit = DTOToUnit(unit);
		unitManager.insertUnit(newUnit);
		eventForwarder.sendEvent(newUnit.toDTO(), EventSource.DATABASE, EventType.UNIT_INSERT);
	}

	private Sensor DTOToSensor(SensorDTO dto) {
		 Sensor sensor = deviceManager.getSensor(dto.deviceId);
		 if (sensor == null) {
			 sensor = new Sensor();
		 }
		sensor.setName(dto.name);
		sensor.setDescription(dto.description);
		sensor.setDeviceId(dto.deviceId);
		sensor.setAddress(dto.address);
		sensor.setProtocol(dto.protocol);
		sensor.setDeviceState(DeviceState.valueOf(dto.state));
		sensor.setHealthCheckInterval(dto.healthCheckInterval);
		
		for (String actuatorId : dto.affectedBy) {
			Actuator actuator = deviceManager.getActuator(actuatorId);
			if (actuator != null) {
				sensor.getAffectedBy().add(actuator);
			}
		}
		sensor.setLowerCriticalThreshold(dto.lowerCriticalThreshold);
		sensor.setLowerFatalThreshold(dto.lowerFatalThreshold);
		sensor.setUpperCriticalThreshold(dto.upperCriticalThreshold);
		sensor.setUpperFatalThreshold(dto.upperFatalThreshold);
		sensor.setMinReadable(dto.minReadable);
		sensor.setMaxReadable(dto.maxReadable);
		sensor.setUnit(unitManager.getUnit(dto.unit));
		sensor.setReadInterval(dto.readInterval);
		return sensor;
	}

	private Actuator DTOToActuator(ActuatorDTO dto) {
		Actuator actuator = new Actuator();
		actuator.setDeviceId(dto.deviceId);
		actuator.setName(dto.name);
		actuator.setAddress(dto.address);
		actuator.setDescription(dto.description);
		actuator.setProtocol(dto.protocol);
		actuator.setDeviceState(DeviceState.valueOf(dto.state));
		actuator.setEffect(ActuatorEffect.valueOf(dto.effect));
		actuator.setState(ActuatorState.valueOf(dto.actuatorState));
		actuator.setPerformance(dto.performance);
		for (String sensorId : dto.affects) {
			Sensor sensor = deviceManager.getSensor(sensorId);
			if (sensor != null) {
				actuator.getAffects().add(sensor);
			}
		}
		return actuator;
	}

	private Unit DTOToUnit(UnitDTO dto) {
		Unit unit = new Unit();
		unit.setName(dto.name);
		unit.setUnit(dto.unit);
		return unit;
	}
}
