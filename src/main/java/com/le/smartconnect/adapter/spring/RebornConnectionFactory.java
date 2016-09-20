package com.le.smartconnect.adapter.spring;

import org.reborndb.reborn.RoundRobinJedisPool;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.ExceptionTranslationStrategy;
import org.springframework.data.redis.PassThroughExceptionTranslationStrategy;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import org.springframework.util.Assert;

import redis.clients.jedis.Jedis;

/**
 * 基于reborn实现的适配Spring-data-redis的RedisConnectionFactory
 * @author zouziwen
 *
 */
public class RebornConnectionFactory implements RedisConnectionFactory{

	private static final ExceptionTranslationStrategy EXCEPTION_TRANSLATION = new PassThroughExceptionTranslationStrategy(
			JedisConverters.exceptionConverter());
	
	private final RoundRobinJedisPool rebornJedisPool;
	private boolean convertPipelineAndTxResults = true;
	private int dbIndex = 0;
	
	public RebornConnectionFactory(RoundRobinJedisPool rebornJedisPool) {
		Assert.notNull(rebornJedisPool, "RoundRobinJedisPool must not be null.");
		this.rebornJedisPool = rebornJedisPool;
	}
	
	@Override
	public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
		return EXCEPTION_TRANSLATION.translate(ex);
	}

	@Override
	public RedisConnection getConnection() {
		Jedis jedis = rebornJedisPool.getResource();
		RebornConnection connection = new RebornConnection(jedis, rebornJedisPool, dbIndex);
		connection.setConvertPipelineAndTxResults(convertPipelineAndTxResults);
		return postProcessConnection(connection);
	}

	@Override
	public RedisClusterConnection getClusterConnection() {
		throw new UnsupportedOperationException("Reborn Unsupported Redis Cluster");
	}

	@Override
	public RedisSentinelConnection getSentinelConnection() {
		throw new UnsupportedOperationException("Reborn Unsupported Redis Sentinel");
	}
	
	/**
	 * Post process a newly retrieved connection. Useful for decorating or executing initialization commands on a new
	 * connection. This implementation simply returns the connection.
	 * 
	 * @param connection
	 * @return processed connection
	 */
	protected JedisConnection postProcessConnection(JedisConnection connection) {
		return connection;
	}
	
	/**
	 * Specifies if pipelined results should be converted to the expected data type. If false, results of
	 * {@link JedisConnection#closePipeline()} and {@link JedisConnection#exec()} will be of the type returned by the
	 * Jedis driver
	 * 
	 * @return Whether or not to convert pipeline and tx results
	 */
	public boolean getConvertPipelineAndTxResults() {
		return convertPipelineAndTxResults;
	}

	/**
	 * Specifies if pipelined results should be converted to the expected data type. If false, results of
	 * {@link JedisConnection#closePipeline()} and {@link JedisConnection#exec()} will be of the type returned by the
	 * Jedis driver
	 * 
	 * @param convertPipelineAndTxResults Whether or not to convert pipeline and tx results
	 */
	public void setConvertPipelineAndTxResults(boolean convertPipelineAndTxResults) {
		this.convertPipelineAndTxResults = convertPipelineAndTxResults;
	}
	
	/**
	 * Returns the index of the database.
	 * 
	 * @return Returns the database index
	 */
	public int getDatabase() {
		return dbIndex;
	}

	/**
	 * Sets the index of the database used by this connection factory. Default is 0.
	 * 
	 * @param index database index
	 */
	public void setDatabase(int index) {
		Assert.isTrue(index >= 0, "invalid DB index (a positive index required)");
		this.dbIndex = index;
	}
}
