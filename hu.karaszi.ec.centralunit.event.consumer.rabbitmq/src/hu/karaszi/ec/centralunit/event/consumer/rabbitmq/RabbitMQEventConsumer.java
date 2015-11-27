package hu.karaszi.ec.centralunit.event.consumer.rabbitmq;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.LongString;

import hu.karaszi.ec.centralunit.event.consumer.api.EventConsumer;
import hu.karaszi.ec.centralunit.event.processor.api.EventProcessor;

public class RabbitMQEventConsumer implements EventConsumer {
	private final String EXCHANGE_NAME = "device_data";
	private final String HOST = "localhost";

	private Connection connection;
	private Channel channel;
	
	private EventProcessor processor;
	
	@Override
	public void activate(EventProcessor handler, String[] topics) {
		this.processor = handler;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			String queueName = channel.queueDeclare().getQueue();
			for (String topic : topics) {
				channel.queueBind(queueName, EXCHANGE_NAME, topic);				
			}
			channel.basicQos(1);
			channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag,
						Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String objectType = ((LongString) properties.getHeaders().get(
							"message-type")).toString();
					Object receivedObject = byteArrayToObject(body);
					processor.handleEvent(receivedObject, objectType);
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deactivate() {
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
}
