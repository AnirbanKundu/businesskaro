package com.businesskaro.security;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Component
public class SecureTokenCache {
	private JedisPool pool;

	public void createConnection() {
		try {
			pool = new JedisPool(new JedisPoolConfig(),
					"pub-redis-16365.us-east-1-3.6.ec2.redislabs.com", 16365,
					Protocol.DEFAULT_TIMEOUT, "6uNtA6zeP5GlBVmV");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JedisPool getInstance(){
		if(pool == null){
			createConnection();
		}
		return pool;
	}
	
	public boolean isTokenValid(String secureToken){
		Jedis jedis = getInstance().getResource();
		boolean flag = jedis.exists(secureToken);
		getInstance().returnResource(jedis);
		return flag;
	}
	
	public void addToken(String secureToken){
		Jedis jedis = getInstance().getResource();
		jedis.set(secureToken, "1");
		getInstance().returnResource(jedis);
	}
	
	public void removeToken(String secureToken){
		Jedis jedis = getInstance().getResource();
		jedis.del(secureToken);
		getInstance().returnResource(jedis);
	}
}
