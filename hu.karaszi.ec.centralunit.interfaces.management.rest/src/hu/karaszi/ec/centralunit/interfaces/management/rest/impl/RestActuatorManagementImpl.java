package hu.karaszi.ec.centralunit.interfaces.management.rest.impl;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.ActuatorEffect;
import hu.karaszi.ec.centralunit.data.ActuatorState;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.event.forwarder.api.ManagementEventForwarder;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestActuatorManagement;
import hu.karaszi.ec.centralunit.interfaces.management.rest.dto.ActuatorDTO;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

public class RestActuatorManagementImpl implements RestActuatorManagement {
	private DeviceManager deviceManager;
	private ManagementEventForwarder managementEventForwarder;

	public void setDeviceManager(DeviceManager dm) {
		deviceManager = dm;
	}

	public void unsetDeviceManager(DeviceManager dm) {
		deviceManager = null;
	}

	public void setManagementEventForwarder(ManagementEventForwarder mef) {
		managementEventForwarder = mef;
	}

	public void unsetManagementEventForwarder(ManagementEventForwarder mef) {
		managementEventForwarder = null;
	}
	
	@Override
	public List<ActuatorDTO> getActuators() {
		List<ActuatorDTO> actuatorDTOList = new ArrayList<ActuatorDTO>();
		for (Actuator actuator : deviceManager.getActuators()) {
			actuatorDTOList.add(actuatorToDTO(actuator));
		} 
		return actuatorDTOList;
	}

	@Override
	public ActuatorDTO getActuator(long actuatorId) {
		Actuator actuator = deviceManager.getActuator(actuatorId);
		return actuatorToDTO(actuator);
	}

	@Override
	public ActuatorDTO newActuator(ActuatorDTO actuator) {
		Actuator newActuator = DTOToActuator(actuator);
		Actuator created = deviceManager.insertActuator(newActuator);
		managementEventForwarder.forwardActuatorInsertEvent(created);
		return actuatorToDTO(created);
	}

	@Override
	public ActuatorDTO updateActuator(long actuatorId, ActuatorDTO actuator) {
		Actuator newActuator = DTOToActuator(actuator);
		Actuator updated = deviceManager.updateActuator(newActuator);
		managementEventForwarder.forwardActuatorUpdateEvent(updated);
		return actuatorToDTO(updated);
	}

	@Override
	public Response deleteActuator(long actuatorId) {
		deviceManager.deleteActuator(actuatorId);
		managementEventForwarder.forwardActuatorDeleteEvent(actuatorId);
		//return Response.ok().build();
		return null;
	}

	private ActuatorDTO actuatorToDTO(Actuator actuator){
		ActuatorDTO dto = new ActuatorDTO();
		dto.id = actuator.getId();
		dto.name = actuator.getName();
		dto.address = actuator.getAddress();
		dto.protocol = actuator.getProtocol();
		dto.description = actuator.getDescription();
		dto.effect = actuator.getEffect().name();
		dto.state = actuator.getState().name();
		dto.performance = actuator.getPerformance();
		for (Sensor sensor : actuator.getAffects()) {
			dto.affects.add(sensor.getId());
		}
		return dto;
	}
	
	private Actuator DTOToActuator(ActuatorDTO dto){
		Actuator actuator = new Actuator();
		actuator.setId(dto.id);
		actuator.setName(dto.name);
		actuator.setAddress(dto.address);
		actuator.setDescription(dto.description);
		actuator.setProtocol(dto.protocol);
		actuator.setEffect(ActuatorEffect.valueOf(dto.effect));
		actuator.setState(ActuatorState.valueOf(dto.state));
		actuator.setPerformance(dto.performance);
		for (long sensorId : dto.affects) {
			Sensor sensor = deviceManager.getSensor(sensorId);
			actuator.getAffects().add(sensor);
		}
		return actuator;
	}
}
