package com.db.test.service;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ValueOperations;

import com.db.test.config.RabbitMQConfig;
import com.db.test.domain.rabbitmqEntity;
import com.db.test.domain.redisEntity;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service("rabbitmqService")
@Slf4j
@Configuration
public class RabbitMQServiceImpl extends DBTestServiceImpl {
	
	@Autowired
	private RabbitMQConfig rabbitmqConfig;
	
	public void rabbitmqInsertData(redisEntity redisEntity) throws Exception {
		
	}

	@Override
	public void redisInsertData(redisEntity redisEntity) throws Exception {
	}

	@Override
	public void redisPutRandom(redisEntity redisEntity) throws Exception {
	}

	@Override
	public void redisGetAll(redisEntity redisEntity) throws Exception {
	}

	@Override
	public void redisGetData(redisEntity redisEntity) throws Exception {
	}

	@Override
	public void redisDelKey(redisEntity redisEntity) throws Exception {
	}

	@Override
	public void redisAllDelete(redisEntity redisEntity) throws Exception {
	}

	@Override
	public void redisGetAllConfig(redisEntity redisEntity) throws Exception {
	}

	@Override
	public void rabbitmqInsertData(rabbitmqEntity rabbitmqEntity) throws Exception {
		Properties properties = getProperties();
		log.info("rabbitmqInsertData - Config("+properties.get("spring.rabbitmq.host")+":"+properties.get("spring.rabbitmq.port")+")");
		
		String queue_name = (String) rabbitmqEntity.getQueueName();
		String message = (String) rabbitmqEntity.getMessage();
		
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
			
		    Channel channel = connection.createChannel();
		    
		    channel.queueDeclare(queue_name, false, false, false, null);

		    channel.basicPublish("", queue_name, null, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + message + "'");
		    
		    channel.close();
		    connection.close();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
	}
	
	public void rabbitmqInsertData2(rabbitmqEntity rabbitmqEntity) throws Exception {
		Properties properties = getProperties();
		log.info("rabbitmqInsertData - Config("+properties.get("spring.rabbitmq.host")+":"+properties.get("spring.rabbitmq.port")+")");
		
		String exchange_name = (String) rabbitmqEntity.getInsertData().get("exchangeName");
		String queue_name = (String) rabbitmqEntity.getInsertData().get("queueName");
		String message = (String) rabbitmqEntity.getInsertData().get("message");
		String routingKey = (String) rabbitmqEntity.getInsertData().get("routingKey");

		log.info("rabbitmqInsertData -  QUEUE_NAME: "+queue_name+", Message: "+message);
		
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
			
		    Channel channel = connection.createChannel();
		    
		    // (String)exchange_name, (String)type, (Boolean)durable
		    channel.exchangeDeclare(exchange_name, "direct", true);
		    
		    // (String)queue_name, (Boolean)durable, (Boolean)exclusive, (Boolean)autoDelete, (Map<String,Object>)arguments
		    channel.queueDeclare(queue_name, true, false, false, null);
		    
		    // (String)queue, (String)exchange, (String)routingKey
		    channel.queueBind(queue_name, exchange_name, routingKey);

		    channel.basicPublish("", queue_name, null, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + message + "'");
		    
		    channel.close();
		    connection.close();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
	}
	
	@Override
	public void rabbitmqGetData(rabbitmqEntity rabbitmqEntity) throws Exception {
		Properties properties = getProperties();
		log.info("rabbitmqInsertData - Config("+properties.get("spring.rabbitmq.host")+":"+properties.get("spring.rabbitmq.port")+")");
		
		String QUEUE_NAME = (String) rabbitmqEntity.getInsertData().get("queueName");
		log.info("rabbitmqGetData -  QUEUE_NAME: "+QUEUE_NAME);
		
		String getData = "";
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
		    Channel channel = connection.createChannel();

		    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		    
		    Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                      String message = new String(body, "UTF-8");
                      System.out.println(" [x] Received '" + message + "'");
                }
            };
            System.out.println(consumer.toString());
            channel.basicConsume(QUEUE_NAME, true, consumer);


		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
	}

}
