package hu.karaszi.ec.piadapter.centralunitproxy;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import com.eclipsesource.jaxrs.provider.gson.GsonProvider;

import hu.karaszi.ec.piadapter.centralunitproxy.dto.CommandDTO;
import hu.karaszi.ec.piadapter.centralunitproxy.dto.DeviceHealthDTO;
import hu.karaszi.ec.piadapter.centralunitproxy.dto.MeasurementDataDTO;
import hu.karaszi.ec.piadapter.centralunitproxy.dto.SensorDTO;
import hu.karaszi.ec.piadapter.centralunitproxy.dto.UnitDTO;
import hu.karaszi.ec.piadapter.conversion.CommandHandler;

public class HttpProxy implements RestDeviceDataReceiver, RestUnitManagement, RestSensorManagement {
	private static String centralUnitBaseAddress = "http://192.168.0.10:9090/services/";
	private static String sseAddress = "device/command/";
	private static String unitMgmtAddress = "unit";
	private static String sensorMgmtAddress = "sensor";
	private static String measurementAddress = "device/measurementdata";
	private static String statusAddress = "device/operationalstatus";
	
	private static String adapterName = "piadapter";
	
	private EventSource commandEventSource;
	private CommandHandler commandHandler;
	
	public HttpProxy(CommandHandler handler) {
		commandHandler = handler;
	}
	
	public void startConnection(){
		unregisterCommandListener();
		registerCommandListener();
	}
	
	private void registerCommandListener() {
		Client client = ClientBuilder.newBuilder().register(SseFeature.class).register(GsonProvider.class).build();
		WebTarget target = client.target(centralUnitBaseAddress + sseAddress + adapterName);
		commandEventSource = EventSource.target(target).build();
		EventListener listener = new EventListener() {
			@Override
			public void onEvent(InboundEvent inboundEvent) {
				CommandDTO command = inboundEvent.readData(CommandDTO.class, MediaType.APPLICATION_JSON_TYPE);
				commandHandler.handleCommand(command);
			}
		};
		commandEventSource.register(listener, "command");
		commandEventSource.open();
	}

	private void unregisterCommandListener() {
		if (commandEventSource != null) {
			commandEventSource.close();
		}
		commandEventSource = null;
	}
	
	@Override
	public SensorDTO newSensor(SensorDTO sensor) {
		Client client = ClientBuilder.newBuilder().register(GsonProvider.class).build();
		WebTarget target = client.target(centralUnitBaseAddress).path(sensorMgmtAddress);
		
		return target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(sensor)).readEntity(SensorDTO.class);
	}
	
	@Override
	public Response newUnit(UnitDTO unit) {
		Client client = ClientBuilder.newBuilder().register(GsonProvider.class).build();
		WebTarget target = client.target(centralUnitBaseAddress).path(unitMgmtAddress);
		
		return target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(unit));
	}

	@Override
	public void receiveMeasurementData(MeasurementDataDTO data) {
		Client client = ClientBuilder.newBuilder().register(GsonProvider.class).build();
		WebTarget target = client.target(centralUnitBaseAddress).path(measurementAddress);
		
		target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(data));
	}
	
	@Override
	public void receiveOperationalStatus(DeviceHealthDTO data) {
		Client client = ClientBuilder.newBuilder().register(GsonProvider.class).build();
		WebTarget target = client.target(centralUnitBaseAddress).path(statusAddress);
		
		target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(data));
	}
	
	////// dont care
	@Override
	public List<SensorDTO> getSensors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SensorDTO getSensor(String sensorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateSensor(String sensorId, SensorDTO sensor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteSensor(String sensorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnitDTO> getUnits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnitDTO getUnit(String unitId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateUnit(String unitId, UnitDTO unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteUnit(String unitId) {
		// TODO Auto-generated method stub
		return null;
	}
}
