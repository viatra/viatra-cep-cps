package hu.karaszi.ec.testadapter.testlogic;

import java.util.Date;

import hu.karaszi.ec.centralunit.data.dto.devices.ActuatorStateDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.management.ActuatorDTO;

public class SimpleActuator implements Actuator {
	private String name;
	private String deviceId;
	private long healthCheckInterval;
	private String state = "ACTIVE";
	private String description = "simple rest test actuator";
	private String protocol = "incadapter";
	
	private int performance;
	private String actuatorState;
	
	public SimpleActuator(String deviceId, String name, long healthCheckInterval, int performance, String state) {
		this.deviceId = deviceId;
		this.name = name;
		this.healthCheckInterval = healthCheckInterval;
		
		this.performance = performance;
		this.actuatorState = state;
	}
	
	@Override
	public DeviceHealthDTO getDeviceHealth() {
		DeviceHealthDTO dto = new DeviceHealthDTO();
		dto.date = new Date();
		dto.deviceId = deviceId;
		dto.health = "OK";
		return dto;
	}

	@Override
	public ActuatorDTO getDTO() {
		ActuatorDTO dto = new ActuatorDTO();
		dto.deviceId = deviceId;
		dto.name = name;
		dto.description = description;
		dto.protocol = protocol;
		dto.address = deviceId;

		dto.healthCheckInterval = healthCheckInterval;
		dto.state = state;
		
		dto.actuatorState = actuatorState;
		dto.effect = "NEGATIVE";
		dto.performance = performance;
		return dto;
	}

	@Override
	public ActuatorStateDTO getStateDTO() {
		ActuatorStateDTO dto = new ActuatorStateDTO();
		dto.date = new Date();
		dto.deviceId = deviceId;
		dto.performance = performance;
		dto.state = actuatorState;
		return dto;
	}

	@Override
	public String getId() {
		return deviceId;
	}

	@Override
	public String getAddress() {
		return deviceId;
	}

	@Override
	public void setState(String state) {
		this.actuatorState = state;
	}

	@Override
	public void setPerformance(int performance) {
		this.performance = performance;
	}

}
