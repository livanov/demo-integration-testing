package com.livanov.demo.integrationtesting.adapters.freegeoip;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "adapters.freegeoip")
@Value
@ConstructorBinding
public class FreeGeoIpProperties {

    /**
     * The root uri of the Free Geo Ip service.
     */
    String rootUri;
}
