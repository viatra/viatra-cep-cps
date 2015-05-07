package hu.karaszi.ec.testadapter.rest;

import hu.karaszi.ec.testadapter.dto.data.MeasurementDataDTO;
import hu.karaszi.ec.testadapter.dto.management.SensorDTO;
import hu.karaszi.ec.testadapter.rest.proxy.RestDeviceDataReceiver;
import hu.karaszi.ec.testadapter.rest.proxy.RestSensorManagement;
import hu.karaszi.ec.testadapter.testlogic.IncrementalSensor;
import hu.karaszi.ec.testadapter.testlogic.Sensor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.ComponentContext;

public class RestAdapter implements Adapter {
	Sensor sensor;
	boolean active = true;
	//RestDeviceDataReceiver rddr;
	WebTarget dataTarget;
	
	public void activate(ComponentContext context){
		sensor = new IncrementalSensor("IncrementalRestTestSensor1", -1, 1, 0, 2, 4, 6);
		
		//RestSensorManagement rsm = JAXRSClientFactory.create("http://localhost:9090/management/sensor", RestSensorManagement.class);
		//SensorDTO created = rsm.newSensor(sensor.getDTO());
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/management/sensor");
		SensorDTO created = target.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(sensor.getDTO(), MediaType.APPLICATION_JSON_TYPE), SensorDTO.class);
		sensor.setId(created.id);
		dataTarget = client.target("http://localhost:8080/receiver/device");
		//rddr = JAXRSClientFactory.create("http://localhost:9090/receive", RestDeviceDataReceiver.class);
	}

	public void start() {
		while (active) {
			//rddr.receiveMeasurementData(sensor.getNextMeasurement());
			dataTarget.path("measurementdata");
			dataTarget.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(sensor.getNextMeasurement(), MediaType.APPLICATION_JSON_TYPE), MeasurementDataDTO.class);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public void stop() {
		active = false;
	}
}
