package com.db.test.controller;

import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.db.test.domain.rabbitmqEntity;
import com.db.test.domain.redisEntity;
import com.db.test.service.DBTestService;
import com.db.test.service.DBTestServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/DBTest")
public class DBTestController {
	
//	@Autowired
//	@Qualifier("mariadbService")
//	private DBTestService mariadbService;

	@Autowired
	@Qualifier("redisService")
	private DBTestService redisService;
	
	@Autowired
	@Qualifier("rabbitmqService")
	private DBTestService rabbitmqService;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() { 
		
		ModelAndView mav = null;
		
		try {
			mav = new ModelAndView("home");
			return mav; 
			
		} catch (Exception e) {
			log.error(e.getMessage());
			mav = new ModelAndView("error");
		}
		
		return mav; 
	}
	
	
	
	@RequestMapping(value = "/selectDB", method = RequestMethod.POST)
	public ModelAndView SelectDB(@RequestParam("DBType") String dbType) {
		log.info("SelectDB - "+dbType);
		ModelAndView mav = null;

		String path = "application.properties";
		Properties properties = new Properties();
		
		try {
			Reader reader = Resources.getResourceAsReader(path);
            properties.load(reader);
            
			switch (dbType) {
			case "mariaDB":
				mav = new ModelAndView("mariaDB");
				break;
			case "redis":
				
				mav = new ModelAndView("redis");
				mav.addObject("setedHost", properties.get("spring.redis.host"));
				mav.addObject("setedPort", properties.get("spring.redis.port"));
				mav.addObject("setedPassword", properties.get("spring.redis.password"));
				break;
			case "rabbitmq":
				mav = new ModelAndView("rabbitmq");
				mav.addObject("setedHost", properties.get("spring.rabbitmq.host"));
				mav.addObject("setedPort", properties.get("spring.rabbitmq.port"));
				mav.addObject("setedPassword", properties.get("spring.rabbitmq.password"));
				mav.addObject("setedUsername", properties.get("spring.rabbitmq.username"));
				break;
			case "kafka":
				mav = new ModelAndView("kafka");
				break;
			default :
				log.info("Undefined DB Type.");
			}
			return mav; 
			
		} catch (Exception e) {
			log.error(e.getMessage());
			mav = new ModelAndView("error");
		}
		return mav; 
	}
	
	@Deprecated
	@PostMapping("/redis")
	public String redis(@RequestParam("dbName") String dbName, Model model) { 
		model.addAttribute("dbName", dbName);
		return "redis"; 
	}
	
	@ResponseBody
	@RequestMapping(value = "/redisPut", method = RequestMethod.PUT)
	public void redisPut(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisPut - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		
		try {
			redisService.redisInsertData(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	    
		//return "redis";
	}
	
	@ResponseBody
	@RequestMapping(value = "/redisPutRandom", method = RequestMethod.PUT)
	public void redisPutRandom(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisPutRandom - "+redisEntity.getRedisPeriod()+"put/sec : "+redisEntity.getRedisTerm()+"sec");
		
		try {
			redisService.redisPutRandom(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/redisGetAll", method = RequestMethod.GET)
	public void redisGetAll(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisGetAll - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		
		try {
			redisService.redisGetAll(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/redisGetData", method = RequestMethod.GET)
	public void redisGetData(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisGetData - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		
		try {
			redisService.redisGetData(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/redisGetAllConfig", method = RequestMethod.GET)
	public void redisGetAllConfig(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisGetAllConfig - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		
		try {
			redisService.redisGetAllConfig(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/redisDelKey", method = RequestMethod.DELETE)
	public void redisDelKey(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisDelKey - "+redisEntity.getInsertData());
		
		try {
			redisService.redisDelKey(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/redisAllDelete", method = RequestMethod.DELETE)
	public void redisAllDelete(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisAllDelete - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		
		try {
			redisService.redisAllDelete(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/rabbitmqPut", method = RequestMethod.PUT)
	public ResponseEntity<String> rabbitmqPut(@ModelAttribute rabbitmqEntity rabbitmqEntity) { 
		log.info("rabbitmqPut - "+"QueueName: "+rabbitmqEntity.getQueueName()+", Message: "+rabbitmqEntity.getMessage());
		
		try {
			rabbitmqService.rabbitmqInsertData(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	    
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/rabbitmqPutRandom", method = RequestMethod.PUT)
	public void rabbitmqPutRandom(@ModelAttribute rabbitmqEntity rabbitmqEntity) { 
		log.info("redisPutRandom - "+rabbitmqEntity.getRabbitmqPeriod()+"put/sec : "+rabbitmqEntity.getRabbitmqTerm()+"sec");
		
		try {
			rabbitmqService.rabbitmqPutRandom(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/rabbitmqGetData", method = RequestMethod.GET)
	public ResponseEntity<String> rabbitmqGet(@ModelAttribute rabbitmqEntity rabbitmqEntity) { 
		log.info("rabbitmqGet - QueueName: "+rabbitmqEntity.getQueueName());
		
		try {
			rabbitmqService.rabbitmqGetData(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return new ResponseEntity<>("RabbitMQ - Get Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/rabbitmqPut_task", method = RequestMethod.PUT)
	public ResponseEntity<String> rabbitmqPut_task(@ModelAttribute rabbitmqEntity rabbitmqEntity) { 
		log.info("rabbitmqPut_task - "+"QueueName: "+rabbitmqEntity.getQueueName()+", Message: "+rabbitmqEntity.getMessage());
		
		try {
			rabbitmqService.rabbitmqInsertData_task(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	    
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
	
}
