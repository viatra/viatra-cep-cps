package hu.karaszi.ec.centralunit.dal.impl;

import hu.karaszi.ec.centralunit.dal.MeasurementDataManager;
import hu.karaszi.ec.centralunit.data.persistence.Measurement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class MeasurementDataManagerImpl implements MeasurementDataManager {
	private EntityManager em;
	
	public void setEntityManager(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	public void unsetEntityManager(EntityManagerFactory emf) {
		em = null;
	}
	
	@Override
	public List<Measurement> getMeasurements(long sensorId) {
		TypedQuery<Measurement> measurementQuery = em.createQuery("SELECT m FROM Measurement m  WHERE m.sensor = :sensorId", Measurement.class);
		return measurementQuery.setParameter("sensorId", sensorId).getResultList();
	}

	@Override
	public Measurement insertMeasurement(Measurement measurement) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.flush();
		em.persist(measurement);
		transaction.commit();
		return measurement;
	}

	@Override
	public Measurement getLastMeasurement(long sensorId) {
		TypedQuery<Measurement> measurementQuery = em.createQuery("SELECT m FROM Measurement m  WHERE m.sensor = :sensorId AND m.timestamp = MAX(m.timestamp)", Measurement.class);
		return measurementQuery.setParameter("sensorId", sensorId).getSingleResult();
	}

}
