package com.db.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitMQConfig {
	
	@Value("${spring.rabbitmq.host}")
	 private String hostname;
	
	 @Value("${spring.rabbitmq.port}")
	 private int portnum;
	 
	 @Value("${spring.rabbitmq.username}")
	 private String username;
	 
	 @Value("${spring.rabbitmq.password}")
	 private String rabbitmqpw;
	 
	 @Value("${spring.rabbitmq.virtual-host}")
	 private String vhost;
	 
	 public ConnectionFactory rabbitmqConnectionFactory() {
		 ConnectionFactory factory = new ConnectionFactory();
		 factory.setHost(hostname);
		 factory.setPort(portnum);
		 factory.setUsername(username);
		 factory.setPassword(rabbitmqpw);
		 factory.setVirtualHost(vhost);
		 
		 return factory;
	 }
	 
//	 public CachingConnectionFactory rabbitmqCachingConnectionFactory() {
//		 CachingConnectionFactory cachingFactory = new CachingConnectionFactory();
//		 cachingFactory.setUsername(username);
//		 cachingFactory.setPassword(rabbitmqpw);
//		 cachingFactory.setHost(hostname);
//		 cachingFactory.setPort(portnum);
//		 
//		 return cachingFactory;
//	 }
}
