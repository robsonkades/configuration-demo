package com.example.configuration.client;

import java.util.Set;

public class ConfigurationProperty {

    private Set<String> properties;

    public ConfigurationProperty(Set<String> properties) {
        this.properties = properties;
    }

    public Set<String> getProperties() {
        return properties;
    }

    public void setProperties(Set<String> properties) {
        this.properties = properties;
    }
}
