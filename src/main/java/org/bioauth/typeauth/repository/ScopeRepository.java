package org.bioauth.typeauth.repository;

import org.bioauth.typeauth.domain.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, Integer> {
}
