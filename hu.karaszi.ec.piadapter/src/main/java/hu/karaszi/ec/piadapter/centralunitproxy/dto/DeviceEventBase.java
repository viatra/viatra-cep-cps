package hu.karaszi.ec.piadapter.centralunitproxy.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public abstract class DeviceEventBase implements Serializable {
	@XmlElement public String deviceId;
	@XmlElement public Date date;
}
