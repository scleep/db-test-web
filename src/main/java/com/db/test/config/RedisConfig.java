package com.db.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.db.test.domain.redisEntity;

@Configuration
public class RedisConfig {
	
	 @Value("${spring.redis.host}")
	 private String hostname;
	 
	 @Value("${spring.redis.port}")
	 private int portnum;
	 
	 @Value("${spring.redis.password}")
	 private String redispw;

	 //private RedisProperties redisProperties;
	
//	@Bean
//	public JedisConnectionFactory connectionFactory(String redisHost, int redisPort) {				
//		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//		jedisConnectionFactory.setHostName(redisHost);
//		jedisConnectionFactory.setPort(redisPort);
//		jedisConnectionFactory.setUsePool(true);		
//		return jedisConnectionFactory;
//	}
//	
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate(String redisHost, int redisPort) {
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new StringRedisSerializer());
//		redisTemplate.setConnectionFactory(connectionFactory(redisHost, redisPort));		
//		return redisTemplate;
//	}
	
	@Bean
    public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisconf = new RedisStandaloneConfiguration(hostname, portnum);
		redisconf.setPassword(redispw);

		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisconf);

        return lettuceConnectionFactory;
    }
	
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(String str_host, int int_port, String str_pw) {
//		RedisStandaloneConfiguration redisconf = new RedisStandaloneConfiguration(str_host, int_port);
//		redisconf.setPassword(str_pw);
//
//		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisconf);
//
//        return lettuceConnectionFactory;
//    }
//    @Bean
//    public RedisTemplate<String, Object> redisTemplateWithConfig(redisEntity redisEntity) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory(redisEntity.getRedisIP(), Integer.parseInt(redisEntity.getRedisPort()), redisEntity.getRedisPw()));
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }

}
