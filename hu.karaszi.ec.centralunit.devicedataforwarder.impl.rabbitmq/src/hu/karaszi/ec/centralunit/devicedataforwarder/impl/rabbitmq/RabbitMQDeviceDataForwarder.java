package hu.karaszi.ec.centralunit.devicedataforwarder.impl.rabbitmq;

import hu.karaszi.ec.centralunit.dal.DeviceManager;
import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.DeviceHealth;
import hu.karaszi.ec.centralunit.data.Measurement;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.data.SensorRange;
import hu.karaszi.ec.centralunit.devicedataforwarder.api.DeviceDataForwarder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import org.osgi.service.component.ComponentContext;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RabbitMQDeviceDataForwarder implements DeviceDataForwarder {
	private final String EXCHANGE_NAME = "device_data";
	private final String HOST = "localhost";

	private DeviceManager deviceManager;

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

	@Override
	public void forwardDeviceStatus(String source, String status, Date date) {
		try {
			DeviceHealth statusEnum = DeviceHealth.valueOf(status);
			Device sourceDevice = deviceManager.getDevice(source);
			sourceDevice.setDeviceHealth(statusEnum);
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

	@Override
	public void forwardThresholdEvent(String source, String newRange,
			double measurement, int scale, String unit, Date date) {
		try {
			SensorRange rangeEnum = SensorRange.valueOf(newRange);
			Sensor sourceSensor = deviceManager.getSensor(source);
			sourceSensor.setCurrentRange(rangeEnum);
			send(sourceSensor, "threshold_event");
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

	@Override
	public void forwardMeasurementData(String source, double measurement,
			int scale, Date date) {
		try {
			Sensor sourceSensor = deviceManager.getSensor(source);
			Measurement newMeasurement = new Measurement();
			newMeasurement.setScale(scale);
			newMeasurement.setSensor(sourceSensor);
			newMeasurement.setTimestamp(date);
			newMeasurement.setValue(measurement);
			send(newMeasurement, "new_measurement");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// Send error
			e.printStackTrace();
		}
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
		props.getHeaders().put("message-type", type);
		channel.basicPublish(EXCHANGE_NAME, "", props, data);
	}
}
