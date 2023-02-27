package com.example.tenant;

import com.example.configuration.client.ServiceConfiguration;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ServiceRuntimeConfiguration {

    private final Function<String, ServiceConfiguration> configurationServiceFactoryFunction;

    public ServiceRuntimeConfiguration(final Function<String, ServiceConfiguration> configurationServiceFactoryFunction) {
        this.configurationServiceFactoryFunction = configurationServiceFactoryFunction;
    }

    public String getDbUrl() {
        return getServiceConfiguration().asString(ConfigurationKey.DB_URL.getValue());
    }

    public String getUsername() {
        return getServiceConfiguration().asString(ConfigurationKey.DB_USER.getValue());
    }

    public String getPassword() {
        return getServiceConfiguration().asString(ConfigurationKey.DB_PASS.getValue());
    }

    public String getSchema() {
        return String.format("%s_%s", "TNT", TenantContextHolder.getTenant());
    }

    public int port() {
        return getServiceConfiguration().asInt(ConfigurationKey.DB_PORT.getValue());
    }

    private ServiceConfiguration getServiceConfiguration() {
        return configurationServiceFactoryFunction.apply(TenantContextHolder.getTenant());
    }
}
