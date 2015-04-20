package hu.karaszi.ec.centralunit.devicedataforwarder.impl.rabbitmq;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import hu.karaszi.ec.centralunit.devicedataforwarder.api.DeviceDataForwarder;

public class RabbitMQDeviceDataForwarder implements DeviceDataForwarder {
private EntityManager em;
	
	public void setEntityManager(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}
	
	public void unsetEntityManager(EntityManagerFactory emf) {
		em = null;
	}
	
	@Override
	public void forwardOperationalStatus(String source, String status, Date date) {
		// TODO Auto-generated method stub
		System.out.println(date.toString() + " --- Received operational status from " + source +": " + status);
	}

	@Override
	public void forwardThresholdEvent(String source, String newRange,
			double measurement, int scale, String unit, Date date) {
		// TODO Auto-generated method stub

	}

	@Override
	public void forwardMeasurementData(String source, double measurement,
			int scale, String unit, Date date) {
		// TODO Auto-generated method stub

	}

}
