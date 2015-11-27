package hu.karaszi.ec.testadapter.testlogic;

import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.data.dto.management.SensorDTO;

import java.util.Date;

public class IncrementalSensor implements Sensor {
	private double value;
	private double increment;
	private double lowerFatalThreshold;
	private double lowerCriticalThreshold;
	private double upperCriticalThreshold;
	private double upperFatalThreshold;
	private String name;
	private String deviceId;
	private String unitName;	
	private long readInterval;
	private long healthCheckInterval;
	private String state = "ACTIVE";
	private String description = "incremental rest test sensor";
	private String protocol = "incadapter";
	
	public IncrementalSensor(String deviceId, String name, String unitName, double value, double increment, double lowerFatalThreshold,
			double lowerCriticalThreshold, double upperCriticalThreshold,
			double upperFatalThreshold, long readInterval, long healthCheckInterval) {
		super();
		this.deviceId = deviceId;
		this.name = name;
		this.unitName = unitName;
		this.value = value;
		this.increment = increment;
		this.lowerFatalThreshold = lowerFatalThreshold;
		this.lowerCriticalThreshold = lowerCriticalThreshold;
		this.upperCriticalThreshold = upperCriticalThreshold;
		this.upperFatalThreshold = upperFatalThreshold;
		this.readInterval = readInterval;
		this.healthCheckInterval = healthCheckInterval;
	}

	@Override
	public MeasurementDataDTO getNextMeasurement() {
		MeasurementDataDTO measurement = new MeasurementDataDTO();
		measurement.deviceId = deviceId;
		measurement.date = new Date();
		measurement.measurement = value;
		measurement.scale = 1;
		value += increment;
		return measurement;
	}

	@Override
	public String getCurrentThreshold() {
		if (value < lowerFatalThreshold) {
			return "LowFatal";
		} else if (value < lowerCriticalThreshold) {
			return "LowCritical";
		} else if (value < upperCriticalThreshold) {
			return "Normal";
		} else if (value < upperFatalThreshold) {
			return "HighCritical";
		} else {
			return "HighFatal";
		}
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
	public SensorDTO getDTO() {
		SensorDTO dto = new SensorDTO();
		dto.deviceId = deviceId;
		dto.name = name;
		dto.description = description;
		dto.protocol = protocol;
		dto.address = deviceId;
		dto.lowerFatalThreshold = lowerFatalThreshold;
		dto.lowerCriticalThreshold = lowerCriticalThreshold;
		dto.upperCriticalThreshold = upperCriticalThreshold;
		dto.upperFatalThreshold = upperFatalThreshold;
		dto.unit = unitName;
		dto.readInterval = readInterval;
		dto.healthCheckInterval = healthCheckInterval;
		dto.state = state;
		return dto;
	}

	@Override
	public String getAddress() {
		return deviceId;
	}
	
	@Override
	public String getId() {
		return deviceId;
	}
}
