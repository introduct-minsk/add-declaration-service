package com.ee.declaration.service.impl;

import com.ee.declaration.common.properties.LocaleProperty;
import com.ee.declaration.exception.DuplicateDeclarationException;
import com.ee.declaration.exception.InvalidLocaleException;
import com.ee.declaration.persistance.dao.DeclarationRepository;
import com.ee.declaration.persistance.dao.ServiceProviderLocaleRuleRepository;
import com.ee.declaration.persistance.model.Declaration;
import com.ee.declaration.persistance.model.ServiceProviderLocaleRule;
import com.ee.declaration.persistance.model.TranslatableString;
import com.ee.declaration.service.DeclarationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeclarationServiceImpl implements DeclarationService {

    private final DeclarationRepository declarationRepository;
    private final ServiceProviderLocaleRuleRepository serviceProviderLocaleRuleRepository;
    private final LocaleProperty localeProperty;

    @Transactional
    public void add(Declaration declaration) {
        log.info("Adding new declaration: {}", declaration);

        checkDuplicateDeclaration(declaration);
        checkLocales(declaration);

        declarationRepository.save(declaration);
        log.info("New declaration has been successfully added");
    }

    private void checkLocales(Declaration declaration) {
        Set<Locale> providedLocales = serviceProviderLocaleRuleRepository
                .findAllByServiceProviderId(declaration.getServiceProviderId())
                .stream()
                .map(ServiceProviderLocaleRule::getLocale)
                .collect(Collectors.toSet());

        providedLocales.addAll(localeProperty.getLocales()
                .stream()
                .map(local -> new Locale(local.getLanguage(), local.getCountry()))
                .collect(Collectors.toSet()));

        checkLocales(declaration, providedLocales);
    }

    private void checkLocales(Declaration declaration, Set<Locale> providedLocales) {
        log.debug("Check locales for service provider id: {} with allowed locales: {}",
                declaration.getServiceProviderId(), providedLocales);

        Set<Locale> nameLocales = getLocales(declaration::getName);
        if (!nameLocales.containsAll(providedLocales)) {
            log.error("Invalid name locale for service provider id: {}, allowed locales: {}, invalid locales: {}",
                    declaration.getServiceProviderId(), providedLocales, nameLocales);
            providedLocales.removeAll(nameLocales);
            throw new InvalidLocaleException("name", providedLocales);
        }

        Set<Locale> descriptionLocales = getLocales(declaration::getDescription);
        if (!descriptionLocales.containsAll(providedLocales)) {
            log.error("Invalid description locale for service provider id: {}, allowed locales: {}, invalid locales: {}",
                    declaration.getServiceProviderId(), providedLocales, descriptionLocales);
            providedLocales.removeAll(descriptionLocales);
            throw new InvalidLocaleException("description", providedLocales);
        }

    }

    private Set<Locale> getLocales(Supplier<List<? extends TranslatableString>> supplier) {
        return supplier.get().stream()
                .map(TranslatableString::getLocale)
                .collect(Collectors.toSet());
    }

    private void checkDuplicateDeclaration(Declaration declaration) {
        log.debug("Check for duplicates with service provider id: {} and service declaration id: {}",
                declaration.getServiceProviderId(), declaration.getServiceDeclarationId());

        Optional<Declaration> optionalDeclaration = declarationRepository
                .findDeclarationByServiceProviderIdAndServiceDeclarationId(declaration.getServiceProviderId(),
                        declaration.getServiceDeclarationId());

        if (optionalDeclaration.isPresent()) {
            log.error("Duplicate declaration with service provider id: {} and service declaration id: {}",
                    declaration.getServiceProviderId(), declaration.getServiceDeclarationId());
            throw new DuplicateDeclarationException();
        }
    }
}
