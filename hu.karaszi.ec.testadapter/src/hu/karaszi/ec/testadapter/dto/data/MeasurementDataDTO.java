package hu.karaszi.ec.testadapter.dto.data;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MeasurementDataDTO {
	@XmlElement public String source;
	@XmlElement public double measurement;
	@XmlElement public int scale;
	@XmlElement public Date date;
}
