package hu.karaszi.ec.testadapter.dto.management;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UnitDTO {
	@XmlElement public long id;
	@XmlElement public String name;
	@XmlElement public String unit;
}
