package com.db.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RabbitMQ_NewTask {
	
	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = rabbitmqConnectionFactory();
		
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

			String message = String.join(" ", argv);

			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes("UTF-8"));
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
