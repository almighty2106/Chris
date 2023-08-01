package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void testString() {
        //存一个值
        redisTemplate.opsForValue().set("name", "Chenyu Li");

        //这个时候会被序列化
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println("name = " + name);

        redisTemplate.opsForValue().set("lucy", "lucy");
        Object lucy = redisTemplate.opsForValue().get("lucy");
        System.out.println("lucy = " + lucy);

    }

}
