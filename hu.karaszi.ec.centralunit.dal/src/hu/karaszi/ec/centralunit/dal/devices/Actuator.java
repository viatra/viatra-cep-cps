package hu.karaszi.ec.centralunit.dal.devices;

import javax.persistence.Entity;

@Entity
public class Actuator extends Device {
	private int performance;
	private ActuatorState state;

	public int getPerformance() {
		return performance;
	}

	public void setPerformance(int performance) {
		this.performance = performance;
	}

	public ActuatorState getState() {
		return state;
	}

	public void setState(ActuatorState state) {
		this.state = state;
	}
}
