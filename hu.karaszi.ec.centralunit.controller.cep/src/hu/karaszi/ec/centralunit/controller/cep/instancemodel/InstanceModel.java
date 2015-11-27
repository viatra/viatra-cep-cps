package hu.karaszi.ec.centralunit.controller.cep.instancemodel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.dal.MeasurementDataManager;
import hu.karaszi.ec.centralunit.data.dto.devices.ActuatorStateDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.ThresholdEventDTO;
import hu.karaszi.ec.centralunit.data.dto.management.ActuatorDTO;
import hu.karaszi.ec.centralunit.data.dto.management.DeviceDTO;
import hu.karaszi.ec.centralunit.data.dto.management.SensorDTO;
import systemmodel.Actuator;
import systemmodel.ActuatorEffect;
import systemmodel.ActuatorState;
import systemmodel.Device;
import systemmodel.DeviceHealth;
import systemmodel.DeviceState;
import systemmodel.Sensor;
import systemmodel.SensorRange;
import systemmodel.SystemmodelFactory;
import systemmodel.Time;

public class InstanceModel {
	private Resource resource;
	private ResourceSet resourceSet;
	private Map<String, Sensor> sensors = new HashMap<>();
	private Map<String, Actuator> actuators = new HashMap<>();
	private Time time;
	
	public Resource getResource(){
		return resource;
	}

	public void prepareModel(DeviceManager deviceManager, MeasurementDataManager measurementManager) {
		createResource();
		createTimeInstance();
		loadSensors(deviceManager, measurementManager);
		loadActuators(deviceManager);
	}

	private void createResource() {
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("model", new XMIResourceFactoryImpl());
		resourceSet = new ResourceSetImpl();
		resource = resourceSet.createResource(URI.createURI("systemmodel/system.model"));
	}
	
	private void createTimeInstance() {
		Time tInstance  = SystemmodelFactory.eINSTANCE.createTime();
		tInstance.setCurrentTime(new Date().getTime());
		time = tInstance;
		resource.getContents().add(tInstance);
	}
	
	public Map<String, Sensor> getSensors() {
		return sensors;
	}
	
	public Map<String, Actuator> getActuators() {
		return actuators;
	}
	
	private void loadSensors(DeviceManager deviceManager,
			MeasurementDataManager measurementManager) {
//		for (Sensor sensor : deviceManager.getSensors()) {
//			Sensor sensorInstance = sensorToESensor(sensor,
//					measurementManager);
//			resource.getContents().add(sensorInstance);
//		}
	}

//	private Sensor sensorToESensor(
//			Sensor sensor, MeasurementDataManager measurementManager) {
//		Sensor eSensor = SystemmodelFactory.eINSTANCE.createSensor();
//		eSensor.setDeviceId(sensor.getDeviceId());
//		updateSensor(eSensor, sensor);
//		
//		Measurement lastMeasurement = measurementManager.getLastMeasurement(sensor.getId());
//		eSensor.setLastMeasurement(lastMeasurement.getValue() * lastMeasurement.getScale());
//		eSensor.setLastMeasurementDate(lastMeasurement.getTimestamp().getTime());
//		return eSensor;
//	}
	
	private void loadActuators(DeviceManager deviceManager) {
		// TODO Auto-generated method stub
		
	}

	public void processDeviceHealthEvent(DeviceHealthDTO healthDTO) {
		Device eDevice = sensors.get(healthDTO.deviceId);
		if (eDevice == null) {
			eDevice = actuators.get(healthDTO.deviceId);
		}
		
		System.out.println("New health arrived --- Device: " + eDevice.getDeviceId() +
				", Health: " + healthDTO.health + " @ " + healthDTO.date.toString());

		eDevice.setDeviceHealth(DeviceHealth.valueOf(healthDTO.health));
		eDevice.setLastDeviceHealthDate(healthDTO.date.getTime());
	}

	public void processThresholdEvent(ThresholdEventDTO thresholdEvent) {
		Sensor sensorInstance = sensors.get(thresholdEvent.deviceId);
		sensorInstance.setCurrentRange(SensorRange.valueOf(thresholdEvent.newRange));
	}
	
	public void processActuatorStateEvent(ActuatorStateDTO message) {
		Actuator eActuator = actuators.get(message.deviceId);
		eActuator.setState(ActuatorState.valueOf(message.state));
		eActuator.setPerformance(message.performance);
		eActuator.setLastActuatorStateDate(message.date);
	}

	public void processMeasurementData(MeasurementDataDTO measurement) {
		Sensor eSensor = sensors.get(measurement.deviceId);
		
		double newValue = measurement.measurement * measurement.scale;
		SensorRange newRange = SensorRange.HIGH_FATAL;
		if (eSensor.getLastMeasurement() < eSensor.getLowFatalThreshold()) {
			newRange = SensorRange.LOW_FATAL;
		} else if (eSensor.getLastMeasurement() < eSensor.getLowCriticalThreshold()) {
			newRange = SensorRange.LOW_CRITICAL;
		} else if (eSensor.getLastMeasurement() < eSensor.getHighCriticalThreshold()) {
			newRange = SensorRange.NORMAL;
		} else if (eSensor.getLastMeasurement() < eSensor.getHighFatalThreshold()) {
			newRange = SensorRange.HIGH_CRITICAL;
		}
		
//		System.out.println("New measurement arrived --- Sensor: " + eSensor.getDeviceId() +
//				", Value: " + newValue +
//				" (" + newRange.name() + ")  @ " +
//				measurement.date.toString());
		
		eSensor.setLastMeasurement(newValue);
		eSensor.setLastMeasurementDate(measurement.date.getTime());
		eSensor.setCurrentRange(newRange);
	}

	public void processSensorDeleteEvent(String name) {
		Sensor sensorInstance = sensors.remove(name);
		EcoreUtil.delete(sensorInstance);
	}

	public void processSensorUpdateEvent(SensorDTO sensor) {
		Sensor sensorInstance = sensors.get(sensor.deviceId);
		updateSensor(sensorInstance, sensor);
	}

	public void processSensorInsertEvent(SensorDTO sensor) {
		Sensor sensorInstance = sensorToESensor(sensor);
		sensors.put(sensorInstance.getDeviceId(), sensorInstance);
		resource.getContents().add(sensorInstance);
	}

	public void processActuatorDeleteEvent(String name) {
		Actuator actuator = actuators.remove(name);
		EcoreUtil.delete(actuator);
	}

	public void processActuatorUpdateEvent(ActuatorDTO actuator) {
		Actuator actuatorInstance = actuators.remove(actuator.deviceId);
		updateActuator(actuatorInstance, actuator);
	}

	public void processActuatorInsertEvent(ActuatorDTO actuator) {
		Actuator actuatorInstance = actuatorToEActuator(actuator);
		actuators.put(actuatorInstance.getDeviceId(), actuatorInstance);
		resource.getContents().add(actuatorInstance);
	}

	public void processTick(Date currentTime){
		time.setCurrentTime(currentTime.getTime());
		int index = resource.getContents().indexOf(time);
		Time timeInstance = (Time)resource.getContents().get(index);
		System.out.println("Tick: " + new Date(timeInstance.getCurrentTime()));
	}
	
	public ResourceSet getResourceSet() {
		return resourceSet;
	}
	
	private Sensor sensorToESensor(SensorDTO sensor) {
		Sensor eSensor = SystemmodelFactory.eINSTANCE.createSensor();
		eSensor.setDeviceId(sensor.deviceId);
		updateSensor(eSensor, sensor);
		return eSensor;
	}
	
	private Actuator actuatorToEActuator(ActuatorDTO actuator) {
		Actuator eActuator = SystemmodelFactory.eINSTANCE.createActuator();
		eActuator.setDeviceId(actuator.deviceId);
		updateActuator(eActuator, actuator);
		return eActuator;
	}

	private void updateActuator(Actuator actuatorInstance, ActuatorDTO actuator) {
		updateDeviceProperties(actuatorInstance, actuator);
		if (!actuatorInstance.getEffect().equals(ActuatorEffect.valueOf(actuator.effect)))
			actuatorInstance.setEffect(ActuatorEffect.valueOf(actuator.effect));
		if (!actuatorInstance.getState().equals(ActuatorState.valueOf(actuator.actuatorState)))
			actuatorInstance.setState(ActuatorState.valueOf(actuator.actuatorState));
		if (actuatorInstance.getPerformance() != actuator.performance)
			actuatorInstance.setPerformance(actuator.performance);

		List<Sensor> affects = actuatorInstance.getAffects();
		for (Sensor sensor : affects) {
			if (!actuator.affects.contains(sensor.getDeviceId())) {
				affects.remove(sensor);
				sensor.getAffectedBy().remove(actuatorInstance);
			} else {
				actuator.affects.remove(sensor.getDeviceId());
			}
		}
		for (String sensorId : actuator.affects) {
			Sensor toAdd = sensors.get(sensorId);
			affects.add(toAdd);
			toAdd.getAffectedBy().add(actuatorInstance);
		}
	}
	
	private void updateSensor(Sensor sensorInstance, SensorDTO sensor) {
		updateDeviceProperties(sensorInstance, sensor);
		if(sensorInstance.getHighCriticalThreshold() != sensor.upperCriticalThreshold)
			sensorInstance.setHighCriticalThreshold(sensor.upperCriticalThreshold);
		if(sensorInstance.getHighFatalThreshold() != sensor.upperFatalThreshold)
			sensorInstance.setHighFatalThreshold(sensor.upperFatalThreshold);
		if(sensorInstance.getLowCriticalThreshold() != sensor.lowerCriticalThreshold)
			sensorInstance.setLowCriticalThreshold(sensor.lowerCriticalThreshold);
		if(sensorInstance.getLowFatalThreshold() != sensor.lowerFatalThreshold)
			sensorInstance.setLowFatalThreshold(sensor.lowerFatalThreshold);
		if(sensorInstance.getMinReadable() != sensor.minReadable)
			sensorInstance.setMinReadable(sensor.minReadable);
		if(sensorInstance.getMaxReadable() != sensor.maxReadable)
			sensorInstance.setMaxReadable(sensor.maxReadable);
		if (sensorInstance.getReadInterval() != sensor.readInterval)
			sensorInstance.setReadInterval(sensor.readInterval);

		List<Actuator> affectedBy = sensorInstance.getAffectedBy();
		for (Actuator actuator : affectedBy) {
			if (!sensor.affectedBy.contains(actuator.getDeviceId())) {
				affectedBy.remove(actuator);
				actuator.getAffects().remove(sensorInstance);
			} else {
				sensor.affectedBy.remove(actuator.getDeviceId());
			}
		}
		for (String actuatorId : sensor.affectedBy) {
			Actuator toAdd = actuators.get(actuatorId);
			affectedBy.add(toAdd);
			toAdd.getAffects().add(sensorInstance);
		}
}
	
	private void updateDeviceProperties(Device eDevice,
			DeviceDTO device) {
		if(!eDevice.getDeviceState().name().equals(device.state))
			eDevice.setDeviceState(DeviceState.valueOf(device.state));
		if(eDevice.getHealthCheckInterval() != device.healthCheckInterval)
			eDevice.setHealthCheckInterval(device.healthCheckInterval);
	}
}
