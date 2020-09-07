package com.ee.declaration.persistance.dao;

import com.ee.declaration.persistance.model.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeclarationRepository extends JpaRepository<Declaration, Long> {

    Optional<Declaration> findDeclarationByServiceProviderIdAndServiceDeclarationId(String serviceProviderId,
                                                                                    String serviceDeclarationId);

}
