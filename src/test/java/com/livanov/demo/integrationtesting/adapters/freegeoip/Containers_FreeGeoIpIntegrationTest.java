package com.livanov.demo.integrationtesting.adapters.freegeoip;

import com.livanov.demo.integrationtesting.domain.IpDetails;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.JsonBody;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Testcontainers
class Containers_FreeGeoIpIntegrationTest {

    @Container
    private final MockServerContainer container = new MockServerContainer();

    @Test
    void successful_response_is_deserialized_correctly() {

        // GIVEN
        FreeGeoIp service = new FreeGeoIp(
                new RestTemplateBuilder(),
                new FreeGeoIpProperties(container.getEndpoint())
        );

        mockServer()
                .when(request()
                        .withMethod("GET")
                        .withPath("/json/1.1.1.1"))
                .respond(response()
                        .withHeader("Content-type", "application/json")
                        .withBody(new JsonBody(JSONObject.wrap(new HashMap<String, Object>() {
                            {
                                put("country_code", "AU");
                                put("metro_code", 0);
                                put("city", "");
                                put("ip", "1.1.1.1");
                                put("latitude", -33.494);
                                put("country_name", "Australia");
                                put("region_name", "");
                                put("time_zone", "Australia/Sydney");
                                put("zip_code", "");
                                put("region_code", "");
                                put("longitude", 143.2104);
                            }
                        }).toString())));

        // WHEN
        IpDetails info = service.getInfo("1.1.1.1").get();

        // THEN
        assertThat(info.getCountryCode()).isEqualTo("AU");
        assertThat(info.getCountryName()).isEqualTo("Australia");
        assertThat(info.getIp()).isEqualTo("1.1.1.1");
        assertThat(info.getLatitude()).isEqualTo(-33.494);
        assertThat(info.getLongitude()).isEqualTo(143.2104);
    }

    private MockServerClient mockServer() {
        return new MockServerClient(container.getHost(), container.getServerPort());
    }
}
