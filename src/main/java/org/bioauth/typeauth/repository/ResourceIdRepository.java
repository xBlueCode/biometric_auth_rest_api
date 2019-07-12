package org.bioauth.typeauth.repository;

import org.bioauth.typeauth.domain.ResourceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceIdRepository extends JpaRepository<ResourceId, Integer> {
}
