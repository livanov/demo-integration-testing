package com.livanov.demo.integrationtesting.domain.ports;

import com.livanov.demo.integrationtesting.domain.IpDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IpDetailsRepository extends CrudRepository<IpDetails, Long> {

    Optional<IpDetails> findOneByIp(String ip);
}