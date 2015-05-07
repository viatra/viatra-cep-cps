package hu.karaszi.ec.testadapter.dto.management;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SensorDTO {
	@XmlElement public long id;
	@XmlElement public String name;
	@XmlElement public String protocol;
	@XmlElement public String address;
	@XmlElement public String description;
	
	@XmlElement public double lowerFatalThreshold;
	@XmlElement public double lowerCriticalThreshold;
	@XmlElement public double upperCriticalThreshold;
	@XmlElement public double upperFatalThreshold;
	
	@XmlElement public double minReadable;
	@XmlElement public double maxReadable;

	@XmlElement public double hysteresis;
	@XmlElement public long unit;
	@XmlElement public List<Long> affectedBy = new ArrayList<Long>();
}
