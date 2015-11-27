package hu.karaszi.ec.centralunit.event.forwarder.impl.rabbitmq;

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

import hu.karaszi.ec.centralunit.event.forwarder.api.EventForwarder;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventSource;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventType;

public class RabbitMQForwarder implements EventForwarder {
	protected final String EXCHANGE_NAME = "device_data";
	protected final String HOST = "localhost";
	
	protected Connection connection;
	protected Channel channel;

	protected void activate(ComponentContext context) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
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
	
	@Override
	public <T extends Serializable> void sendEvent(T item, EventSource source, EventType type) {
		sendEvent(item, source, type.name());
	}

	@Override
	public <T extends Serializable> void sendEvent(T item, EventSource source, String type) {
		try {
			byte[] data;
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(bos)) {
				oos.writeObject(item);
				oos.flush();

				data = bos.toByteArray();
			}
			BasicProperties props = MessageProperties.PERSISTENT_BASIC;
			String topic = source + "." + type;
			Map<String, Object> headers = new HashMap<>();
			headers.put("message-type", type);
			props = props.builder().headers(headers).build();
			channel.basicPublish(EXCHANGE_NAME, topic, props, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
