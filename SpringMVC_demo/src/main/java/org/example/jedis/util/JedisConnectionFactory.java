package org.example.jedis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConnectionFactory {
    private static final JedisPool jedisPool;

    static {
        //配置连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(8);
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMinIdle(0);
        //jedisPoolConfig.setMaxWaitMillis(1000);

        //创建连接池对象
        jedisPool = new JedisPool(jedisPoolConfig,
                "192.168.149.128", 6379, 1000, "12345");
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

}
