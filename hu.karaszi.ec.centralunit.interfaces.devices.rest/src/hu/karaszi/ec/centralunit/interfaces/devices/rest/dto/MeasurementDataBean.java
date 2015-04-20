package hu.karaszi.ec.centralunit.interfaces.devices.rest.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MeasurementDataBean {
	@XmlElement public String source;
	@XmlElement public double measurement;
	@XmlElement public int scale;
	@XmlElement public String unit;
	@XmlElement public Date date;
}
