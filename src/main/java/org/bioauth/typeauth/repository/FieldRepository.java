package org.bioauth.typeauth.repository;

import org.bioauth.typeauth.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long> {
}
