package com.le.smartconnect.adapter.spring;

import org.reborndb.reborn.RoundRobinJedisPool;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.jedis.JedisConnection;

import redis.clients.jedis.Jedis;

/**
 * 基于jedis提供的reborn Connection
 * @author zouziwen
 *
 */
public class RebornConnection extends JedisConnection{
	
	public RebornConnection(Jedis jedis , RoundRobinJedisPool rebornJedisPool) {
		this(jedis,rebornJedisPool, 0);
	}

	public RebornConnection(Jedis jedis , RoundRobinJedisPool rebornJedisPool , int dbIndex) {
		super(jedis , rebornJedisPool.currentThreadPool() , dbIndex);
	}

	@Override
	public void close() throws DataAccessException {
		super.close();
	}
	
	
	
	
}
