package com.muyu.controller;

import com.muyu.service.ImitatePositionI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 牧鱼
 * @Classname TestController
 * @Description TODO
 * @Date 2021/9/15
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ImitatePositionI position;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/position")
    public String testPosition(){
        return position.selectAll().toString();
    }

    @GetMapping("/redisPosition")
    public void redisPosition(){
        ListOperations<String, String> operations = redisTemplate.opsForList();
        for (int i = 0; i < 10; i++) {
            operations.leftPush("list",String.valueOf(i));
            operations.leftPushAll("list",new String[]{});
        }
    }

    @GetMapping("/rightPop")
    public void rightPop(){
        ListOperations<String, String> operations = redisTemplate.opsForList();
        System.out.println(operations.rightPop("VIN12345678912345-1"));
    }


}
