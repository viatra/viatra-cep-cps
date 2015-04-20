package hu.karaszi.ec.centralunit.dal.measurements;

import hu.karaszi.ec.centralunit.dal.NamedEntity;

import javax.persistence.Entity;

@Entity
public class Unit extends NamedEntity {
	private String unit;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
