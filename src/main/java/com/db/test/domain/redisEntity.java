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
public class redisEntity {
	private String redisIP;
	private String redisPort;
	private String redisPw;
	private String redisPeriod;
	private String redisTerm;
	private Map<String, Object> insertData;
}
