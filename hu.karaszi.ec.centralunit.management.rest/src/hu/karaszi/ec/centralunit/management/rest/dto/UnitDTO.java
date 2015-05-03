package hu.karaszi.ec.centralunit.management.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UnitDTO {
	@XmlElement public long id;
	@XmlElement public String name;
	@XmlElement public String unit;
}
