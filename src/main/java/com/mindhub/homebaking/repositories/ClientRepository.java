package com.mindhub.homebaking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mindhub.homebaking.models.Client;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
        Client findByEmail(String email);

}

