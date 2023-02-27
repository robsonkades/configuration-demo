package com.example.configuration.client;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CacheConfigurationInMemory implements CacheConfiguration {

    private Cache<String, Map<String, String>> cache;

    @PostConstruct
    private void onCreate() {
        cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(0, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public void invalidateAll() {
        cache.invalidateAll();
    }

    @Override
    public void invalidate(String tenant) {
        cache.invalidate(tenant);
    }

    @Override
    public Map<String, String> get(String tenant, Function<String, Map<String, String>> function) {
        return cache.get(tenant, function);
    }
}
