package com.example.configuration;

public class ConfigurationFilter {

    private String tenant;
    private String domain;
    private String service;

    public ConfigurationFilter(String tenant, String domain, String service) {
        this.tenant = tenant;
        this.domain = domain;
        this.service = service;
    }

    public String getTenant() {
        return tenant;
    }

    public String getDomain() {
        return domain;
    }

    public String getService() {
        return service;
    }
}
