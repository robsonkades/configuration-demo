package com.example.configuration.client;

import java.util.Set;

public class ServiceConfigurationClient {

    private CacheConfiguration cacheConfiguration;
    private Set<String> properties;
    private String host;
    private String domain;
    private String service;

    public ServiceConfigurationClient withCacheConfiguration(final CacheConfiguration cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
        return this;
    }

    public ServiceConfigurationClient withProperties(final Set<String> properties) {
        this.properties = properties;
        return this;
    }

    public ServiceConfigurationClient withHost(String host) {
        this.host = host;
        return this;
    }

    public ServiceConfigurationClient withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public ServiceConfigurationClient withService(String service) {
        this.service = service;
        return this;
    }

    public CacheConfiguration getCacheConfiguration() {
        return cacheConfiguration;
    }

    public Set<String> getProperties() {
        return properties;
    }

    public String getHost() {
        return host;
    }

    public String getDomain() {
        return domain;
    }

    public String getService() {
        return service;
    }
}
