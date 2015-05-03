package hu.karaszi.ec.centralunit.dal;

import hu.karaszi.ec.centralunit.data.Unit;

import java.util.List;

public interface UnitManager {
	public List<Unit> getUnits();
	public Unit getUnit(String name);
	public Unit getUnit(long id);
	public void insertUnit(Unit unit);
	public void updateUnit(Unit unit);
	public void deleteUnit(long id);
}
