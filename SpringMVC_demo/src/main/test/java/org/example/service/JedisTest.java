package org.example.service;

import org.example.jedis.util.JedisConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class JedisTest {
    private Jedis jedis;

    @Before
    public void setUp(){
        //set up
        //jedis = new Jedis("192.168.149.128", 6379);
        jedis = JedisConnectionFactory.getJedis();
        //password
        jedis.auth("12345");

        //选择库
        jedis.select(0);
    }

    @Test
    public void testHash(){
        jedis.hset("user:1", "name", "Jack");
        jedis.hset("user:1", "age", "21");

        Map<String, String> result = jedis.hgetAll("user:1");
        System.out.println(result);
    }

    @Test
    public void testJedis(){
        //存入数据
        String result = jedis.set("name", "李宸宇");
        System.out.println(result);

        //Get data
        String name = jedis.get("name");
        System.out.println(name);
    }

    @After
    public void tearDown(){
        if(jedis != null){
            jedis.close();
        }
    }
}
