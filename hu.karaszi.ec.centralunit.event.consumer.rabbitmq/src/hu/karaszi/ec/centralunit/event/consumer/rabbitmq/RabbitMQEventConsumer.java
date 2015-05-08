package hu.karaszi.ec.centralunit.event.consumer.rabbitmq;

import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Device;
import hu.karaszi.ec.centralunit.data.Measurement;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.event.processor.api.EventProcessor;

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
import com.rabbitmq.client.LongString;

public class RabbitMQEventConsumer {
	private final String EXCHANGE_NAME = "device_data";
	private final String HOST = "localhost";

	private List<EventProcessor> eventProcessors = new ArrayList<EventProcessor>();

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
					String objectType = ((LongString) properties.getHeaders().get(
							"message-type")).toString();
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
					case "sensor_insert_event":
						forwardSensorInsertEvent(receivedObject);
						break;
					case "sensor_update_event":
						forwardSensorUpdateEvent(receivedObject);
						break;
					case "sensor_delete_event":
						forwardSensorDeleteEvent(receivedObject);
						break;
					case "actuator_insert_event":
						forwardActuatorInsertEvent(receivedObject);
						break;
					case "actuator_update_event":
						forwardActuatorUpdateEvent(receivedObject);
						break;
					case "actuator_delete_event":
						forwardActuatorDeleteEvent(receivedObject);
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

	public void setEventProcessor(EventProcessor ep) {
		eventProcessors.add(ep);
	}

	public void unsetEventProcessor(EventProcessor ep) {
		eventProcessors.remove(ep);
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
		for (EventProcessor deviceDataProcessor : eventProcessors) {
			deviceDataProcessor.processDeviceStatus(d);
		}
	}

	private void forwardThresholdEvent(Object sensor) {
		Sensor s = (Sensor) sensor;
		for (EventProcessor deviceDataProcessor : eventProcessors) {
			deviceDataProcessor.processThresholdEvent(s);
		}
	}

	private void forwardMeasurement(Object measurement) {
		Measurement m = (Measurement) measurement;
		for (EventProcessor deviceDataProcessor : eventProcessors) {
			deviceDataProcessor.processMeasurementData(m);
		}
	}
	
	private void forwardActuatorDeleteEvent(Object receivedObject) {
		long id = (long) receivedObject;
		for (EventProcessor deviceDataProcessor : eventProcessors) {
			deviceDataProcessor.processActuatorDeleteEvent(id);
		}
	}

	private void forwardActuatorUpdateEvent(Object receivedObject) {
		Actuator actuator = (Actuator) receivedObject;
		for (EventProcessor deviceDataProcessor : eventProcessors) {
			deviceDataProcessor.processActuatorUpdateEvent(actuator);
		}
	}

	private void forwardActuatorInsertEvent(Object receivedObject) {
		Actuator actuator = (Actuator) receivedObject;
		for (EventProcessor deviceDataProcessor : eventProcessors) {
			deviceDataProcessor.processActuatorInsertEvent(actuator);
		}
	}

	private void forwardSensorDeleteEvent(Object receivedObject) {
		long id = (long) receivedObject;
		for (EventProcessor deviceDataProcessor : eventProcessors) {
			deviceDataProcessor.processSensorDeleteEvent(id);
		}
	}

	private void forwardSensorUpdateEvent(Object receivedObject) {
		Sensor sensor = (Sensor) receivedObject;
		for (EventProcessor deviceDataProcessor : eventProcessors) {
			deviceDataProcessor.processSensorUpdateEvent(sensor);
		}
	}

	private void forwardSensorInsertEvent(Object receivedObject) {
		Sensor sensor = (Sensor) receivedObject;
		for (EventProcessor deviceDataProcessor : eventProcessors) {
			deviceDataProcessor.processSensorInsertEvent(sensor);
		}
	}
}
