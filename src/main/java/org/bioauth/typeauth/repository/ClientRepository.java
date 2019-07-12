package org.bioauth.typeauth.repository;

import org.bioauth.typeauth.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	Client findClientByClientId(String clientId);
}
