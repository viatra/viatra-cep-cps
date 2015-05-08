package hu.karaszi.ec.centralunit.interfaces.management.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.dal.UnitManager;
import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.event.forwarder.api.ManagementEventForwarder;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestSensorManagement;
import hu.karaszi.ec.centralunit.interfaces.management.rest.dto.SensorDTO;

public class RestSensorManagementImpl implements RestSensorManagement {
	private DeviceManager deviceManager;
	private UnitManager unitManager;
	private ManagementEventForwarder managementEventForwarder;
	
	public void setDeviceManager(DeviceManager dm) {
		deviceManager = dm;
	}

	public void unsetDeviceManager(DeviceManager dm) {
		deviceManager = null;
	}
	
	public void setUnitManager(UnitManager um) {
		unitManager = um;
	}

	public void unsetUnitManager(UnitManager um) {
		unitManager = null;
	}
	
	public void setManagementEventForwarder(ManagementEventForwarder mef) {
		managementEventForwarder = mef;
	}

	public void unsetManagementEventForwarder(ManagementEventForwarder mef) {
		managementEventForwarder = null;
	}
	
	@Override
	public List<SensorDTO> getSensors() {
		List<SensorDTO> sensorDTOList = new ArrayList<SensorDTO>();
		for (Sensor sensor : deviceManager.getSensors()) {
			sensorDTOList.add(sensorToDTO(sensor));
		} 
		return sensorDTOList;
	}

	@Override
	public SensorDTO getSensor(long sensorId) {
		Sensor sensor = deviceManager.getSensor(sensorId);
		return sensorToDTO(sensor);
	}

	@Override
	public SensorDTO newSensor(SensorDTO sensor) {
		Sensor newSensor = DTOToSensor(sensor);
		Sensor created = deviceManager.insertSensor(newSensor);		
		managementEventForwarder.forwardSensorInsertEvent(created);
		return sensorToDTO(created);

	}

	@Override
	public SensorDTO updateSensor(long sensorId, SensorDTO sensor) {
		Sensor newSensor = DTOToSensor(sensor);
		Sensor updated = deviceManager.updateSensor(newSensor);
		managementEventForwarder.forwardSensorUpdateEvent(updated);
		return sensorToDTO(updated);
	}

	@Override
	public Response deleteSensor(long sensorId) {
		deviceManager.deleteSensor(sensorId);
		managementEventForwarder.forwardSensorDeleteEvent(sensorId);
		//return Response.ok().build();
		return null;
	}

	private SensorDTO sensorToDTO(Sensor sensor){
		SensorDTO dto = new SensorDTO();
		dto.address = sensor.getAddress();
		for (Actuator actuator : sensor.getAffectedBy()) {
			dto.affectedBy.add(actuator.getId());
		}
		dto.description = sensor.getDescription();
		dto.id = sensor.getId();
		dto.lowerCriticalThreshold = sensor.getLowerCriticalThreshold();
		dto.lowerFatalThreshold = sensor.getLowerFatalThreshold();
		dto.maxReadable = sensor.getMaxReadable();
		dto.minReadable = sensor.getMinReadable();
		dto.name = sensor.getName();
		dto.protocol = sensor.getProtocol();
		dto.unit = sensor.getUnit().getId();
		dto.upperCriticalThreshold = sensor.getUpperCriticalThreshold();
		dto.upperFatalThreshold = sensor.getUpperFatalThreshold();
		return dto;
	}
	
	private Sensor DTOToSensor(SensorDTO dto){
		Sensor sensor = new Sensor();
		sensor.setAddress(dto.address);
		for (long actuatorId : dto.affectedBy) {
			Actuator actuator = deviceManager.getActuator(actuatorId);
			sensor.getAffectedBy().add(actuator);
		}
		sensor.setDescription(dto.description);
		sensor.setId(dto.id);
		sensor.setLowerCriticalThreshold(dto.lowerCriticalThreshold);
		sensor.setLowerFatalThreshold(dto.lowerFatalThreshold);
		sensor.setUpperCriticalThreshold(dto.upperCriticalThreshold);
		sensor.setUpperFatalThreshold(dto.upperFatalThreshold);
		sensor.setMinReadable(dto.minReadable);
		sensor.setMaxReadable(dto.maxReadable);
		sensor.setName(dto.name);
		sensor.setProtocol(dto.protocol);
		sensor.setUnit(unitManager.getUnit(dto.unit));		
		return sensor;
	}
}
