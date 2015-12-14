package hu.karaszi.ec.piadapter.conversion;

import java.util.Date;

import hu.karaszi.ec.piadapter.adc.ADCReader;
import hu.karaszi.ec.piadapter.centralunitproxy.HttpProxy;
import hu.karaszi.ec.piadapter.centralunitproxy.dto.CommandDTO;
import hu.karaszi.ec.piadapter.centralunitproxy.dto.DeviceHealthDTO;
import hu.karaszi.ec.piadapter.centralunitproxy.dto.MeasurementDataDTO;
import hu.karaszi.ec.piadapter.centralunitproxy.dto.SensorDTO;
import hu.karaszi.ec.piadapter.centralunitproxy.dto.UnitDTO;

public class Adapter implements CommandHandler {
	private HttpProxy centralUnitProxy;
	private ADCReader adcReader;
	
	public void initialize() {
		System.out.println("Initializing adapter...");
		System.out.println("Initializing ADC...");
		adcReader = new ADCReader();
		adcReader.initADC();
		testAdc();
		
		System.out.println("Initializing Http client...");
		centralUnitProxy = new HttpProxy(this);
		centralUnitProxy.startConnection();
		
		System.out.println("Creating temperature unit...");
		String unitName = createUnit();
		
		System.out.println("Creating sensor...");
		SensorDTO sensor = new SensorDTO();
		sensor.address = "1";
		sensor.description = "Raspberry Pi analog temp sensor";
		sensor.deviceId = "rasPiTempSensor1";
		sensor.name = "Raspberry Pi analog temperature sensor";
		sensor.protocol = "piadapter";
		sensor.unit = unitName;
		
		sensor.readInterval = 4000;
		sensor.healthCheckInterval = 30000;
		sensor.health = "OK";
		sensor.state = "ACTIVE";
		
		sensor.lowerFatalThreshold = 15;
		sensor.lowerCriticalThreshold = 20;
		sensor.upperCriticalThreshold = 25;
		sensor.upperFatalThreshold = 30;
		sensor.minReadable = 10;
		sensor.maxReadable = 40;
		
		
		centralUnitProxy.newSensor(sensor);
		System.out.println("Initializing adapter done");
	}
	
	public void dispose(){
		adcReader.shutdownADC();
		//TODO: clean
	}
	
	@Override
	public void handleCommand(CommandDTO command) {
		switch (command.command) {
		case "read-measurement":
			System.out.println(new Date() + " Sensor read request arrived");
			readSensor(command);
			break;
		case "read-health":					
			System.out.println(new Date() + " Sensor status request arrived");
			readDeviceHealth(command);
			break;
		default:
			System.out.println("Received command: " + command.command + " for " + command.address);
			break;
		}
	}
	
	private String createUnit() {
		try {
			UnitDTO unit = new UnitDTO();
			unit.name = "Temperature";
			unit.unit = "°C";
			centralUnitProxy.newUnit(unit);
			return unit.name;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	private void readSensor(CommandDTO command){
		int voltage = adcReader.readAdc(Integer.parseInt(command.address));
		
		MeasurementDataDTO measurement = new MeasurementDataDTO();
		measurement.date = new Date();
		measurement.deviceId = command.deviceId;
		measurement.scale = 1;
		measurement.measurement = voltageToMeasurement(voltage);
		
		System.out.println("Measured: " + measurement.measurement + " °C (raw data: " + voltage + ")");
		centralUnitProxy.receiveMeasurementData(measurement);
	}

	private void readDeviceHealth(CommandDTO command){
		DeviceHealthDTO status = new DeviceHealthDTO();
		status.date = new Date();
		status.deviceId = command.deviceId;
		status.health = "OK";
		
		centralUnitProxy.receiveOperationalStatus(status);
	}
	
	private double voltageToMeasurement(int rawVoltage) {
		double milivolts = ((double)rawVoltage / 1024.0) * 5000;
		double kelvin = milivolts / 10.0;
		double celsius = kelvin - 273.15;
		return celsius;
	}
	
	private void testAdc(){
		System.out.println("ADC test starting...");
		for (int i = 0; i < 8; i++) {
			int voltage = adcReader.readAdc(i);
			System.out.println("Channel " + i + " voltage: " + voltage);
		}
		System.out.println("ADC test finished");
	}
}
