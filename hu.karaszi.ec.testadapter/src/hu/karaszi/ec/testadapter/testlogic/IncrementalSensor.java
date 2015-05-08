package hu.karaszi.ec.testadapter.testlogic;

import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.interfaces.management.rest.dto.SensorDTO;

import java.util.Date;

public class IncrementalSensor implements Sensor {
	private double value;
	private double increment;
	private double lowerFatalThreshold;
	private double lowerCriticalThreshold;
	private double upperCriticalThreshold;
	private double upperFatalThreshold;
	private String name;
	private long id;
	private long unitId;	
	
	public IncrementalSensor(String name, long unitId, double value, double increment, double lowerFatalThreshold,
			double lowerCriticalThreshold, double upperCriticalThreshold,
			double upperFatalThreshold) {
		super();
		this.name = name;
		this.unitId = unitId;
		this.value = value;
		this.increment = increment;
		this.lowerFatalThreshold = lowerFatalThreshold;
		this.lowerCriticalThreshold = lowerCriticalThreshold;
		this.upperCriticalThreshold = upperCriticalThreshold;
		this.upperFatalThreshold = upperFatalThreshold;
	}

	@Override
	public MeasurementDataDTO getNextMeasurement() {
		MeasurementDataDTO measurement = new MeasurementDataDTO();
		measurement.source = name;
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
	public String getOperationalStatus() {
		return "OK";
	}

	@Override
	public SensorDTO getDTO() {
		SensorDTO dto = new SensorDTO();
		dto.id = id;
		dto.name = name;
		dto.description = "incremental rest test sensor";
		dto.protocol = "not uset yet";
		dto.address = "not uset yet";
		dto.lowerFatalThreshold = lowerFatalThreshold;
		dto.lowerCriticalThreshold = lowerCriticalThreshold;
		dto.upperCriticalThreshold = upperCriticalThreshold;
		dto.upperFatalThreshold = upperFatalThreshold;
		dto.unit = unitId;
		return dto;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}
}
