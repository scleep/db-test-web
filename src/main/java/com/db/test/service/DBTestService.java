package com.db.test.service;

import com.db.test.domain.rabbitmqEntity;
import com.db.test.domain.redisEntity;

public interface DBTestService {

	void redisInsertData(redisEntity redisEntity) throws Exception;

	void redisPutRandom(redisEntity redisEntity) throws Exception;

	void redisGetAll(redisEntity redisEntity) throws Exception;

	void redisGetData(redisEntity redisEntity) throws Exception;

	void redisDelKey(redisEntity redisEntity) throws Exception;

	void redisAllDelete(redisEntity redisEntity) throws Exception;

	void redisGetAllConfig(redisEntity redisEntity) throws Exception;

	void rabbitmqInsertData(rabbitmqEntity rabbitmqEntity) throws Exception;

	void rabbitmqGetData(rabbitmqEntity rabbitmqEntity) throws Exception;

}
