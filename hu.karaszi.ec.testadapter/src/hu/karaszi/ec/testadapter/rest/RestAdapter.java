package hu.karaszi.ec.testadapter.rest;

import hu.karaszi.ec.centralunit.interfaces.devices.rest.RestDeviceDataReceiver;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestSensorManagement;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestUnitManagement;

public class RestAdapter implements Adapter {
	private SenderThread senderThread;
	//private RestDeviceDataReceiver rddr;
//	private WebTarget dataTarget;
	private RestSensorManagement restSensorManagement;
	private RestUnitManagement restUnitManagement;
	private RestDeviceDataReceiver restDeviceDataReceiver;

//	public void activate(ComponentContext context){
//		//RestSensorManagement rsm = JAXRSClientFactory.create("http://localhost:9090/management/sensor", RestSensorManagement.class);
//		//SensorDTO created = rsm.newSensor(sensor.getDTO());
////		Client client = ClientBuilder.newClient();
////		WebTarget target = client.target("http://localhost:8080/management/sensor");
////		SensorDTO created = target.request(MediaType.APPLICATION_JSON_TYPE)
////				.post(Entity.entity(sensor.getDTO(), MediaType.APPLICATION_JSON_TYPE), SensorDTO.class);
////		sensor.setId(created.id);
////		dataTarget = client.target("http://localhost:8080/receiver/device");
//		//rddr = JAXRSClientFactory.create("http://localhost:9090/receive", RestDeviceDataReceiver.class);	
//	}

	public void setRestSensorManagement(RestSensorManagement rsm) {
		restSensorManagement = rsm;
	}

	public void unsetRestSensorManagement(RestSensorManagement rsm) {
		restSensorManagement = null;
	}

	public void setRestUnitManagement(RestUnitManagement rum) {
		restUnitManagement = rum;
	}

	public void unsetRestUnitManagement(RestUnitManagement rum) {
		restUnitManagement = null;
	}	
	public void setRestDeviceDataReceiver(RestDeviceDataReceiver rddr) {
		restDeviceDataReceiver = rddr;
	}

	public void unsetRestDeviceDataReceiver(RestDeviceDataReceiver rddr) {
		restDeviceDataReceiver = null;
	}
	
	public void start() {
		stopSenderThread();
		senderThread = new SenderThread(restSensorManagement, restUnitManagement, restDeviceDataReceiver);
		senderThread.start();
	}
	
	public void stop() {
		stopSenderThread();
	}

	private void stopSenderThread() {
		if (senderThread != null) {
			senderThread.Deactivate();
		}
	}
}
