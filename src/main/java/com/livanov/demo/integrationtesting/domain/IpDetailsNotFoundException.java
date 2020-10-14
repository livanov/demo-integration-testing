package com.livanov.demo.integrationtesting.domain;

public class IpDetailsNotFoundException extends RuntimeException {
    public IpDetailsNotFoundException(String ip) {
        super("Couldn't find information for ip: " + ip);
    }
}
