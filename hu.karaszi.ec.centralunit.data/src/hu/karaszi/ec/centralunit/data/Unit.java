package hu.karaszi.ec.centralunit.data;

import java.io.Serializable;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Unit extends NamedEntity implements Serializable {
	private String unit;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
