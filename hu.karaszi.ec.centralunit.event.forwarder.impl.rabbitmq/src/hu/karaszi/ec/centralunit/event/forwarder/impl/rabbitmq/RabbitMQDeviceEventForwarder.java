package hu.karaszi.ec.centralunit.event.forwarder.impl.rabbitmq;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.dal.MeasurementDataManager;
import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.DeviceHealth;
import hu.karaszi.ec.centralunit.data.Measurement;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.data.SensorRange;
import hu.karaszi.ec.centralunit.event.forwarder.api.DeviceEventForwarder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.ComponentContext;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RabbitMQDeviceEventForwarder implements DeviceEventForwarder {
	private final String EXCHANGE_NAME = "device_data";
	private final String HOST = "localhost";

	private DeviceManager deviceManager;
	private MeasurementDataManager measurementDataManager;
	
	private Connection connection;
	private Channel channel;

	protected void activate(ComponentContext context) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void deactivate(ComponentContext context) {
		try {
			if (channel != null) {
				channel.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setDeviceManager(DeviceManager dm) {
		deviceManager = dm;
	}

	public void unsetDeviceManager(DeviceManager dm) {
		deviceManager = null;
	}

	public void setMeasurementDataManager(MeasurementDataManager mdm) {
		measurementDataManager = mdm;
	}

	public void unsetMeasurementDataManager(MeasurementDataManager mdm) {
		measurementDataManager = null;
	}
	@Override
	public void forwardDeviceStatus(String source, String status, Date date) {
		try {
			Device sourceDevice = processDeviceStatusEvent(source, status,  date);
			send(sourceDevice, "devicehealth_event");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			// Unknown status
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// Send error
			e.printStackTrace();
		}
	}

	private Device processDeviceStatusEvent(String source, String status, Date date) {
		DeviceHealth statusEnum = DeviceHealth.valueOf(status);
		Device sourceDevice = deviceManager.getDevice(source);
		sourceDevice.setDeviceHealth(statusEnum);
		sourceDevice.setLastDeviceHealthDate(date);
		return deviceManager.updateDevice(sourceDevice);
	}

	@Override
	public void forwardThresholdEvent(String source, String newRange,
			double measurement, int scale, Date date) {
		try {
			Sensor sourceSensor = processThresholdEvent(source, newRange);
			send(sourceSensor, "threshold_event");
			Measurement newMeasurement = processMeasurementData(source,
					measurement, scale, date);
			send(newMeasurement, "new_measurement");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			// Unknown status
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// Send error
			e.printStackTrace();
		}
	}

	private Sensor processThresholdEvent(String source, String newRange) {
		SensorRange rangeEnum = SensorRange.valueOf(newRange);
		Sensor sourceSensor = deviceManager.getSensor(source);
		sourceSensor.setCurrentRange(rangeEnum);
		return deviceManager.updateSensor(sourceSensor);
	}

	@Override
	public void forwardMeasurementData(String source, double measurement,
			int scale, Date date) {
		try {
			Measurement newMeasurement = processMeasurementData(source,
					measurement, scale, date);
			send(newMeasurement, "new_measurement");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// Send error
			e.printStackTrace();
		}
	}

	private Measurement processMeasurementData(String source,
			double measurement, int scale, Date date) {
		Sensor sourceSensor = deviceManager.getSensor(source);
		Measurement newMeasurement = new Measurement();
		newMeasurement.setScale(scale);
		newMeasurement.setSensor(sourceSensor);
		newMeasurement.setTimestamp(date);
		newMeasurement.setValue(measurement);
		return measurementDataManager.insertMeasurement(newMeasurement);
	}

	private <T extends Serializable> void send(T item, String type) throws IOException {
		byte[] data;
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(item);
			oos.flush();

			data = bos.toByteArray();
		}
		BasicProperties props = MessageProperties.PERSISTENT_BASIC;
		Map<String, Object> headers = new HashMap<>();
		headers.put("message-type", type);
		props = props.builder().headers(headers).build();
		channel.basicPublish(EXCHANGE_NAME, "", props, data);
	}
}
