package com.livanov.demo.integrationtesting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class IpDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "country_code")
    @JsonProperty("country_code")
    private String countryCode;

    @Column(name = "country_name")
    @JsonProperty("country_name")
    private String countryName;

    @Column(name = "ip", unique = true)
    @JsonProperty("ip")
    private String ip;

    @Column(name = "latitude")
    @JsonProperty("latitude")
    private double latitude;

    @Column(name = "longitude")
    @JsonProperty("longitude")
    private double longitude;

    private IpDetails() {
        // required by Jackson and Hibernate
    }

    public IpDetails(String ip, String countryCode, String countryName, double latitude, double longitude) {
        this.ip = ip;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
