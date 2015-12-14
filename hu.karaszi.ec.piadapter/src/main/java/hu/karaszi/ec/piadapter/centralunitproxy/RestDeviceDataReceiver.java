package hu.karaszi.ec.piadapter.centralunitproxy;

import hu.karaszi.ec.piadapter.centralunitproxy.dto.DeviceHealthDTO;
import hu.karaszi.ec.piadapter.centralunitproxy.dto.MeasurementDataDTO;

public interface RestDeviceDataReceiver {
	public void receiveOperationalStatus(final DeviceHealthDTO data);
	public void receiveMeasurementData(final MeasurementDataDTO data);
}
