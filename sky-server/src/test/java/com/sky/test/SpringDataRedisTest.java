package com.sky.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SpringDataRedisTest {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void testRedisTemplate(){
        System.out.println(redisTemplate);
        //string数据操作
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //hash类型的数据操作
        HashOperations hashOperations = redisTemplate.opsForHash();
        //list类型的数据操作
        ListOperations listOperations = redisTemplate.opsForList();
        //set类型数据操作
        SetOperations setOperations = redisTemplate.opsForSet();
        //zset类型数据操作
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }

    @Test
    public void testString(){
        // set
        redisTemplate.opsForValue().set("name","CarJ");
        // get
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);
        //setex
        redisTemplate.opsForValue().set("code","1234",3, TimeUnit.MINUTES);
        //setnx
        redisTemplate.opsForValue().setIfAbsent("lock","1");
        redisTemplate.opsForValue().setIfAbsent("lock","2");

    }
    @Test
    public void testHash(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("100","name","CarJ");
        hashOperations.put("100","age","20");
        String name = (String) hashOperations.get("100","name");
        System.out.println(name);
        Set keys = hashOperations.keys("100");
        System.out.println(keys);
        List values = hashOperations.values("100");
        System.out.println(values);
        hashOperations.delete("100","age");
    }
}
