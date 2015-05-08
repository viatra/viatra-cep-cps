package hu.karaszi.ec.centralunit.interfaces.management.rest.impl;

import hu.karaszi.ec.centralunit.dal.MeasurementDataManager;
import hu.karaszi.ec.centralunit.data.Measurement;
import hu.karaszi.ec.centralunit.interfaces.management.rest.RestMeasurementManagement;
import hu.karaszi.ec.centralunit.interfaces.management.rest.dto.MeasurementDTO;

import java.util.ArrayList;
import java.util.List;

public class RestMeasurementManagementImpl implements RestMeasurementManagement {
	private MeasurementDataManager measurementManager;
	
	public void setMeasurementDataManager(MeasurementDataManager mm) {
		measurementManager = mm;
	}

	public void unsetMeasurementDataManager(MeasurementDataManager dm) {
		measurementManager = null;
	}
	
	@Override
	public List<MeasurementDTO> getMeasurement(long sensorId) {
		List<Measurement> measurements = measurementManager.getMeasurements(sensorId);
		return measurementToDTOList(measurements);
	}

	private MeasurementDTO measurementToDTO(Measurement measurement){
		MeasurementDTO dto = new MeasurementDTO();
		dto.timestamp = measurement.getTimestamp();
		dto.value = measurement.getValue();
		dto.scale = measurement.getScale();
		return dto;
	}
	
	private List<MeasurementDTO> measurementToDTOList(List<Measurement> measurementList){
		List<MeasurementDTO> dtoList = new ArrayList<>();
		for (Measurement measurement : measurementList) {
			dtoList.add(measurementToDTO(measurement));
		}
		return dtoList;
	}
}
