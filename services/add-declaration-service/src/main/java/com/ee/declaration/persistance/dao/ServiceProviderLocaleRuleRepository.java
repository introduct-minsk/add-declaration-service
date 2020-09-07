package com.ee.declaration.persistance.dao;

import com.ee.declaration.persistance.model.ServiceProviderLocaleRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ServiceProviderLocaleRuleRepository extends JpaRepository<ServiceProviderLocaleRule, Long> {

    Set<ServiceProviderLocaleRule> findAllByServiceProviderId(String serviceProviderId);

}
