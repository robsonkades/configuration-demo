package com.example.configuration;

import jakarta.validation.constraints.NotBlank;

public class CreatePropertyDto {

    @NotBlank
    private String domain;
    @NotBlank
    private String service;
    @NotBlank
    private String key;
    @NotBlank
    private String data;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreatePropertyDto that = (CreatePropertyDto) o;

        if (!domain.equals(that.domain)) return false;
        if (!service.equals(that.service)) return false;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        int result = domain.hashCode();
        result = 31 * result + service.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }
}
