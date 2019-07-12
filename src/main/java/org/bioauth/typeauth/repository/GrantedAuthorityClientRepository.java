package org.bioauth.typeauth.repository;

import org.bioauth.typeauth.domain.GrantedAuthorityClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantedAuthorityClientRepository extends JpaRepository<GrantedAuthorityClient, Integer> {
}
