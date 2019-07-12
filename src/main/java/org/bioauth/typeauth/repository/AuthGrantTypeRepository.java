package org.bioauth.typeauth.repository;

import org.bioauth.typeauth.domain.AuthGrantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthGrantTypeRepository extends JpaRepository<AuthGrantType, Integer> {

	AuthGrantType findAuthGrantTypeByType(String type);
}
