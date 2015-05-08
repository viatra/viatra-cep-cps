package hu.karaszi.ec.centralunit.dal.impl;

import hu.karaszi.ec.centralunit.dal.UnitManager;
import hu.karaszi.ec.centralunit.data.Unit;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class UnitManagerImpl implements UnitManager {
	private EntityManager em;
	
	public void setEntityManager(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	public void unsetEntityManager(EntityManagerFactory emf) {
		em = null;
	}
	@Override
	public List<Unit> getUnits() {
		TypedQuery<Unit> query = em.createQuery("SELECT u FROM Unit u", Unit.class);
		return query.getResultList();
	}

	@Override
	public Unit getUnit(String name) {
		TypedQuery<Unit> unitQuery = em.createQuery("SELECT u FROM Unit u WHERE u.name = :name", Unit.class);
		return unitQuery.setParameter("name", name).getSingleResult();
	}

	@Override
	public Unit getUnit(long id) {
		return em.find(Unit.class, id);
	}

	@Override
	public Unit insertUnit(Unit unit) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(unit);
		transaction.commit();
		return unit;
	}

	@Override
	public Unit updateUnit(Unit unit) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(unit);
		transaction.commit();
		return unit;
	}

	@Override
	public void deleteUnit(long unitId) {
		Unit unit = em.find(Unit.class, unitId);
		EntityTransaction transaction = em.getTransaction();
		if (unit != null) {
			transaction.begin();
			em.remove(unit);
			transaction.commit();
		}
	}
}
