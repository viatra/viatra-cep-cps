package hu.karaszi.ec.centralunit.event.forwarder.impl.rabbitmq;

import hu.karaszi.ec.centralunit.data.Actuator;
import hu.karaszi.ec.centralunit.data.Sensor;
import hu.karaszi.ec.centralunit.event.forwarder.api.ManagementEventForwarder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.ComponentContext;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RabbitMQManagementEventForwarder implements ManagementEventForwarder {
	private final String EXCHANGE_NAME = "device_data";
	private final String HOST = "localhost";
	
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

	@Override
	public void forwardSensorInsertEvent(Sensor sensor) {
		try {
			send(sensor, "sensor_insert_event");
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
	public void forwardSensorUpdateEvent(Sensor sensor) {
		try {
			send(sensor, "sensor_update_event");
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
	public void forwardSensorDeleteEvent(long id) {
		try {
			send(id, "sensor_delete_event");
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
	public void forwardActuatorInsertEvent(Actuator actuator) {
		try {
			send(actuator, "actuator_insert_event");
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
	public void forwardActuatorUpdateEvent(Actuator actuator) {
		try {
			send(actuator, "actuator_update_event");
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
	public void forwardActuatorDeleteEvent(long id) {
		try {
			send(id, "actuator_delete_event");
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
}
