package com.livanov.demo.integrationtesting.adapters.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
class AutoConfiguration {

    @Bean
    JedisPool jedisPool(
            @Value("${adapters.redis.uri}") String uri
    ) throws URISyntaxException {

        return new JedisPool(new URI(uri));
    }
}
