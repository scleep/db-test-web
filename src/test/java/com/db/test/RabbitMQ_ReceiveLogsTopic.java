package com.db.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class RabbitMQ_ReceiveLogsTopic {
	private static final String EXCHANGE_NAME = "topic_logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = rabbitmqConnectionFactory();

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queueName = channel.queueDeclare().getQueue();

		if (argv.length < 1) {
			System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
			System.exit(1);
		}

		for (String bindingKey : argv) {
			channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
		}

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
		};
		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});
	}
	
	public static ConnectionFactory rabbitmqConnectionFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("###.###.###.###");
		factory.setPort(Integer.parseInt("0000"));
		factory.setUsername("######");
		factory.setPassword("##########");
		factory.setVirtualHost("####");

		return factory;
	}
}
