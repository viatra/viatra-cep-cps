package hu.karaszi.ec.centralunit.data.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hu.karaszi.ec.centralunit.data.dto.management.ActuatorDTO;

@SuppressWarnings("serial")
@Entity
public class Actuator extends Device implements Serializable {
	private int performance;
	private ActuatorState state;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastActuatorStateDate;
	@ManyToMany
	private List<Sensor> affects = new ArrayList<Sensor>();
	private ActuatorEffect effect;
	
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

	public ActuatorEffect getEffect() {
		return effect;
	}

	public void setEffect(ActuatorEffect effect) {
		this.effect = effect;
	}

	public Date getLastActuatorStateDate() {
		return lastActuatorStateDate;
	}

	public void setLastActuatorStateDate(Date lastActuatorStateDate) {
		this.lastActuatorStateDate = lastActuatorStateDate;
	}
	
	public ActuatorDTO toDTO(){
		ActuatorDTO dto = new ActuatorDTO();
		fillDTO(dto);
		dto.effect = effect.name();
		dto.actuatorState = state.name();
		dto.performance = performance;
		dto.affects = new ArrayList<>();
		for (Sensor sensor : affects) {
			dto.affects.add(sensor.deviceId);
		}
		return dto;
	}
}
