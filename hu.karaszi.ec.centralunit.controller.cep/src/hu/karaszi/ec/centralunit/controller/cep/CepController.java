package hu.karaszi.ec.centralunit.controller.cep;

import hu.karaszi.ec.centralunit.controller.cep.eventmodel.CepFactory;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.mapping.QueryEngine2ViatraCep;
import hu.karaszi.ec.centralunit.controller.cep.instancemodel.InstanceModel;
import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.dal.MeasurementDataManager;
import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.Measurement;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.event.processor.api.EventProcessor;

import org.eclipse.viatra.cep.core.api.helpers.DefaultApplication;
import org.eclipse.viatra.cep.core.metamodels.automaton.EventContext;
import org.osgi.service.component.ComponentContext;

public class CepController extends DefaultApplication implements EventProcessor {
	private QueryEngine2ViatraCep mapping;
	private InstanceModel systemModel = new InstanceModel();
	private DeviceManager deviceManager;
	private MeasurementDataManager measurementManager;
	
	public void setDeviceManager(DeviceManager dm) {
		deviceManager = dm;
	}

	public void unsetDeviceManager(DeviceManager dm) {
		deviceManager = null;
	}
	
	public void setMeasurementDataManager(MeasurementDataManager mm) {
		measurementManager = mm;
	}

	public void unsetMeasurementDataManager(MeasurementDataManager dm) {
		measurementManager = null;
	}
	
	public CepController() {
		super(EventContext.CHRONICLE);
	}

	protected void activate(ComponentContext context) {
		getEngine().getLogger().debug("Starting");
		systemModel.prepareModel(deviceManager, measurementManager);
		mapping = QueryEngine2ViatraCep.register(systemModel.getResourceSet(), getEventStream());
	}
	
	protected void deactivate(ComponentContext context) {
		mapping.dispose();
		getEngine().getLogger().debug("Ending");
	}
	
	@Override
	protected void configureRules() {
		getEngine().addRules(CepFactory.getInstance().allRules());
	}

	@Override
	public void processDeviceStatus(Device device) {
		System.out.println("device status received");
		systemModel.processDeviceStatus(device);
	}

	@Override
	public void processThresholdEvent(Sensor sensor) {
		System.out.println("threshold event received");
		systemModel.processThresholdEvent(sensor);
	}

	@Override
	public void processMeasurementData(Measurement measurement) {
		System.out.println("measurement data received");
		systemModel.processMeasurementData(measurement);
	}

	@Override
	public void processSensorDeleteEvent(long id) {
		System.out.println("sensor delete received");
		systemModel.processSensorDeleteEvent(id);
	}

	@Override
	public void processSensorUpdateEvent(Sensor sensor) {
		System.out.println("seonsor update received");
		systemModel.processSensorUpdateEvent(sensor);
	}

	@Override
	public void processSensorInsertEvent(Sensor sensor) {
		System.out.println("sensor insert received");
		systemModel.processSensorInsertEvent(sensor);
	}

	@Override
	public void processActuatorDeleteEvent(long id) {
		System.out.println("actuator delete received");
		systemModel.processActuatorDeleteEvent(id);
	}

	@Override
	public void processActuatorUpdateEvent(Actuator actuator) {
		System.out.println("actuator update received");
		systemModel.processActuatorUpdateEvent(actuator);
	}

	@Override
	public void processActuatorInsertEvent(Actuator actuator) {
		System.out.println("actuator insert received");
		systemModel.processActuatorInsertEvent(actuator);
	}

}
