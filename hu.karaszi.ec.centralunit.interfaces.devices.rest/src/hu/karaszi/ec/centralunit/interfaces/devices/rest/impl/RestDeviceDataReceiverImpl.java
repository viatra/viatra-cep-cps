package hu.karaszi.ec.centralunit.interfaces.devices.rest.impl;

import hu.karaszi.ec.centralunit.devicedataforwarder.api.DeviceDataForwarder;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.RestDeviceDataReceiver;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.MeasurementDataBean;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.OperationalStatusBean;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.ThresholdEventBean;

public class RestDeviceDataReceiverImpl implements RestDeviceDataReceiver {
	private DeviceDataForwarder forwarder;
	
	public void setDeviceDataForwarder(DeviceDataForwarder ddf) {
		forwarder = ddf;
	}
	
	public void unsetDeviceDataForwarder(DeviceDataForwarder ddf) {
		forwarder = null;
	}
	
	@Override
	public void receiveOperationalStatus(OperationalStatusBean data) {
		if (forwarder != null) {
			forwarder.forwardDeviceStatus(data.source, data.status, data.date);
		} else {
			//TODO: log missing service
		}
	}

	@Override
	public void receiveThresholdEvent(ThresholdEventBean data) {
		if (forwarder != null) {
			forwarder.forwardThresholdEvent(data.source, data.newRange, data.measurement, data.scale, data.unit, data.date);
		} else {
			//TODO: log missing service
		}
	}

	@Override
	public void receiveMeasurementData(MeasurementDataBean data) {
		if (forwarder != null) {
			forwarder.forwardMeasurementData(data.source, data.measurement, data.scale, data.unit, data.date);
		} else {
			//TODO: log missing service
		}
	}
	
	
}
