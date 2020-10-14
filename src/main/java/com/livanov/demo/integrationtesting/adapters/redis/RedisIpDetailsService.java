package com.livanov.demo.integrationtesting.adapters.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.livanov.demo.integrationtesting.domain.IpDetails;
import com.livanov.demo.integrationtesting.domain.ports.CachedIpDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RedisIpDetailsService implements CachedIpDetailsService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final JedisPool jedisPool;

    @Override
    public Optional<IpDetails> getInfo(String ip) {

        try (Jedis jedis = jedisPool.getResource()) {
            String ipDetailsAsJson = jedis.get(ip);

            return Optional.ofNullable(ipDetailsAsJson)
                    .map(this::deserialize);
        }
    }

    @Override
    public void cache(IpDetails ipDetails) {

        String ipDetailsAsJson = serialize(ipDetails);

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(ipDetails.getIp(), ipDetailsAsJson);
        }
    }

    private IpDetails deserialize(String ipDetailsAsJson) {
        try {
            return OBJECT_MAPPER.readValue(ipDetailsAsJson, IpDetails.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while deserializing json", e);
        }
    }

    private String serialize(IpDetails ipDetails) {
        try {
            return OBJECT_MAPPER.writeValueAsString(ipDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while serializing to json", e);
        }
    }
}
