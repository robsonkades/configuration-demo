package com.example.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Long>, CustomConfigurationRepository {
//
//    @Query(value = "from ConfigurationEntity configuration " +
//            "where (configuration.tenant = :tenant and configuration.domain = :domain and configuration.service = :service)" +
//            "or (configuration.tenant = :tenant and configuration.domain = :domain and configuration.service = '*')" +
//            "or (configuration.tenant = :tenant and configuration.domain = '*' and configuration.service = :service)" +
//            "or (configuration.tenant = :tenant and configuration.domain = '*' and configuration.service = '*')" +
//            "or (configuration.tenant = '*' and configuration.domain = :domain and configuration.service = :service) " +
//            "or (configuration.tenant = '*' and configuration.domain = :domain and configuration.service = '*') " +
//            "or (configuration.tenant = '*' and configuration.domain = '*' and configuration.service = :service) " +
//            "or (configuration.tenant = '*' and configuration.domain = '*' and configuration.service = '*')")
//    Optional<List<ConfigurationEntity>> findAllTenantAndDomainAndService(@Param("tenant") String tenant, @Param("domain") String domain, @Param("service") String service);
}
