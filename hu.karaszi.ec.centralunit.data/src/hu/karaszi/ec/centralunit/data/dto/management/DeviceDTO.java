package hu.karaszi.ec.centralunit.data.dto.management;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public abstract class DeviceDTO implements Serializable {
	@XmlElement public String deviceId;
	@XmlElement public String name;
	@XmlElement public String protocol;
	@XmlElement public String address;
	@XmlElement public String description;
	@XmlElement public long healthCheckInterval;
	@XmlElement public String health;
	@XmlElement public String state;
}
