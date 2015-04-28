package hu.karaszi.ec.centralunit.devicedataprocessor.api;

import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.Measurement;
import hu.karaszi.ec.centralunit.data.Sensor;

public interface DeviceDataProcessor {
	public void processDeviceStatus(Device device);
	public void processThresholdEvent(Sensor sensor);
	public void processMeasurementData(Measurement measurement);
}
