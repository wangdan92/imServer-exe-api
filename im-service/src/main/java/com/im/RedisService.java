package com.im;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhangPeng
 * @date 2017/12/3
 */
public interface RedisService {
    /**
     * 写入缓存
     * @param key 键
     * @param value 值
     */
    void addData(String key, String value);

    /**
     * 写入缓存
     * @param key 键
     * @param value 值
     * @param timeout 超时时间
     * @param unit 单位
     */
    void addData(String key, String value, long timeout, TimeUnit unit);

    /**
     * 删除缓存
     * @param key 键
     */
    void deleteData(String key);

    /**
     * 读取缓存
     * @param key 键
     * @return
     */
    String getData(String key);

    /**
     * 查询key的生命周期
     * @param key
     * @param timeUnit
     * @return
     */
    long getKeyExpire(String key, TimeUnit timeUnit);
}
