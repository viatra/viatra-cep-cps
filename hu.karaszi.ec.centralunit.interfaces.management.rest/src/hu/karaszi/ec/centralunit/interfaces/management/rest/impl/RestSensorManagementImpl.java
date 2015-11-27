package hu.karaszi.ec.centralunit.interfaces.management.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.data.dto.management.SensorDTO;
import hu.karaszi.ec.centralunit.data.persistence.Sensor;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventForwarder;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventSource;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventType;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestSensorManagement;

public class RestSensorManagementImpl implements RestSensorManagement {
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
	public List<SensorDTO> getSensors() {
		List<SensorDTO> sensorDTOList = new ArrayList<SensorDTO>();
		for (Sensor sensor : deviceManager.getSensors()) {
			sensorDTOList.add(sensor.toDTO());
		} 
		return sensorDTOList;
	}

	@Override
	public SensorDTO getSensor(String sensorId) {
		Sensor sensor = deviceManager.getSensor(sensorId);
		return sensor.toDTO();
	}

	@Override
	public Response newSensor(SensorDTO sensor) {
		eventForwarder.sendEvent(sensor, EventSource.MANAGEMENT, EventType.SENSOR_INSERT);
		return null;
	}

	@Override
	public Response updateSensor(String sensorId, SensorDTO sensor) {
		eventForwarder.sendEvent(sensor, EventSource.MANAGEMENT, EventType.SENSOR_UPDATE);
		return null;
	}

	@Override
	public Response deleteSensor(String sensorId) {
		eventForwarder.sendEvent(sensorId, EventSource.MANAGEMENT, EventType.SENSOR_DELETE);
		//return Response.ok().build();
		return null;
	}
}
