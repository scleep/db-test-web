package com.db.test.domain;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Configuration
public class rabbitmqEntity {
	private String rabbitmqPeriod;
	private String rabbitmqTerm;
	private String queueName;
	private String message;
	private Map<String, Object> insertData;
}
