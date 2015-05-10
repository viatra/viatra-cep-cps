package hu.karaszi.ec.centralunit.controller.cep.instancemodel;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.dal.MeasurementDataManager;
import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.Measurement;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.event.processor.api.EventProcessor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import systemmodel.DeviceHealth;
import systemmodel.DeviceState;
import systemmodel.SensorRange;
import systemmodel.SystemmodelFactory;

public class InstanceModel implements EventProcessor{
	private Resource resource;
	private ResourceSet resourceSet;
	private Map<Long, systemmodel.Sensor> sensors = new HashMap<>();
	private Map<Long, systemmodel.Actuator> actuators = new HashMap<>();
	
	public Resource getResource(){
		return resource;
	}

	public void prepareModel(DeviceManager deviceManager, MeasurementDataManager measurementManager) {
		createResource();
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
	
	private void loadSensors(DeviceManager deviceManager,
			MeasurementDataManager measurementManager) {
		for (Sensor sensor : deviceManager.getSensors()) {
			systemmodel.Sensor sensorInstance = sensorToESensor(sensor,
					measurementManager);
			resource.getContents().add(sensorInstance);
		}
	}

	private systemmodel.Sensor sensorToESensor(
			Sensor sensor, MeasurementDataManager measurementManager) {
		systemmodel.Sensor sensorInstance = SystemmodelFactory.eINSTANCE.createSensor();
		setDeviceProperties(sensor, sensorInstance);
		
		sensorInstance.setCurrentRange(SensorRange.valueOf(sensor.getCurrentRange().name()));
		sensorInstance.setHighCriticalThreshold(sensor.getUpperCriticalThreshold());
		sensorInstance.setHighFatalThreshold(sensor.getUpperFatalThreshold());
		sensorInstance.setLowCriticalThreshold(sensor.getLowerCriticalThreshold());
		sensorInstance.setLowFatalThreshold(sensor.getLowerFatalThreshold());
		sensorInstance.setMinReadable(sensor.getMinReadable());
		sensorInstance.setMaxReadable(sensor.getMaxReadable());
		
		Measurement lastMeasurement = measurementManager.getLastMeasurement(sensor.getId());
		sensorInstance.setLastMeasurement(lastMeasurement.getValue() * lastMeasurement.getScale());
		sensorInstance.setLastMeasurementDate(lastMeasurement.getTimestamp());
		return sensorInstance;
	}
	
	private systemmodel.Sensor sensorToESensor(Sensor sensor) {
		systemmodel.Sensor eSensor = SystemmodelFactory.eINSTANCE.createSensor();
		setDeviceProperties(sensor, eSensor);		
		if(sensor.getCurrentRange() != null)
			eSensor.setCurrentRange(SensorRange.valueOf(sensor.getCurrentRange().name()));
		eSensor.setHighCriticalThreshold(sensor.getUpperCriticalThreshold());
		eSensor.setHighFatalThreshold(sensor.getUpperFatalThreshold());
		eSensor.setLowCriticalThreshold(sensor.getLowerCriticalThreshold());
		eSensor.setLowFatalThreshold(sensor.getLowerFatalThreshold());
		eSensor.setMinReadable(sensor.getMinReadable());
		eSensor.setMaxReadable(sensor.getMaxReadable());
		return eSensor;
	}

	private void setDeviceProperties(Device device,
			systemmodel.Device eDevice) {
		eDevice.setId(device.getId());
		eDevice.setName(device.getName());
		if(device.getDeviceHealth() != null)
			eDevice.setHealth(DeviceHealth.valueOf(device.getDeviceHealth().name()));
		if(device.getLastDeviceHealthDate() != null)
			eDevice.setLastHealthDate(device.getLastDeviceHealthDate());
		if(device.getDeviceState() != null)
			eDevice.setDeviceState(DeviceState.valueOf(device.getDeviceState().name()));
		if(device.getLastDeviceStateDate() != null)
			eDevice.setLastDeviceStateDate(device.getLastDeviceStateDate());
	}
	
	private void loadActuators(DeviceManager deviceManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processDeviceStatus(Device device) {
		systemmodel.Device deviceInstance = sensors.get(device.getId());
		if (deviceInstance == null) {
			deviceInstance = actuators.get(device.getId());
		}
		deviceInstance.setDeviceState(DeviceState.valueOf(device.getDeviceState().name()));
		deviceInstance.setLastDeviceStateDate(device.getLastDeviceStateDate());
	}

	@Override
	public void processThresholdEvent(Sensor sensor) {
		systemmodel.Sensor sensorInstance = sensors.get(sensor.getId());
		sensorInstance.setCurrentRange(SensorRange.valueOf(sensor.getCurrentRange().name()));
	}

	@Override
	public void processMeasurementData(Measurement measurement) {
		systemmodel.Sensor eSensor = sensors.get(measurement.getSensor().getId());
		
		double newValue = measurement.getValue() * measurement.getScale();
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
		
		System.out.println("New measurement arrived --- Senor: " + eSensor.getId() +
				", Value: " + newValue +
				" (" + newRange.name() + ")  @ " +
				measurement.getTimestamp().toString());
		
		eSensor.setLastMeasurement(newValue);
		eSensor.setLastMeasurementDate(measurement.getTimestamp());
		eSensor.setCurrentRange(newRange);
	}

	@Override
	public void processSensorDeleteEvent(long id) {
		systemmodel.Sensor sensorInstance = sensors.remove(id);
		EcoreUtil.delete(sensorInstance);
	}

	@Override
	public void processSensorUpdateEvent(Sensor sensor) {
		systemmodel.Sensor sensorInstance = sensors.get(sensor.getId());
		updateSensor(sensorInstance, sensor);
	}

	private void updateSensor(systemmodel.Sensor sensorInstance, Sensor sensor) {
		updateDeviceProperties(sensorInstance, sensor);		
		if(!sensorInstance.getCurrentRange().name().equals(sensor.getCurrentRange().name()))
			sensorInstance.setCurrentRange(SensorRange.valueOf(sensor.getCurrentRange().name()));
		if(sensorInstance.getHighCriticalThreshold() != sensor.getUpperCriticalThreshold())
			sensorInstance.setHighCriticalThreshold(sensor.getUpperCriticalThreshold());
		if(sensorInstance.getHighFatalThreshold() != sensor.getUpperFatalThreshold())
			sensorInstance.setHighFatalThreshold(sensor.getUpperFatalThreshold());
		if(sensorInstance.getLowCriticalThreshold() != sensor.getLowerCriticalThreshold())
			sensorInstance.setLowCriticalThreshold(sensor.getLowerCriticalThreshold());
		if(sensorInstance.getLowFatalThreshold() != sensor.getLowerFatalThreshold())
			sensorInstance.setLowFatalThreshold(sensor.getLowerFatalThreshold());
		if(sensorInstance.getMinReadable() != sensor.getMinReadable())
			sensorInstance.setMinReadable(sensor.getMinReadable());
		if(sensorInstance.getMaxReadable() != sensor.getMaxReadable())
			sensorInstance.setMaxReadable(sensor.getMaxReadable());
	}

	private void updateDeviceProperties(systemmodel.Device eDeviec,
			Device device) {
		if(!eDeviec.getName().equals(device.getName()))
			eDeviec.setName(device.getName());
		if(!eDeviec.getHealth().name().equals(device.getDeviceHealth().name()))
			eDeviec.setHealth(DeviceHealth.valueOf(device.getDeviceHealth().name()));
		if(!eDeviec.getLastHealthDate().equals(device.getLastDeviceHealthDate()))
			eDeviec.setLastHealthDate(device.getLastDeviceHealthDate());
		if(!eDeviec.getDeviceState().name().equals(device.getDeviceState().name()))
			eDeviec.setDeviceState(DeviceState.valueOf(device.getDeviceState().name()));
		if(!eDeviec.getLastDeviceStateDate().equals(device.getLastDeviceStateDate()))
			eDeviec.setLastDeviceStateDate(device.getLastDeviceStateDate());
	}

	@Override
	public void processSensorInsertEvent(Sensor sensor) {
		systemmodel.Sensor sensorInstance = sensorToESensor(sensor);
		sensors.put(sensorInstance.getId(), sensorInstance);
		resource.getContents().add(sensorInstance);
	}

	@Override
	public void processActuatorDeleteEvent(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processActuatorUpdateEvent(Actuator actuator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processActuatorInsertEvent(Actuator actuator) {
		// TODO Auto-generated method stub
		
	}

	public ResourceSet getResourceSet() {
		return resourceSet;
	}
}
