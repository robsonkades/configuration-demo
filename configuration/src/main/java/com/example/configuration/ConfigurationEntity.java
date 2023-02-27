package com.example.configuration;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "configurations",
        indexes = {
                @Index(name = "idx_configurations_tenant_domain_service_property_key", columnList = "tenant, domain, service, property_key")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uni_configurations_tenant_domain_service_property_key", columnNames = {"tenant", "domain", "service", "property_key"})
        })
public class ConfigurationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "tenant", length = 50, nullable = false)
    private String tenant;

    @Column(name = "domain", length = 50, nullable = false)
    private String domain;

    @Column(name = "service", length = 50, nullable = false)
    private String service;

    @Column(name = "property_key", length = 50, nullable = false)
    private String key;

    @Column(name = "property_value", length = 300, nullable = false)
    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

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

        ConfigurationEntity that = (ConfigurationEntity) o;

        if (!tenant.equals(that.tenant)) return false;
        if (!domain.equals(that.domain)) return false;
        if (!service.equals(that.service)) return false;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        int result = tenant.hashCode();
        result = 31 * result + domain.hashCode();
        result = 31 * result + service.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ConfigurationEntity{" +
                "id=" + id +
                ", tenant='" + tenant + '\'' +
                ", domain='" + domain + '\'' +
                ", service='" + service + '\'' +
                ", key='" + key + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
