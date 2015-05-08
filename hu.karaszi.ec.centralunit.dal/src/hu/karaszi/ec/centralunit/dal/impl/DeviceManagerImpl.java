package hu.karaszi.ec.centralunit.dal.impl;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.Measurement;
import hu.karaszi.ec.centralunit.data.Sensor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DeviceManagerImpl implements DeviceManager {
	private EntityManager em;
	
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
	public Sensor updateSensor(Sensor sensor) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(sensor);
		transaction.commit();
		return sensor;
	}

	@Override
	public Actuator updateActuator(Actuator actuator) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(actuator);
		transaction.commit();
		return actuator;
	}

	@Override
	public Sensor getSensor(String name) {
		TypedQuery<Sensor> sensorQuery = em.createQuery("SELECT d FROM Sensor d WHERE d.name = :name", Sensor.class);
		return sensorQuery.setParameter("name", name).getSingleResult();		
	}

	@Override
	public List<Sensor> getSensors() {
		TypedQuery<Sensor> query = em.createQuery("SELECT s FROM Sensor s", Sensor.class);
		return query.getResultList();
	}

	@Override
	public Sensor insertSensor(Sensor sensor) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(sensor);
		transaction.commit();
		return sensor;
	}

	@Override
	public void deleteSensor(long id) {
		Sensor sensor = em.find(Sensor.class, id);
		EntityTransaction transaction = em.getTransaction();
		if (sensor != null) {
			transaction.begin();
			TypedQuery<Measurement> deleteMeasurements = em.createQuery("DELETE FROM Measurement m WHERE m.sensorId = :sensorId", Measurement.class);
			deleteMeasurements.setParameter("sensorId", id).executeUpdate();
			em.remove(sensor);
			transaction.commit();
		}
	}

	@Override
	public List<Actuator> getActuators() {
		TypedQuery<Actuator> query = em.createQuery("SELECT a FROM Actuator a", Actuator.class);
		return query.getResultList();
	}

	@Override
	public Actuator insertActuator(Actuator actuator) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(actuator);
		transaction.commit();
		return actuator;
	}

	@Override
	public void deleteActuator(long id) {
		Actuator actuator = em.find(Actuator.class, id);
		EntityTransaction transaction = em.getTransaction();
		if (actuator != null) {
			transaction.begin();
			em.remove(actuator);
			transaction.commit();
		}
	}

	@Override
	public Device updateDevice(Device device) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(device);
		transaction.commit();
		return device;
	}
}
