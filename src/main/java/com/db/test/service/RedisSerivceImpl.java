package com.db.test.service;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.db.test.config.RedisConfig;
import com.db.test.domain.rabbitmqEntity;
import com.db.test.domain.redisEntity;

import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service("redisService")
@Slf4j
@Configuration
public class RedisSerivceImpl extends DBTestServiceImpl {
	
	@Autowired
	private RedisConfig redisConfig;
	
	@Autowired
    RedisTemplate<String, Object> redisTemplate;

	@Autowired
    RedisTemplate<String, Object> redisTemplateWithConfig;

	@Override
	public void redisInsertData(redisEntity redisEntity) throws Exception {
		log.info("redisInsertData - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		log.info("redisInsertData - insertData : "+redisEntity.getInsertData());
		
		Map<String, Object> insert = redisEntity.getInsertData();
		try {
			ValueOperations<String, Object> vop = redisTemplate.opsForValue();
			
			for (String key : insert.keySet()){
		        vop.set(key, insert.get(key));
		    }

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

	@Override
	public void redisPutRandom(redisEntity redisEntity) throws Exception {
		log.info("redisPutRandom - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		
		int period = Integer.parseInt(redisEntity.getRedisPeriod());
		int term = Integer.parseInt(redisEntity.getRedisTerm());
		int count = 0;
		int put_cnt = 0;
		
		try {
			ValueOperations<String, Object> vop = redisTemplate.opsForValue();
			
			while(count < term) {
				put_cnt++;
				
				vop.set("k"+put_cnt, "v"+put_cnt);
				
				System.out.println("["+"k"+put_cnt+", "+"v"+put_cnt+"]");
				
				Thread.sleep(period*1000);
				count+=period;
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}
	
	@Override
	public void redisGetAll(redisEntity redisEntity) throws Exception {
		log.info("redisGetAll - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		
		try {
			ValueOperations<String, Object> vop = redisTemplate.opsForValue();
			Set<String> keys = redisTemplate.keys("*");
			log.info("redisGetAll - ALL Keys : "+keys);
			
			for(String key : keys) {
				String result = (String) vop.get(key);
		        System.out.println("["+key+", "+result+"]");
			}
		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}
	
	@Override
	public void redisGetData(redisEntity redisEntity) throws Exception {
		log.info("redisGetData - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
		log.info("redisGetData - Key : "+redisEntity.getInsertData().keySet());
		
		Map<String, Object> insert = redisEntity.getInsertData();
		try {
			ValueOperations<String, Object> vop = redisTemplate.opsForValue();
			
			for (String key : insert.keySet()){
				log.info("redisGetData - Key : "+key);
				String result = (String) vop.get(key);
		        System.out.println(result);//jdk
		    }
		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

	@Override
	public void redisDelKey(redisEntity redisEntity) throws Exception {
		Map<String, Object> insert = redisEntity.getInsertData();
		
		try {
			for (String key : insert.keySet()){
				log.info("redisDelKey - "+key+" : Deleting.");
				redisTemplate.delete(key);
				log.info("redisDelKey - "+key+" : Deleted.");
		    }

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

	@Override
	public void redisAllDelete(redisEntity redisEntity) throws Exception {
		try {
			Set<String> keys = redisTemplate.keys("*");
			log.info("redisAllDelete - ALL Keys"+keys+" : Deleting.");
			
			for(String key : keys) {
				redisTemplate.delete(key);
			}
			
			log.info("redisAllDelete - ALL Keys"+keys+" : Deleted.");
		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

	@Override
	public void redisGetAllConfig(redisEntity redisEntity) throws Exception {
		
		try {
			RedisConnection vop = redisTemplate.getConnectionFactory().getConnection();
			// Set<String> keys = redisTemplate.get;
			// log.info("redisGetAll - ALL Keys : "+keys);
			
			
			Properties result = vop.getConfig("*");
	        //System.out.println(result);
	        String[] result_list = result.toString().split(", ");
	        
	        int count = 1;
	        
	        for(String temp : result_list) {
	        	System.out.println(count+": "+temp.split("=")[0]);
	        	count++;
	        	if(temp.split("=").length > 1) {
	        		System.out.println(count+": "+temp.split("=")[1]);
	        	} else {
	        		System.out.println(count+": \"\"");
	        	}
	        	count++;
	        }
	        
		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
	}

	@Override
	public void rabbitmqInsertData(rabbitmqEntity rabbitmqEntity) throws Exception {
	}

	@Override
	public void rabbitmqGetData(rabbitmqEntity rabbitmqEntity) throws Exception {
	}

	@Override
	public void rabbitmqPutRandom(rabbitmqEntity rabbitmqEntity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rabbitmqInsertData_task(rabbitmqEntity rabbitmqEntity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
//	public void insertData2(redisEntity redisEntity) throws Exception {
//		log.info("insertData - Config("+redisEntity.getRedisIP()+":"+redisEntity.getRedisPort()+")");
//		log.info("insertData - insertData("+redisEntity.getInsertData()+")");
//		Map<String, Object> insert = redisEntity.getInsertData();
//		try {
//			RedisConfig rc = new RedisConfig();
//			rc.redisTemplateWithConfig(redisEntity);
//			
//			ValueOperations<String, Object> vop = redisTemplateWithConfig.opsForValue();
//			
//			for (String key : insert.keySet()){
//				log.info("key:"+key+",value:"+insert.get(key));
//		        vop.set(key, insert.get(key));
//		    }
//			
//			log.info("----------------------------------------------------");
//			
//			for (String key : insert.keySet()){
//				String result = (String) vop.get(key);
//		        System.out.println(result);//jdk
//		    }
//
//		} catch (Exception e) {
//			log.error(e.getMessage());		
//		}
//	}
}
