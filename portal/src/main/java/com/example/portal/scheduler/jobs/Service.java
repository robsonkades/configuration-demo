package com.example.portal.scheduler.jobs;

public class Service {

    private String domain;
    private String service;

    public Service(String domain, String service) {
        this.domain = domain;
        this.service = service;
    }

    public String getDomain() {
        return domain;
    }

    public String getService() {
        return service;
    }
}
