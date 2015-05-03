package hu.karaszi.ec.centralunit.management.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.dal.UnitManager;
import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.management.rest.RestSensorManagement;
import hu.karaszi.ec.centralunit.management.rest.dto.SensorDTO;

public class RestSensorManagementImpl implements RestSensorManagement {
	private DeviceManager deviceManager;
	private UnitManager unitManager;
	
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
	
	@Override
	public List<SensorDTO> getSensors() {
		List<SensorDTO> sensorDTOList = new ArrayList<SensorDTO>();
		for (Sensor sensor : deviceManager.getSensors()) {
			sensorDTOList.add(sensorToDTO(sensor));
		} 
		return sensorDTOList;
	}

	@Override
	public SensorDTO getSensor(String sensorId) {
		Sensor sensor = deviceManager.getSensor(Long.parseLong(sensorId));
		return sensorToDTO(sensor);
	}

	@Override
	public Response newSensor(SensorDTO sensor) {
		Sensor newSensor = DTOToSensor(sensor);
		deviceManager.insertSensor(newSensor);
		//TODO
		return null;

	}

	@Override
	public Response updateSensor(String sensorId, SensorDTO sensor) {
		Sensor newSensor = DTOToSensor(sensor);
		deviceManager.updateSensor(newSensor);
		return Response.ok().build();
	}

	@Override
	public Response deleteSensor(String sensorId) {
		deviceManager.deleteSensor(Long.parseLong(sensorId));
		return Response.ok().build();
	}

	private SensorDTO sensorToDTO(Sensor sensor){
		SensorDTO dto = new SensorDTO();
		dto.address = sensor.getAddress();
		for (Actuator actuator : sensor.getAffectedBy()) {
			dto.affectedBy.add(actuator.getId());
		}
		dto.description = sensor.getDescription();
		dto.hysteresis = sensor.getHysteresis();
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
		sensor.setHysteresis(dto.hysteresis);
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
