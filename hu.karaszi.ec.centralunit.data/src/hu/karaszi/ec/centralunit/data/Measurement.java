package hu.karaszi.ec.centralunit.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Measurement implements Serializable {
	@Id
	@GeneratedValue
	protected long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private double value;
	private int scale;
	@ManyToOne
	private Sensor sensor;
	
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

	public long getId() {
		return id;
	}
	
	protected void setId(long id) {
		this.id = id;
	}
	
	public Sensor getSensor() {
		return sensor;
	}
	
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}
