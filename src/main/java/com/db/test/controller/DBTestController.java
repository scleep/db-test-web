package com.db.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.db.test.domain.redisEntity;
import com.db.test.service.DBTestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/DBTest")
public class DBTestController {
	
	@Autowired
	private DBTestService dbTestService;
	
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
		try {
			switch (dbType) {
			case "mariaDB":
				mav = new ModelAndView("mariaDB");
				break;
			case "redis":
				mav = new ModelAndView("redis");
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
			dbTestService.insertData(redisEntity);
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
			dbTestService.redisPutRandom(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/redisGetAll", method = RequestMethod.GET)
	public void redisGetAll(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisGetAll - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		
		try {
			dbTestService.redisGetAll(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/redisGetData", method = RequestMethod.GET)
	public void redisGetData(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisGetData - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		
		try {
			dbTestService.redisGetData(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/redisDelKey", method = RequestMethod.DELETE)
	public void redisDelKey(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisDelKey - "+redisEntity.getInsertData());
		
		try {
			dbTestService.redisDelKey(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/redisAllDelete", method = RequestMethod.DELETE)
	public void redisAllDelete(@ModelAttribute redisEntity redisEntity) { 
		log.info("redisAllDelete - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		
		try {
			dbTestService.redisAllDelete(redisEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
}
