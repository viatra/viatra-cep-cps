package hu.karaszi.ec.centralunit.data.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hu.karaszi.ec.centralunit.data.dto.management.DeviceDTO;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class Device extends NamedEntity implements Serializable {
	@Column(unique = true)
	protected String deviceId;
	protected String protocol;
	protected String address;
	protected String description;
	protected DeviceHealth deviceHealth = DeviceHealth.UNKNOWN;
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastDeviceHealthDate;
	protected long healthCheckInterval;
	protected DeviceState deviceState = DeviceState.INACTIVE;
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastDeviceStateDate;

	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

		public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public DeviceHealth getDeviceHealth() {
		return deviceHealth;
	}
	
	public void setDeviceHealth(DeviceHealth deviceHealth) {
		this.deviceHealth = deviceHealth;
	}
	
	public DeviceState getDeviceState() {
		return deviceState;
	}
	
	public void setDeviceState(DeviceState deviceState) {
		this.deviceState = deviceState;
	}

	public Date getLastDeviceHealthDate() {
		return lastDeviceHealthDate;
	}

	public void setLastDeviceHealthDate(Date lastDeviceHealthDate) {
		this.lastDeviceHealthDate = lastDeviceHealthDate;
	}

	public long getHealthCheckInterval() {
		return healthCheckInterval;
	}
	
	public void setHealthCheckInterval(long healthCheckInterval) {
		this.healthCheckInterval = healthCheckInterval;
	}
	
	public Date getLastDeviceStateDate() {
		return lastDeviceStateDate;
	}

	public void setLastDeviceStateDate(Date lastDeviceStateDate) {
		this.lastDeviceStateDate = lastDeviceStateDate;
	}
	
	public DeviceDTO fillDTO(DeviceDTO dto){
		dto.deviceId = deviceId;
		dto.name = name;
		dto.address = address;
		dto.protocol = protocol;
		dto.description = description;
		dto.state = deviceState.name();
		dto.healthCheckInterval = healthCheckInterval;
		dto.health = deviceHealth.name();
		return dto;
	}
}
