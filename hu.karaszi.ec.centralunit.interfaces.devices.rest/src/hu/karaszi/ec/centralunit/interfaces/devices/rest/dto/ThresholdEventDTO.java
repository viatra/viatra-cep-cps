package hu.karaszi.ec.centralunit.interfaces.devices.rest.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ThresholdEventDTO {
	@XmlElement public String source;
	@XmlElement public String newRange;
	@XmlElement public double measurement;
	@XmlElement public int scale;
	@XmlElement public Date date;
}
