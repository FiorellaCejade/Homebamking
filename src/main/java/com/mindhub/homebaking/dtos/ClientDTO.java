package com.mindhub.homebaking.dtos;

import com.mindhub.homebaking.models.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;
    private String firstName, lastName , email;

    private List<AccountDTO> account;
    private List<ClientLoanDTO> clientLoan;

    private List<CardDTO> card;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.account = client.getAccounts().stream().filter(account -> account.getShow_account() == true).map(account -> new AccountDTO(account)).collect(Collectors.toList());
        this.clientLoan = client.getClientLoan().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toList());
        this.card = client.getCard().stream().filter(card -> card.getShow_card() == true).map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<AccountDTO> getAccount() {
        return account;
    }

    public List<ClientLoanDTO> getClientLoan() {
        return clientLoan;
    }

    public List<CardDTO> getCard() { return card; }

}
