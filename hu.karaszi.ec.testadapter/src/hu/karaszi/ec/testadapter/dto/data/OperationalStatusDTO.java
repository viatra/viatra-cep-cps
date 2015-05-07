package hu.karaszi.ec.testadapter.dto.data;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OperationalStatusDTO {
	@XmlElement public String source;
	@XmlElement public String status;
	@XmlElement public Date date;
}
