package hu.karaszi.ec.centralunit.dal.devices;

import hu.karaszi.ec.centralunit.dal.measurements.Measurement;
import hu.karaszi.ec.centralunit.dal.measurements.Unit;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Sensor extends Device {
	private double lowerFatalThreshold;
	private double lowerCriticalThreshold;
	private double lowerNormalThreshold;
	private double upperNormalThreshold;
	private double upperCriticalThreshold;
	private double upperFatalThreshold;

	private double minReadable;
	private double maxReadable;

	private double hysteresis;
	@OneToOne
	private Unit unit;
	private int scale;
	@OneToMany
	private List<Measurement> measurements;

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

	public double getLowerNormalThreshold() {
		return lowerNormalThreshold;
	}

	public void setLowerNormalThreshold(double lowerNormalThreshold) {
		this.lowerNormalThreshold = lowerNormalThreshold;
	}

	public double getUpperNormalThreshold() {
		return upperNormalThreshold;
	}

	public void setUpperNormalThreshold(double upperNormalThreshold) {
		this.upperNormalThreshold = upperNormalThreshold;
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

	public double getHysteresis() {
		return hysteresis;
	}

	public void setHysteresis(double hysteresis) {
		this.hysteresis = hysteresis;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public List<Measurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<Measurement> measurements) {
		this.measurements = measurements;
	}
}
