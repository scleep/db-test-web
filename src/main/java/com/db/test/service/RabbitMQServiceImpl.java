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
		log.info("rabbitmqInsertData - insertData : "+rabbitmqEntity.getInsertData());

		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
		    Channel channel = connection.createChannel();
		    
		    String QUEUE_NAME = "hello";

		    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		    String message = "Hello World!";


		    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + message + "'");
		    
		    channel.close();
		    connection.close();
			
//			CachingConnectionFactory cf = rabbitmqConfig.rabbitmqCachingConnectionFactory();
//			
//			RabbitTemplate template = new RabbitTemplate(cf);
//			template.setExchange("amq.direct");
//		    template.setQueue("myQueue");
//		    template.convertAndSend("foo.bar", "Hello, world!");
//		    cf.destroy();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
	}
	
	@Override
	public void rabbitmqGetData(rabbitmqEntity rabbitmqEntity) throws Exception {
		log.info("rabbitmqGetData - Config("+rabbitmqEntity.getRabbitmqIP()+":"+rabbitmqEntity.getRabbitmqPort()+")");

		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
		    Channel channel = connection.createChannel();
		    
		    String QUEUE_NAME = "hello";

		    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		    
		    Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                      String message = new String(body, "UTF-8");
                      System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);
			
//			CachingConnectionFactory cf = rabbitmqConfig.rabbitmqCachingConnectionFactory();
//			
//			// Queue 생성
//			RabbitAdmin admin = new RabbitAdmin(cf);
//	        Queue queue = new Queue("myQueue");
//	        admin.declareQueue(queue);
//	        
//	        // Exchange 바인딩
//	        DirectExchange exchange = new DirectExchange("amq.direct");
//	        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("foo.bar"));    
//	                
//	        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
//	        Object listener = new Object() {
//	            // 메시지 처리
//	            public void handleMessage(Object foo) {
//	                System.out.println(foo);
//	            }
//	        };
//	        
//	        // 메시지 리스닝
//	        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
//	        container.setMessageListener(adapter);
//	        container.setQueueNames("myQueue");
//	        container.start();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
	}

}
