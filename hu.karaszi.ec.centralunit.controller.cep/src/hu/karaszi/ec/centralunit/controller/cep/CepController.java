package hu.karaszi.ec.centralunit.controller.cep;

import java.util.Date;

import org.eclipse.viatra.cep.core.api.helpers.DefaultApplication;
import org.eclipse.viatra.cep.core.metamodels.automaton.EventContext;
import org.osgi.service.component.ComponentContext;

import hu.karaszi.ec.centralunit.controller.cep.eventmodel.CepFactory;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.ActuatorStateArrived_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.DeviceHealthArrived_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.MeasurementArrived_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.mapping.QueryEngine2ViatraCep;
import hu.karaszi.ec.centralunit.controller.cep.instancemodel.InstanceModel;
import hu.karaszi.ec.centralunit.controller.cep.tickgenerator.TickGeneratorThread;
import hu.karaszi.ec.centralunit.controller.cep.workaround.ControlWorkaroundEventHandler;
import hu.karaszi.ec.centralunit.controller.cep.workaround.PossibleThresholdEventHandler;
import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.dal.MeasurementDataManager;
import hu.karaszi.ec.centralunit.data.dto.devices.ActuatorStateDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.DeviceHealthDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.MeasurementDataDTO;
import hu.karaszi.ec.centralunit.data.dto.devices.ThresholdEventDTO;
import hu.karaszi.ec.centralunit.data.dto.management.ActuatorDTO;
import hu.karaszi.ec.centralunit.data.dto.management.SensorDTO;
import hu.karaszi.ec.centralunit.event.consumer.api.EventConsumer;
import hu.karaszi.ec.centralunit.event.consumer.rabbitmq.RabbitMQEventConsumer;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventForwarder;
import hu.karaszi.ec.centralunit.event.processor.api.EventProcessor;
import hu.karaszi.ec.centralunit.interfaces.devices.internal.DeviceCommandSender;

public class CepController extends DefaultApplication implements EventProcessor {
	private static final int TICK_INTERVAL = 1000;
	private final String[] topics = {"DATABASE.#", "DEVICE.#", "INTERNAL.TICK"};
	private EventConsumer consumer = new RabbitMQEventConsumer();

	private QueryEngine2ViatraCep mapping;
	private InstanceModel systemModel = new InstanceModel();
	private TickGeneratorThread tickGenerator;

	private DeviceManager deviceManager;
	private MeasurementDataManager measurementManager;
	private EventForwarder eventForwarder;
	
	public void setDeviceManager(DeviceManager dm) {
		deviceManager = dm;
	}

	public void unsetDeviceManager(DeviceManager dm) {
		deviceManager = null;
	}
	
	public void setMeasurementDataManager(MeasurementDataManager mm) {
		measurementManager = mm;
	}

	public void unsetMeasurementDataManager(MeasurementDataManager mm) {
		measurementManager = null;
	}
	
	public void setEventForwarder(EventForwarder tf) {
		eventForwarder = tf;
		startTickGenerator();
	}

	public void unsetEventForwarder(EventForwarder tf) {
		stopTickGenerator();
		eventForwarder = null;
	}
	
	public void setDeviceCommandSender(DeviceCommandSender dcs) {
		ScheduleEventHandler.setDcSender(dcs);
	}

	public void unsetDeviceCommandSender(DeviceCommandSender dcs) {
		ScheduleEventHandler.setDcSender(null);
	}
	
	public CepController() {
		super(EventContext.CHRONICLE, CepFactory.getInstance().allRules());
	}

	protected void activate(ComponentContext context) {
		getEngine().getLogger().debug("Starting");
		systemModel.prepareModel(deviceManager, measurementManager);
		mapping = QueryEngine2ViatraCep.register(systemModel.getResourceSet(), getEventStream());
		
		//setting fields for handlers
		EventHandlerBase.setEventstream(getEventStream());
		EventHandlerBase.setEventForwarder(eventForwarder);
		EventHandlerBase.setSensors(systemModel.getSensors());
		EventHandlerBase.setActuators(systemModel.getActuators());
		
		//workaroud classes
		PossibleThresholdEventHandler.setEventstream(getEventStream());
		ControlWorkaroundEventHandler.setEventstream(getEventStream());
		
		consumer.activate(this, topics);
	}
	
	protected void deactivate(ComponentContext context) {
		stopTickGenerator();
		mapping.dispose();
		getEngine().getLogger().debug("Ending");
	}
	
	private void startTickGenerator() {
		stopTickGenerator();
		tickGenerator = new TickGeneratorThread(TICK_INTERVAL, eventForwarder);
		tickGenerator.start();
	}

	private void stopTickGenerator() {
		if (tickGenerator != null) {
			tickGenerator.interrupt();
		}
		tickGenerator = null;
	}

	@Override
	public void handleEvent(Object message, String eventType) {
		switch (eventType) {
		case "TICK":
			processTick((Date) message);
			break;
		case "MEASUREMENT":
			processMeasurementData((MeasurementDataDTO) message);
			break;
		case "THRESHOLD":
			processThresholdEvent((ThresholdEventDTO) message);
			break;
		case "HEALTH":
			processDeviceHealthEvent((DeviceHealthDTO) message);
			break;
		case "ACTUATOR_STATE":
			processActuatorStateEvent((ActuatorStateDTO) message);
			break;
		case "SENSOR_INSERT":
			processSensorInsertEvent((SensorDTO) message);
			break;
		case "SENSOR_UPDATE":
			processSensorUpdateEvent((SensorDTO) message);
			break;
		case "SENSOR_DELETE":
			processSensorDeleteEvent((String) message);
			break;
		case "ACTUATOR_INSERT":
			processActuatorInsertEvent((ActuatorDTO) message);
			break;
		case "ACTUATOR_UPDATE":
			processActuatorUpdateEvent((ActuatorDTO) message);
			break;
		case "ACTUATOR_DELETE":
			processActuatorDeleteEvent((String) message);
			break;
		default:
			break;
		}
	}


	//device monitoring
	public void processDeviceHealthEvent(DeviceHealthDTO deviceHealth) {
		DeviceHealthArrived_Event deviceHealthArrivedEvent = CepFactory.getInstance().createDeviceHealthArrived_Event();
		deviceHealthArrivedEvent.setId(deviceHealth.deviceId);
		getEventStream().push(deviceHealthArrivedEvent);
		systemModel.processDeviceHealthEvent(deviceHealth);
	}

	private void processActuatorStateEvent(ActuatorStateDTO message) {
		ActuatorStateArrived_Event actuatorStateArrivedEvent = CepFactory.getInstance().createActuatorStateArrived_Event();
		actuatorStateArrivedEvent.setActuatorId(message.deviceId);
		getEventStream().push(actuatorStateArrivedEvent);
		systemModel.processActuatorStateEvent(message);
	}

	//measurement processing
	public void processThresholdEvent(ThresholdEventDTO thresholdEvent) {
		MeasurementArrived_Event measurementArrivedEvent = CepFactory.getInstance().createMeasurementArrived_Event();
		measurementArrivedEvent.setId(thresholdEvent.deviceId);
		getEventStream().push(measurementArrivedEvent);
		systemModel.processThresholdEvent(thresholdEvent);
	}

	public void processMeasurementData(MeasurementDataDTO measurement) {
		MeasurementArrived_Event measurementArrivedEvent = CepFactory.getInstance().createMeasurementArrived_Event();
		measurementArrivedEvent.setId(measurement.deviceId);
		getEventStream().push(measurementArrivedEvent);
		systemModel.processMeasurementData(measurement);
	}

	//sensor management
	public void processSensorDeleteEvent(String name) {
		systemModel.processSensorDeleteEvent(name);
	}

	public void processSensorUpdateEvent(SensorDTO sensor) {
		systemModel.processSensorUpdateEvent(sensor);
	}

	public void processSensorInsertEvent(SensorDTO sensor) {
		systemModel.processSensorInsertEvent(sensor);
	}

	//actuator management
	public void processActuatorDeleteEvent(String name) {
		systemModel.processActuatorDeleteEvent(name);
	}

	public void processActuatorUpdateEvent(ActuatorDTO actuator) {
		systemModel.processActuatorUpdateEvent(actuator);
	}

	public void processActuatorInsertEvent(ActuatorDTO actuator) {
		systemModel.processActuatorInsertEvent(actuator);
	}

	//time updating
	public void processTick(Date currentTime) {
		getEventStream().push(CepFactory.getInstance().createTick_Event());
		systemModel.processTick(currentTime);
	}
}
