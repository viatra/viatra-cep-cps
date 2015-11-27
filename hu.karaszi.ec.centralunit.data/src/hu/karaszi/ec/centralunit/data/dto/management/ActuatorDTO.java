package hu.karaszi.ec.centralunit.data.dto.management;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class ActuatorDTO extends DeviceDTO {
	@XmlElement public int performance;
	@XmlElement public String actuatorState;
	@XmlElement public String effect;
	@XmlElement public List<String> affects = new ArrayList<String>();
}
