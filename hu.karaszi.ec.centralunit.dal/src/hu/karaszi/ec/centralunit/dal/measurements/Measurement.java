package hu.karaszi.ec.centralunit.dal.measurements;

import hu.karaszi.ec.centralunit.dal.NamedEntity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Measurement extends NamedEntity {
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private double value;
	private int scale;
	@OneToOne
	private Unit unit;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
