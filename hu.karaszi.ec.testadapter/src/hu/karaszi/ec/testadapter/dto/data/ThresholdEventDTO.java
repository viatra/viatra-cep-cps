package hu.karaszi.ec.testadapter.dto.data;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ThresholdEventDTO {
	@XmlElement public String source;
	@XmlElement public String newRange;
	@XmlElement public double measurement;
	@XmlElement public int scale;
	@XmlElement public String unit;
	@XmlElement public Date date;
}
