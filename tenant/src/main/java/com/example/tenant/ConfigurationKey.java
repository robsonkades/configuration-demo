package com.example.tenant;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum ConfigurationKey {

    DB_URL("db.url"),
    DB_USER("db.user"),
    DB_PASS("db.pass"),
    DB_PORT("db.port");

    private final String key;

    ConfigurationKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return key;
    }

    public static Set<String> getProperties() {
        return Arrays.stream(ConfigurationKey.values()).map(configurationKey -> configurationKey.getValue()).collect(Collectors.toSet());
    }
}
