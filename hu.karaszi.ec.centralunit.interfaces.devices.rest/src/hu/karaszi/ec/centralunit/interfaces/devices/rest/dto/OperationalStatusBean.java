package hu.karaszi.ec.centralunit.interfaces.devices.rest.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OperationalStatusBean {
	@XmlElement public String source;
	@XmlElement public String status;
	@XmlElement public Date date;
}
