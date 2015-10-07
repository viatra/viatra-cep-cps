package hu.karaszi.ec.testadapter.rest;

import java.util.UUID;

import hu.karaszi.ec.centralunit.interfaces.devices.rest.RestDeviceDataReceiver;
import hu.karaszi.ec.centralunit.interfaces.management.rest.dto.SensorDTO;
import hu.karaszi.ec.centralunit.interfaces.management.rest.dto.UnitDTO;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestSensorManagement;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestUnitManagement;
import hu.karaszi.ec.testadapter.testlogic.IncrementalSensor;
import hu.karaszi.ec.testadapter.testlogic.Sensor;

public class SenderThread extends Thread {
	private boolean active = true;
	private RestSensorManagement restSensorManagement;
	private RestDeviceDataReceiver restDeviceDataReceiver;
	private RestUnitManagement restUnitManagement;
	private Sensor sensor;
	
	public SenderThread(RestSensorManagement restSensorManagement, RestUnitManagement restUnitManagement, RestDeviceDataReceiver restDeviceDataReceiver) {
		this.restDeviceDataReceiver = restDeviceDataReceiver;
		this.restSensorManagement = restSensorManagement;
		this.restUnitManagement = restUnitManagement;
	}


	@Override
	public void run() {
		long unitId = createUnit();
		sensor = createSensor(unitId);
		while (active) {
			//rddr.receiveMeasurementData(sensor.getNextMeasurement());
//			dataTarget.path("measurementdata");
//			dataTarget.request(MediaType.APPLICATION_JSON_TYPE)
//				.post(Entity.entity(sensor.getNextMeasurement(), MediaType.APPLICATION_JSON_TYPE), MeasurementDataDTO.class);
			restDeviceDataReceiver.receiveMeasurementData(sensor.getNextMeasurement());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		deleteSensor(sensor);
	}
	
	private long createUnit() {
		try {
			UnitDTO unit = new UnitDTO();
			unit.name = "Temperature";
			unit.unit = "°C";
			unit = restUnitManagement.newUnit(unit);
			return unit.id;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 1;
	}


	private Sensor createSensor(long unitId){
		Sensor sensor = new IncrementalSensor("IncrementalRestTestSensor" + UUID.randomUUID(), unitId, -1, 1, 0, 2, 4, 6);
		SensorDTO created = restSensorManagement.newSensor(sensor.getDTO());
		sensor.setId(created.id);
		return sensor;
	}
	
	private void deleteSensor(Sensor sensor){
		restSensorManagement.deleteSensor(sensor.getId());
	}
	
	public void Deactivate(){
		active = false;
	}
}
