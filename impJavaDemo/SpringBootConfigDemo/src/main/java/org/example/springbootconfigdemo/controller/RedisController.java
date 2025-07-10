package org.example.springbootconfigdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/*
redis 一主两从Demo
* */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PutMapping("/set")
    public void set(@RequestParam String key, @RequestParam  String value) {
        // 设置Redis键值对
        stringRedisTemplate.opsForValue().set(key, value);
        System.out.println("success set");
        System.out.println("get key " + key + "get value : " + stringRedisTemplate.opsForValue().get(key));
    }

}
