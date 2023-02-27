package com.example.configuration;

import java.util.List;
import java.util.Set;

public interface CustomConfigurationRepository {

    List<ConfigurationEntity> findByTenantAndDomainAndService(String tenant, String domain, String service, Set<String> properties);
}
