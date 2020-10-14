package com.livanov.demo.integrationtesting.adapters.jpa;

import com.livanov.demo.integrationtesting.domain.IpDetails;
import com.livanov.demo.integrationtesting.domain.ports.IpDetailsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
        "spring.datasource.url=jdbc:tc:postgresql:12.4:///demo-db",
        "spring.jpa.hibernate.ddl-auto=create"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class Containers_IpDetailsRepositoryIntegrationTest {

    @Autowired
    private IpDetailsRepository repository;

    @Test
    void can_save() {

        // GIVEN
        String ip = "1.1.1.1";

        // WHEN
        IpDetails persisted = repository.save(new IpDetails(
                ip, "AU", "Australia", -33.494, 143.2104
        ));

        // THEN
        assertThat(persisted.getId()).isGreaterThan(0);
    }

    @Test
    void can_fetch() {

        // GIVEN
        String ip = "1.1.1.1";
        repository.save(new IpDetails(
                ip, "AU", "Australia", -33.494, 143.2104
        ));

        // WHEN
        IpDetails fetched = repository.findOneByIp(ip).get();

        // THEN
        assertThat(fetched.getId()).isGreaterThan(0);
        assertThat(fetched.getCountryCode()).isEqualTo("AU");
        assertThat(fetched.getCountryName()).isEqualTo("Australia");
        assertThat(fetched.getIp()).isEqualTo("1.1.1.1");
        assertThat(fetched.getLatitude()).isEqualTo(-33.494);
        assertThat(fetched.getLongitude()).isEqualTo(143.2104);
    }
}
