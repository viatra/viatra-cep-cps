package hu.karaszi.ec.centralunit.devicedataconsumer.rabbitmq;

import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.Measurement;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.devicedataprocessor.api.DeviceDataProcessor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.ComponentContext;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitMQDeviceDataConsumer {
	private final String EXCHANGE_NAME = "device_data";
	private final String HOST = "localhost";

	private List<DeviceDataProcessor> deviceDataProcessors = new ArrayList<DeviceDataProcessor>();

	private Connection connection;
	private Channel channel;

	protected void activate(ComponentContext context) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, "");
			channel.basicQos(1);
			channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag,
						Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String objectType = (String) properties.getHeaders().get(
							"message-type");
					Object receivedObject = byteArrayToObject(body);
					switch (objectType) {
					case "new_measurement":
						forwardMeasurement(receivedObject);
						break;
					case "threshold_event":
						forwardThresholdEvent(receivedObject);
						break;
					case "devicehealth_event":
						forwardDeviceEvent(receivedObject);
						break;
					default:
						break;
					}
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			});
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

	public void setDeviceDataProcessor(DeviceDataProcessor ddp) {
		deviceDataProcessors.add(ddp);
	}

	public void unsetDeviceDataProcessor(DeviceDataProcessor ddp) {
		deviceDataProcessors.remove(ddp);
	}

	private Object byteArrayToObject(byte[] receivedByteArray) throws IOException {
		Object readObject;

		try (ByteArrayInputStream bis = new ByteArrayInputStream(
				receivedByteArray);
				ObjectInputStream ois = new ObjectInputStream(bis)) {
			readObject = ois.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Exception during serialization: ");
			e.printStackTrace();

			throw new IOException("Exception during serialization", e);
		}
		return readObject;
	}
	
	private void forwardDeviceEvent(Object device) {
		Device d = (Device) device;
		for (DeviceDataProcessor deviceDataProcessor : deviceDataProcessors) {
			deviceDataProcessor.processDeviceStatus(d);
		}
	}

	private void forwardThresholdEvent(Object sensor) {
		Sensor s = (Sensor) sensor;
		for (DeviceDataProcessor deviceDataProcessor : deviceDataProcessors) {
			deviceDataProcessor.processThresholdEvent(s);
		}
	}

	private void forwardMeasurement(Object measurement) {
		Measurement m = (Measurement) measurement;
		for (DeviceDataProcessor deviceDataProcessor : deviceDataProcessors) {
			deviceDataProcessor.processMeasurementData(m);
		}
	}
}
