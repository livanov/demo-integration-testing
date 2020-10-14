package com.livanov.demo.integrationtesting.adapters.redis;

import com.livanov.demo.integrationtesting.domain.IpDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SocketUtils;
import redis.clients.jedis.JedisPool;
import redis.embedded.RedisServer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EmbeddedRedis_RedisIpDetailsServiceIntegrationTest {

    private RedisServer redisServer;
    private RedisIpDetailsService service;

    @BeforeEach
    void setup() {

        int randomFreePort = SocketUtils.findAvailableTcpPort();

        redisServer = new RedisServer(randomFreePort);
        redisServer.start();

        service = new RedisIpDetailsService(
                new JedisPool("localhost", randomFreePort)
        );
    }

    @AfterEach
    void teardown() {
        redisServer.stop();
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
