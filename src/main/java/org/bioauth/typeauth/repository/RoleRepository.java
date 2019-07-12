package org.bioauth.typeauth.repository;

import org.bioauth.typeauth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findRoleByRole(String role);

}
