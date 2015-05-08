package hu.karaszi.ec.centralunit.controller.instancemodel;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.data.Sensor;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import systemmodel.DeviceHealth;
import systemmodel.SensorState;
import systemmodel.SystemmodelFactory;

public class InstanceModel {
	private Resource resource;
	private ResourceSet resourceSet;
	
	public Resource getResource(){
		return resource;
	}

	public void prepareModel(DeviceManager deviceManager) {
		createResource();
		loadSensors(deviceManager);
		loadActuators(deviceManager);		
	}

	private void createResource() {
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("fowler", new XMIResourceFactoryImpl());
		resourceSet = new ResourceSetImpl();
		resource = resourceSet.createResource(URI.createURI("systemmodel/system.model"));
	}
	
	private void loadSensors(DeviceManager deviceManager) {
		for (Sensor sensor : deviceManager.getSensors()) {
			systemmodel.Sensor sensorInstance = SystemmodelFactory.eINSTANCE.createSensor();
			sensorInstance.setId(sensor.getId());
			sensorInstance.setName(sensor.getName());
			sensorInstance.setHealth(DeviceHealth.valueOf(sensor.getDeviceHealth().name()));
			sensorInstance.setState(SensorState.valueOf(sensor.getCurrentRange().name()));
			resource.getContents().add(sensorInstance);
		}
	}
	
	private void loadActuators(DeviceManager deviceManager) {
		// TODO Auto-generated method stub
		
	}
}
