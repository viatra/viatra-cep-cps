package hu.karaszi.ec.testadapter.testlogic;

import java.util.Date;

import hu.karaszi.ec.testadapter.dto.data.MeasurementDataDTO;
import hu.karaszi.ec.testadapter.dto.management.SensorDTO;

public class IncrementalSensor implements Sensor {
	private double value;
	private double increment;
	private double lowerFatalThreshold;
	private double lowerCriticalThreshold;
	private double upperCriticalThreshold;
	private double upperFatalThreshold;
	private String name;
	private long id;	
	
	public IncrementalSensor(String name, double value, double increment, double lowerFatalThreshold,
			double lowerCriticalThreshold, double upperCriticalThreshold,
			double upperFatalThreshold) {
		super();
		this.name = name;
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
		dto.unit = 1;
		return dto;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}
}
