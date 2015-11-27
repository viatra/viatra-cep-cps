package hu.karaszi.ec.centralunit.dal.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.data.persistence.Actuator;
import hu.karaszi.ec.centralunit.data.persistence.Device;
import hu.karaszi.ec.centralunit.data.persistence.Measurement;
import hu.karaszi.ec.centralunit.data.persistence.Sensor;

public class DeviceManagerImpl implements DeviceManager {
	private EntityManagerFactory emf;

	public void setEntityManager(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public void unsetEntityManager(EntityManagerFactory emf) {
		emf = null;
	}

	@Override
	public Device getDevice(String deviceId) {
		Device device = getSensor(deviceId);
		if (device == null) {
			device = getActuator(deviceId);
		}
		return device;
	}

	@Override
	public Sensor getSensor(long id) {
		EntityManager em = emf.createEntityManager();
		Sensor sensor = em.find(Sensor.class, id);
		em.close();
		return sensor;
	}

	@Override
	public Actuator getActuator(long id) {
		EntityManager em = emf.createEntityManager();
		Actuator actuator = em.find(Actuator.class, id);
		em.close();
		return actuator;
	}

	@Override
	public Sensor updateSensor(Sensor sensor) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(sensor);
		em.getTransaction().commit();
		em.close();
		return sensor;
	}

	@Override
	public Actuator updateActuator(Actuator actuator) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(actuator);
		em.getTransaction().commit();
		em.close();
		return actuator;
	}

	@Override
	public Sensor getSensor(String deviceId) {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Sensor> sensorQuery = em.createQuery("SELECT d FROM Sensor d WHERE d.deviceId = :deviceId",
					Sensor.class);
			return sensorQuery.setParameter("deviceId", deviceId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	@Override
	public List<Sensor> getSensors() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Sensor> query = em.createQuery("SELECT s FROM Sensor s", Sensor.class);
		List<Sensor> resultList = query.getResultList();
		em.close();
		return resultList;
	}

	@Override
	public Sensor insertSensor(Sensor sensor) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(sensor);
		em.getTransaction().commit();
		em.close();
		return sensor;
	}

	@Override
	public void deleteSensor(long id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Sensor sensor = em.find(Sensor.class, id);
		TypedQuery<Measurement> deleteMeasurements = em
				.createQuery("DELETE FROM Measurement m WHERE m.sensor = :sensor", Measurement.class);
		deleteMeasurements.setParameter("sensor", sensor).executeUpdate();
		em.remove(sensor);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void deleteSensor(String deviceId) {
		Sensor sensor = getSensor(deviceId);
		deleteSensor(sensor.getId());
	}

	@Override
	public List<Actuator> getActuators() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Actuator> query = em.createQuery("SELECT a FROM Actuator a", Actuator.class);
		List<Actuator> resultList = query.getResultList();
		em.close();
		return resultList;
	}

	@Override
	public Actuator insertActuator(Actuator actuator) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(actuator);
		em.getTransaction().commit();
		em.close();
		return actuator;
	}

	@Override
	public Device updateDevice(Device device) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(device);
		em.getTransaction().commit();
		em.close();
		return device;
	}

	@Override
	public Actuator getActuator(String deviceId) {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Actuator> actuatorQuery = em.createQuery("SELECT d FROM Actuator d WHERE d.deviceId = :deviceId",
					Actuator.class);
			return actuatorQuery.setParameter("deviceId", deviceId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteActuator(long id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Actuator actuator = em.find(Actuator.class, id);
		em.remove(actuator);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void deleteActuator(String deviceId) {
		Actuator actuator = getActuator(deviceId);
		deleteActuator(actuator.getId());
	}
}
