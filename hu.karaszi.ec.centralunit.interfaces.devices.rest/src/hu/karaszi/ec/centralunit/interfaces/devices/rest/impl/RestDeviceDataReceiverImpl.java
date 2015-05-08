package hu.karaszi.ec.centralunit.interfaces.devices.rest.impl;

import hu.karaszi.ec.centralunit.event.forwarder.api.DeviceEventForwarder;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.RestDeviceDataReceiver;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.OperationalStatusDTO;
import hu.karaszi.ec.centralunit.interfaces.devices.rest.dto.ThresholdEventDTO;

public class RestDeviceDataReceiverImpl implements RestDeviceDataReceiver {
	private DeviceEventForwarder forwarder;
	
	public void setDeviceEventForwarder(DeviceEventForwarder def) {
		forwarder = def;
	}
	
	public void unsetDeviceEventForwarder(DeviceEventForwarder def) {
		forwarder = null;
	}
	
	@Override
	public void receiveOperationalStatus(OperationalStatusDTO data) {
		if (forwarder != null) {
			forwarder.forwardDeviceStatus(data.source, data.status, data.date);
		} else {
			//TODO: log missing service
		}
	}

	@Override
	public void receiveThresholdEvent(ThresholdEventDTO data) {
		if (forwarder != null) {
			forwarder.forwardThresholdEvent(data.source, data.newRange, data.measurement, data.scale, data.date);
		} else {
			//TODO: log missing service
		}
	}

	@Override
	public void receiveMeasurementData(MeasurementDataDTO data) {
		if (forwarder != null) {
			forwarder.forwardMeasurementData(data.source, data.measurement, data.scale, data.date);
		} else {
			//TODO: log missing service
		}
	}
	
	
}
