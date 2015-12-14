package hu.karaszi.ec.piadapter.centralunitproxy.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class UnitDTO implements Serializable {
	@XmlElement public String name;
	@XmlElement public String unit;
}
