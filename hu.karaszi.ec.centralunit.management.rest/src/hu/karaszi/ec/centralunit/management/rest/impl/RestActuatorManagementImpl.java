package hu.karaszi.ec.centralunit.management.rest.impl;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.ActuatorEffect;
import hu.karaszi.ec.centralunit.data.ActuatorState;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.management.rest.RestActuatorManagement;
import hu.karaszi.ec.centralunit.management.rest.dto.ActuatorDTO;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

public class RestActuatorManagementImpl implements RestActuatorManagement {
	private DeviceManager deviceManager;
	
	public void setDeviceManager(DeviceManager dm) {
		deviceManager = dm;
	}

	public void unsetDeviceManager(DeviceManager dm) {
		deviceManager = null;
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
	public ActuatorDTO getActuator(String actuatorId) {
		Actuator actuator = deviceManager.getActuator(Long.parseLong(actuatorId));
		return actuatorToDTO(actuator);
	}

	@Override
	public Response newActuator(ActuatorDTO actuator) {
		Actuator newActuator = DTOToActuator(actuator);
		deviceManager.insertActuator(newActuator);
		//TODO
		return null;

	}

	@Override
	public Response updateActuator(String actuatorId, ActuatorDTO actuator) {
		Actuator newActuator = DTOToActuator(actuator);
		deviceManager.updateActuator(newActuator);
		return Response.ok().build();
	}

	@Override
	public Response deleteActuator(String actuatorId) {
		deviceManager.deleteActuator(Long.parseLong(actuatorId));
		return Response.ok().build();
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
