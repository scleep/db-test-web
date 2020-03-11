package com.db.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQ_EmitLog {

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = rabbitmqConnectionFactory();
		
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

			String message = argv.length < 1 ? "info: Hello World!" : String.join(" ", argv);

			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
		}
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
