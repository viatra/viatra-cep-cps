package hu.karaszi.ec.centralunit.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Sensor extends Device implements Serializable {
	private double lowerFatalThreshold;
	private double lowerCriticalThreshold;
	private double upperCriticalThreshold;
	private double upperFatalThreshold;
	private SensorRange currentRange; 
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastThresholdEventDate;
	private double minReadable;
	private double maxReadable;

	@ManyToOne
	private Unit unit;
	@OneToMany()
	private List<Measurement> measurements = new ArrayList<Measurement>();
	@ManyToMany
	private List<Actuator> affectedBy = new ArrayList<Actuator>();
	
	public double getLowerFatalThreshold() {
		return lowerFatalThreshold;
	}

	public void setLowerFatalThreshold(double lowerFatalThreshold) {
		this.lowerFatalThreshold = lowerFatalThreshold;
	}

	public double getLowerCriticalThreshold() {
		return lowerCriticalThreshold;
	}

	public void setLowerCriticalThreshold(double lowerCriticalThreshold) {
		this.lowerCriticalThreshold = lowerCriticalThreshold;
	}

	public double getUpperCriticalThreshold() {
		return upperCriticalThreshold;
	}

	public void setUpperCriticalThreshold(double upperCriticalThreshold) {
		this.upperCriticalThreshold = upperCriticalThreshold;
	}

	public double getUpperFatalThreshold() {
		return upperFatalThreshold;
	}

	public void setUpperFatalThreshold(double upperFatalThreshold) {
		this.upperFatalThreshold = upperFatalThreshold;
	}

	public double getMinReadable() {
		return minReadable;
	}

	public void setMinReadable(double minReadable) {
		this.minReadable = minReadable;
	}

	public double getMaxReadable() {
		return maxReadable;
	}

	public void setMaxReadable(double maxReadable) {
		this.maxReadable = maxReadable;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public List<Measurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<Measurement> measurements) {
		this.measurements = measurements;
	}
	
	public List<Actuator> getAffectedBy() {
		return affectedBy;
	}
	
	public void setAffectedBy(List<Actuator> affectedBy) {
		this.affectedBy = affectedBy;
	}

	public SensorRange getCurrentRange() {
		return currentRange;
	}

	public void setCurrentRange(SensorRange currentRange) {
		this.currentRange = currentRange;
	}

	public Date getLastThresholdEventDate() {
		return lastThresholdEventDate;
	}

	public void setLastThresholdEventDate(Date lastThresholdEventDate) {
		this.lastThresholdEventDate = lastThresholdEventDate;
	}
}
