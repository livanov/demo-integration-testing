package com.livanov.demo.integrationtesting.infrastructure.web;

import com.livanov.demo.integrationtesting.domain.IpDetails;
import com.livanov.demo.integrationtesting.domain.IpDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class IpDetailsController {

    private final IpDetailsService service;

    @GetMapping("ip/{ip}")
    public IpDetails getSingle(@PathVariable("ip") String ip) {
        return service.getInfo(ip);
    }
}
