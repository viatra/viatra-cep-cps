package hu.karaszi.ec.centralunit.dal;

import java.util.List;

import hu.karaszi.ec.centralunit.data.persistence.Measurement;

public interface MeasurementDataManager {
	public List<Measurement> getMeasurements(long sensorId);
	public Measurement insertMeasurement(Measurement measurement);
	public Measurement getLastMeasurement(long sensorId);
}
