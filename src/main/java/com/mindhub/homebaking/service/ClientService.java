package com.mindhub.homebaking.service;

import com.mindhub.homebaking.models.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAllClient();

    Client getClientById(Long id);

    Client getClientByEmail(String email);

    void saveClient(Client client);
}
