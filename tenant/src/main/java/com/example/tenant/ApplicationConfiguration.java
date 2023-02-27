package com.example.tenant;


import com.example.configuration.client.CacheConfiguration;
import com.example.configuration.client.CacheConfigurationInMemory;
import com.example.configuration.client.ServiceConfiguration;
import com.example.configuration.client.ServiceConfigurationClient;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.function.Function;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CacheConfiguration cacheConfiguration() {
        return new CacheConfigurationInMemory();
    }

    @Bean
    public ServiceConfigurationClient serviceConfigurationClient() {
        return new ServiceConfigurationClient()
                .withCacheConfiguration(cacheConfiguration())
                .withHost("http://localhost:8080")
                .withDomain("platform")
                .withService("tenant")
                .withProperties(ConfigurationKey.getProperties());
    }

    @Bean
    public Function<String, ServiceConfiguration> beanFactory() {
        return this::serviceConfiguration;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ServiceConfiguration serviceConfiguration(String tenant) {
        return new ServiceConfiguration(tenant, serviceConfigurationClient());
    }
}
