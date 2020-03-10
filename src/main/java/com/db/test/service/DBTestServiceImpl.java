package com.db.test.service;

import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public abstract class DBTestServiceImpl implements DBTestService {

	
	public Properties getProperties() {
		String path = "application.properties";
		Properties properties = new Properties();
		
		try {
			Reader reader = Resources.getResourceAsReader(path);
            properties.load(reader);
            
		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
		return properties;
	}
}
