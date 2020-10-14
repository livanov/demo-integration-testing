package com.livanov.demo.integrationtesting.domain.ports;

import com.livanov.demo.integrationtesting.domain.IpDetails;

import java.util.Optional;

public interface RemoteIpDetailsService {
    Optional<IpDetails> getInfo(String ip);
}
