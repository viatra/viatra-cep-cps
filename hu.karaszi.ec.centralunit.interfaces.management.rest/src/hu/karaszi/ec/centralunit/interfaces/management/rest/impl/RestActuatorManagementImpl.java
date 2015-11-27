package hu.karaszi.ec.centralunit.interfaces.management.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.data.dto.management.ActuatorDTO;
import hu.karaszi.ec.centralunit.data.persistence.Actuator;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventForwarder;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventSource;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventType;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestActuatorManagement;

public class RestActuatorManagementImpl implements RestActuatorManagement {
	private DeviceManager deviceManager;
	private EventForwarder eventForwarder;

	public void setDeviceManager(DeviceManager dm) {
		deviceManager = dm;
	}

	public void unsetDeviceManager(DeviceManager dm) {
		deviceManager = null;
	}

	public void setEventForwarder(EventForwarder mef) {
		eventForwarder = mef;
	}

	public void unsetEventForwarder(EventForwarder mef) {
		eventForwarder = null;
	}
	
	@Override
	public List<ActuatorDTO> getActuators() {
		List<ActuatorDTO> actuatorDTOList = new ArrayList<ActuatorDTO>();
		for (Actuator actuator : deviceManager.getActuators()) {
			actuatorDTOList.add(actuator.toDTO());
		} 
		return actuatorDTOList;
	}

	@Override
	public ActuatorDTO getActuator(String actuatorId) {
		Actuator actuator = deviceManager.getActuator(actuatorId);
		return actuator.toDTO();
	}

	@Override
	public Response newActuator(ActuatorDTO actuator) {
		eventForwarder.sendEvent(actuator, EventSource.MANAGEMENT, EventType.ACTUATOR_INSERT);
		return null;
	}

	@Override
	public Response updateActuator(String actuatorId, ActuatorDTO actuator) {
		eventForwarder.sendEvent(actuator, EventSource.MANAGEMENT, EventType.ACTUATOR_UPDATE);
		return null;
	}

	@Override
	public Response deleteActuator(String actuatorId) {
		eventForwarder.sendEvent(actuatorId, EventSource.MANAGEMENT, EventType.ACTUATOR_DELETE);
		return null;
	}
}
