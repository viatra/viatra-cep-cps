package hu.karaszi.ec.centralunit.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@SuppressWarnings("serial")
@Entity
public class Actuator extends Device {
	private int performance;
	private ActuatorState state;
	@ManyToMany
	private List<Sensor> affects = new ArrayList<Sensor>();
	
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
	
	public List<Sensor> getAffects() {
		return affects;
	}
}
