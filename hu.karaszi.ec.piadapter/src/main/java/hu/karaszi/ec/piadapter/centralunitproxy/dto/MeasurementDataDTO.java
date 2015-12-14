package hu.karaszi.ec.piadapter.centralunitproxy.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class MeasurementDataDTO extends DeviceEventBase {
	@XmlElement public double measurement;
	@XmlElement public int scale;
}
