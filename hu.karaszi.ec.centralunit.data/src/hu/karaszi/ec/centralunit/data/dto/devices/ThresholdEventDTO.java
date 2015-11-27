package hu.karaszi.ec.centralunit.data.dto.devices;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class ThresholdEventDTO extends MeasurementDataDTO {
	@XmlElement public String newRange;
}
