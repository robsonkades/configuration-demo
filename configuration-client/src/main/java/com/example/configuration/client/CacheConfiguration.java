package com.example.configuration.client;

import java.util.Map;
import java.util.function.Function;

public interface CacheConfiguration {

    void invalidateAll();

    void invalidate(String tenant);

    Map<String, String> get(String tenant, Function<String, Map<String, String>> function);
}
