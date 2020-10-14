package com.livanov.demo.integrationtesting.adapters.redis;

import com.livanov.demo.integrationtesting.domain.IpDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class Containers_RedisIpDetailsServiceIntegrationTest {

    @Container
    private final GenericContainer<?> redisContainer = new GenericContainer<>("redis:6.0.8-alpine")
            .withExposedPorts(6379);

    private RedisIpDetailsService service;

    @BeforeEach
    void setup() {
        service = new RedisIpDetailsService(
                new JedisPool(redisContainer.getHost(), redisContainer.getMappedPort(6379))
        );
    }

    @Test
    void returns_empty_when_not_present_in_cache() {

        // WHEN
        Optional<IpDetails> info = service.getInfo("8.8.8.8");

        // THEN
        assertThat(info.isPresent()).isFalse();
    }

    @Test
    void can_save_to_and_fetch_from_cache() {

        // GIVEN
        String ip = "1.1.1.1";

        // WHEN
        service.cache(new IpDetails(
                ip, "AU", "Australia", -33.494, 143.2104
        ));

        // THEN
        IpDetails info = service.getInfo(ip).get();
        assertThat(info.getIp()).isEqualTo(ip);
        assertThat(info.getCountryCode()).isEqualTo("AU");
        assertThat(info.getLatitude()).isEqualTo(-33.494);
        assertThat(info.getLongitude()).isEqualTo(143.2104);
    }
}
