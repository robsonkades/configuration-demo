package com.example.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import jakarta.annotation.PostConstruct;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class CacheConfiguration {

    private Cache<Long, Optional<ConfigurationEntity>> cache;
    private ConfigurationRepository configurationRepository;

    public CacheConfiguration(final ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @PostConstruct
    private void onCreate() {
        cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .evictionListener((key, value, cause) -> {
                    System.out.println(key);
                })
                .refreshAfterWrite(15, TimeUnit.SECONDS)
                .expireAfter(new Expiry<Long, Optional<ConfigurationEntity>>() {
                    @Override
                    public long expireAfterCreate(Long key, Optional<ConfigurationEntity> value, long currentTime) {
                        System.out.println("expireAfterCreate " + LocalDateTime.now().toString());
                        return currentTime;
                    }

                    @Override
                    public long expireAfterUpdate(Long key, Optional<ConfigurationEntity> value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(Long key, Optional<ConfigurationEntity> value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }
                })
                .build(new CacheLoader<Long, Optional<ConfigurationEntity>>() {
                    @Override
                    public @Nullable Optional<ConfigurationEntity> load(Long key) throws Exception {
                        System.out.println("CacheLoader " + LocalDateTime.now().toString());
                        return configurationRepository.findById(key);
                    }
                });
    }

    public Optional<ConfigurationEntity> get(Long id) {
        return cache.get(id, k -> configurationRepository.findById(k));
    }
}
