package hu.karaszi.ec.piadapter.centralunitproxy.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommandDTO {
	@XmlElement public String command;
	@XmlElement public String deviceId;
	@XmlElement public String address;
}
