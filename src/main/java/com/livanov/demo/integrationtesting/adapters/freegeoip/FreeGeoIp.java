package com.livanov.demo.integrationtesting.adapters.freegeoip;

import com.livanov.demo.integrationtesting.domain.IpDetails;
import com.livanov.demo.integrationtesting.domain.ports.RemoteIpDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@EnableConfigurationProperties(FreeGeoIpProperties.class)
public class FreeGeoIp implements RemoteIpDetailsService {

    private final RestTemplate restTemplate;

    public FreeGeoIp(RestTemplateBuilder builder, FreeGeoIpProperties freeGeoIpProperties) {

        String rootUri = freeGeoIpProperties.getRootUri();

        this.restTemplate = builder.rootUri(rootUri).build();
    }

    @Override
    public Optional<IpDetails> getInfo(String ip) {

        return Optional.ofNullable(
                restTemplate.getForObject("/json/{ip}", IpDetails.class, ip)
        );
    }
}
