package hu.karaszi.ec.centralunit.interfaces.management.rest.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActuatorDTO {
	@XmlElement public long id;
	@XmlElement public String name;
	@XmlElement public String protocol;
	@XmlElement public String address;
	@XmlElement public String description;
	
	@XmlElement public int performance;
	@XmlElement public String state;
	@XmlElement public String effect;
	@XmlElement public List<Long> affects = new ArrayList<Long>();
}
