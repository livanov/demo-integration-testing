package com.livanov.demo.integrationtesting.domain;

import com.livanov.demo.integrationtesting.domain.ports.RemoteIpDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class IpDetailsService {

    private final RemoteIpDetailsService remoteService;

    public IpDetails getInfo(String ip) {

        Optional<IpDetails> ipInfoFromService = tryGetFromThirdParty(ip);

        if (ipInfoFromService.isPresent()) {
            log.info("IpDetails [{}]     retrieved from Third Party Provider Service.", ip);

            IpDetails ipDetails = ipInfoFromService.get();

            return ipDetails;
        }

        throw new IpDetailsNotFoundException(ip);
    }

    private Optional<IpDetails> tryGetFromThirdParty(String ip) {
        try {
            return remoteService.getInfo(ip);
        } catch (Exception ex) {
            log.warn("IpDetails [" + ip + "] NOT retrieved from Third Party Provider.");
            return Optional.empty();
        }
    }
}
