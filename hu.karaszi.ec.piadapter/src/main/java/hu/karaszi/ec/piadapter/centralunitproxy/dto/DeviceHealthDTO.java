package hu.karaszi.ec.piadapter.centralunitproxy.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class DeviceHealthDTO extends DeviceEventBase {
	@XmlElement public String health;
}
