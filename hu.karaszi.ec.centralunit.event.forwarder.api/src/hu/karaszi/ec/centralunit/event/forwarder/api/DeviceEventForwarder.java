package hu.karaszi.ec.centralunit.event.forwarder.api;

import java.util.Date;

public interface DeviceEventForwarder {		
	public void forwardDeviceStatus(String source, String status, Date date);
	public void forwardThresholdEvent(String source, String newRange, double measurement, int scale, Date date);
	public void forwardMeasurementData(String source, double measurement, int scale, Date date);
}
