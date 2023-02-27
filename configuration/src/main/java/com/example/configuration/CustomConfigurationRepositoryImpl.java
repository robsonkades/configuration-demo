package com.example.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CustomConfigurationRepositoryImpl implements CustomConfigurationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ConfigurationEntity> findByTenantAndDomainAndService(String tenant, String domain, String service, Set<String> properties) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConfigurationEntity> query = criteriaBuilder.createQuery(ConfigurationEntity.class);
        Root<ConfigurationEntity> root = query.from(ConfigurationEntity.class);

        List<ConfigurationEntity> configurationEntityOutput = new ArrayList<>();

        Set<ConfigurationFilter> configurationFilters = filters(tenant, domain, service);

        for (ConfigurationFilter configurationFilter : configurationFilters) {
            Specification<ConfigurationEntity> configurationSpecification = createConfigurationSpecification(configurationFilter.getTenant(), configurationFilter.getDomain(), configurationFilter.getService(), properties);
            query.where(configurationSpecification.toPredicate(root, query, criteriaBuilder));
            TypedQuery<ConfigurationEntity> typedQuery = entityManager.createQuery(query);

            List<ConfigurationEntity> queryResultList = typedQuery.getResultList();
            properties.removeIf(s -> queryResultList.stream().anyMatch(configurationEntity -> configurationEntity.getKey().equals(s)));

            if (!queryResultList.isEmpty()) {
                configurationEntityOutput.addAll(queryResultList);
            }

            if (properties.isEmpty()) {
                break;
            }
        }
        return configurationEntityOutput;
    }

    private Set<ConfigurationFilter> filters(String tenant, String domain, String service) {
        Set<ConfigurationFilter> configurationFilter = new LinkedHashSet<>();
        configurationFilter.add(new ConfigurationFilter(tenant, domain, service));
        configurationFilter.add(new ConfigurationFilter(tenant, domain, "*"));
        configurationFilter.add(new ConfigurationFilter(tenant, "*", service));
        configurationFilter.add(new ConfigurationFilter(tenant, "*", "*"));
        configurationFilter.add(new ConfigurationFilter("*", domain, service));
        configurationFilter.add(new ConfigurationFilter("*", "*", service));
        configurationFilter.add(new ConfigurationFilter("*", "*", "*"));
        return configurationFilter;
    }

    private Specification<ConfigurationEntity> createConfigurationSpecification(String tenant, String domain, String service, Set<String> properties) {
        return (root, query, criteriaBuilder) -> {

            Predicate[] predicates = properties
                    .stream()
                    .map(key -> criteriaBuilder.equal(root.get("key"), key)).toArray(Predicate[]::new);

            Predicate p1 = criteriaBuilder.and(criteriaBuilder.equal(root.get("tenant"), tenant),
                    criteriaBuilder.equal(root.get("domain"), domain),
                    criteriaBuilder.equal(root.get("service"), service));

            query.orderBy(criteriaBuilder.asc(root.get("key")), criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(p1, criteriaBuilder.or(predicates));
        };
    }
}
