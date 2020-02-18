package com.db.test.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
		log.info("rabbitmqInsertData - Config("+rabbitmqEntity.getRabbitmqIP()+":"+rabbitmqEntity.getRabbitmqPort()+")");
		
		String QUEUE_NAME = (String) rabbitmqEntity.getInsertData().get("queueName");
		String message = (String) rabbitmqEntity.getInsertData().get("message");
		log.info("rabbitmqInsertData -  QUEUE_NAME: "+QUEUE_NAME+", Message: "+message);
		
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
			
		    Channel channel = connection.createChannel();
		    
		    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + message + "'");
		    
		    channel.close();
		    connection.close();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
	}
	
	@Override
	public void rabbitmqGetData(rabbitmqEntity rabbitmqEntity) throws Exception {
		log.info("rabbitmqGetData - Config("+rabbitmqEntity.getRabbitmqIP()+":"+rabbitmqEntity.getRabbitmqPort()+")");
		
		String QUEUE_NAME = (String) rabbitmqEntity.getInsertData().get("queueName");
		log.info("rabbitmqGetData -  QUEUE_NAME: "+QUEUE_NAME);
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
            channel.basicConsume(QUEUE_NAME, true, consumer);


		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
	}

}
