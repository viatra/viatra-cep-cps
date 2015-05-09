package hu.karaszi.ec.centralunit.dal;

import hu.karaszi.ec.centralunit.data.Measurement;

import java.util.List;

public interface MeasurementDataManager {
	public List<Measurement> getMeasurements(long sensorId);
	public Measurement insertMeasurement(Measurement measurement);
	public Measurement getLastMeasurement(long sensorId);
}
