package com.sp.config.redis;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class JedisPoolFactory {

    @Autowired
    JedisConfig redisConfig;


    @Bean
    public JedisPool JedisPoolFactory() {
        log.info("redisConfig{}", redisConfig);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //连接池允许的最大空闲连接数
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        //连接池中的最大连接数
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        //当资源池链接用尽后，调用者的最大等待时间（单位秒）
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        //向资源池借用连接时是否做连接有效性检测(ping)，检测到无效链接时会移除
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        JedisPool jedisPool;
        if(StrUtil.isBlank(redisConfig.getPassword())){
            jedisPool = new JedisPool(poolConfig,
                    redisConfig.getHost(),
                    redisConfig.getPort(),
                    redisConfig.getTimeout()*1000,
                    null,
                    redisConfig.getDatabase()
            );
        }else{
            jedisPool = new JedisPool(poolConfig,
                    redisConfig.getHost(),
                    redisConfig.getPort(),
                    redisConfig.getTimeout()*1000,
                    redisConfig.getPassword(),
                    redisConfig.getDatabase()
            );
        }
        return jedisPool;
    }
}