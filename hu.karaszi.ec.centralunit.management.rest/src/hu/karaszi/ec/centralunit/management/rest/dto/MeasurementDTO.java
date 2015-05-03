package hu.karaszi.ec.centralunit.management.rest.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MeasurementDTO {
	@XmlElement public Date timestamp;
	@XmlElement public double value;
	@XmlElement public int scale;
}
