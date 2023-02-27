package com.example.configuration;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/configurations")
public class ConfigureController {

    private final ConfigurationRepository configurationRepository;
    private final CacheConfiguration cacheConfiguration;

    public ConfigureController(final ConfigurationRepository configurationRepository, final CacheConfiguration cacheConfiguration) {
        this.configurationRepository = configurationRepository;
        this.cacheConfiguration = cacheConfiguration;
    }

    @PostMapping
    public ResponseEntity<ConfigurationEntity> create(@RequestHeader(value = "x-tenant") String tenant, @Valid @RequestBody CreatePropertyDto payload) {

        ConfigurationEntity configurationEntity = new ConfigurationEntity();
        configurationEntity.setTenant(tenant);
        configurationEntity.setDomain(payload.getDomain());
        configurationEntity.setService(payload.getService());
        configurationEntity.setKey(payload.getKey());
        configurationEntity.setData(payload.getData());

        try {
            ConfigurationEntity entity = configurationRepository.save(configurationEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(entity);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(value = "/{domain}/{service}")
    public ResponseEntity<List<ConfigurationEntity>> index(@PathVariable String domain, @PathVariable String service, @RequestHeader(value = "x-tenant") String tenant, @Valid @RequestBody RequestPropertiesDto payload) {
        List<ConfigurationEntity> byTenantAndDomainAndService = configurationRepository.findByTenantAndDomainAndService(tenant, domain, service, payload.getProperties());
        return ResponseEntity.ok(byTenantAndDomainAndService);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConfigurationEntity> index(@PathVariable Long id) {

        if (cacheConfiguration.get(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cacheConfiguration.get(id).get());
    }
}
