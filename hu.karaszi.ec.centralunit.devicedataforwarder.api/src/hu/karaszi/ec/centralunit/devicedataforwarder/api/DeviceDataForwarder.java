package hu.karaszi.ec.centralunit.devicedataforwarder.api;

import java.util.Date;

public interface DeviceDataForwarder {		
	public void forwardOperationalStatus(String source, String status, Date date);
	public void forwardThresholdEvent(String source, String newRange, double measurement, int scale, String unit, Date date);
	public void forwardMeasurementData(String source, double measurement, int scale, String unit, Date date);
}
