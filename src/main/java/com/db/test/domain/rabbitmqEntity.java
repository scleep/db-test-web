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
	private String rabbitmqIP;
	private String rabbitmqPort;
	private String rabbitmqPw;
	private String rabbitmqUsername;
	private String rabbitmqPeriod;
	private String rabbitmqTerm;
	private Map<String, Object> insertData;
}
