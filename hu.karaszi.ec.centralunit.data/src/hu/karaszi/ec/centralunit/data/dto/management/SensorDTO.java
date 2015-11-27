package hu.karaszi.ec.centralunit.data.dto.management;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class SensorDTO extends DeviceDTO {
	@XmlElement public double lowerFatalThreshold;
	@XmlElement public double lowerCriticalThreshold;
	@XmlElement public double upperCriticalThreshold;
	@XmlElement public double upperFatalThreshold;
	
	@XmlElement public double minReadable;
	@XmlElement public double maxReadable;

	@XmlElement public long readInterval;

	@XmlElement public String unit;
	@XmlElement public List<String> affectedBy = new ArrayList<String>();
}
