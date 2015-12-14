package hu.karaszi.ec.piadapter.centralunitproxy;

import java.util.List;

import javax.ws.rs.core.Response;

import hu.karaszi.ec.piadapter.centralunitproxy.dto.SensorDTO;

public interface RestSensorManagement {
	public List<SensorDTO> getSensors();	
	public SensorDTO getSensor(String sensorId);
	public SensorDTO newSensor(final SensorDTO sensor);	
	public Response updateSensor(String sensorId, final SensorDTO sensor);
	public Response deleteSensor(String sensorId);
}
