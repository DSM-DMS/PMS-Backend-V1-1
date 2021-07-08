package com.dms.pms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.connection.RedisServer;

import javax.annotation.PostConstruct;

@TestConfiguration
public class TestRedisConig {
    @Value("${spring.redis.port}")
    private int port;

    private static RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        if (redisServer == null) {
            redisServer = new RedisServer(port);
        }
    }
}
