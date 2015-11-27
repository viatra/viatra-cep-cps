package hu.karaszi.ec.centralunit.data.dto.management;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MeasurementDTO {
	@XmlElement public Date timestamp;
	@XmlElement public double value;
	@XmlElement public int scale;
}
