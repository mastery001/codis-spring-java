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
	
	private final RoundRobinJedisPool rebornJedisPool;
	
	public RebornConnection(Jedis jedis , RoundRobinJedisPool rebornJedisPool) {
		this(jedis, rebornJedisPool, 0);
	}

	public RebornConnection(Jedis jedis , RoundRobinJedisPool rebornJedisPool , int dbIndex) {
		super(jedis , null , dbIndex);
		this.rebornJedisPool = rebornJedisPool;
	}

	@Override
	public void close() throws DataAccessException {
		try {
			super.close();
		} finally {
			// 保证reborn使用的连接池也能关闭
			if(rebornJedisPool != null) {
				rebornJedisPool.close();
			}
		}
		
	}
	
	
}
