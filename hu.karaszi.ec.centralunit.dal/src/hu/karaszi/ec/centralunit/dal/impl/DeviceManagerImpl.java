package hu.karaszi.ec.centralunit.dal.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.Sensor;

public class DeviceManagerImpl implements DeviceManager {
	EntityManager em;
	
	public void setEntityManager(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	public void unsetEntityManager(EntityManagerFactory emf) {
		em = null;
	}
	
	@Override
	public Device getDevice(String name) {
		Device device = getSensor(name);
		if (device == null) {
			TypedQuery<Actuator> actuatorQuery = em.createQuery("SELECT d FROM Sensor d WHERE d.name = :name", Actuator.class);
			device = actuatorQuery.setParameter("name", name).getSingleResult();
		}
		return device;
	}
	
	@Override
	public Sensor getSensor(long id) {
		return em.find(Sensor.class, id);
	}

	@Override
	public Actuator getActuator(long id) {
		return em.find(Actuator.class, id);
	}

	@Override
	public void updateSensor(Sensor sensor) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(sensor);
		transaction.commit();
	}

	@Override
	public void updateActuator(Actuator actuator) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(actuator);
		transaction.commit();
	}

	@Override
	public Sensor getSensor(String name) {
		TypedQuery<Sensor> sensorQuery = em.createQuery("SELECT d FROM Sensor d WHERE d.name = :name", Sensor.class);
		return sensorQuery.setParameter("name", name).getSingleResult();		
	}
}
