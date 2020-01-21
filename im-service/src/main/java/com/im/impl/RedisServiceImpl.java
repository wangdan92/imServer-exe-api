package com.im.impl;
import com.im.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhangPeng
 * @date 2017/12/3
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void addData(String key, String value){
        redisTemplate.boundValueOps(key).set(value);
    }

    @Override
    public void addData(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.boundValueOps(key).set(value, timeout, unit);
    }

    @Override
    public void deleteData(String key){
        redisTemplate.delete(key);
    }

    @Override
    public String getData(String key){
        return redisTemplate.boundValueOps(key).get();
    }

    @Override
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }
}
