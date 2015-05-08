package hu.karaszi.ec.centralunit.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class Device extends NamedEntity implements Serializable {
	protected String protocol;
	protected String address;
	protected String description;
	protected DeviceHealth deviceHealth;
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastDeviceHealthDate;
	protected DeviceState deviceState;
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastDeviceStateDate;

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

	public Date getLastDeviceStateDate() {
		return lastDeviceStateDate;
	}

	public void setLastDeviceStateDate(Date lastDeviceStateDate) {
		this.lastDeviceStateDate = lastDeviceStateDate;
	}
}
