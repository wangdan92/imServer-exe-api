package com.im.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author ZhangPeng
 * @date 2017/12/8
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 24 * 60 * 60)
public class RedisSessionConfiguration {
}
