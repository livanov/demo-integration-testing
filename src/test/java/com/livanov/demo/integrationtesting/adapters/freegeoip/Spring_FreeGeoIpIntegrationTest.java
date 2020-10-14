package com.livanov.demo.integrationtesting.adapters.freegeoip;

import com.livanov.demo.integrationtesting.domain.IpDetails;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(FreeGeoIp.class)
class Spring_FreeGeoIpIntegrationTest {

    @Autowired
    private FreeGeoIp service;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void successful_response_is_deserialized_correctly() {

        // GIVEN
        server.expect(once(), requestTo("/json/1.1.1.1"))
                .andExpect(method(GET))
                .andRespond(withSuccess(JSONObject.wrap(new HashMap<String, Object>() {
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
                }).toString(), APPLICATION_JSON));

        // WHEN
        IpDetails info = service.getInfo("1.1.1.1").get();

        // THEN
        assertThat(info.getCountryCode()).isEqualTo("AU");
        assertThat(info.getCountryName()).isEqualTo("Australia");
        assertThat(info.getIp()).isEqualTo("1.1.1.1");
        assertThat(info.getLatitude()).isEqualTo(-33.494);
        assertThat(info.getLongitude()).isEqualTo(143.2104);
    }
}
