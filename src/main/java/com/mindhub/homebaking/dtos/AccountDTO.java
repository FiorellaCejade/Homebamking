package com.mindhub.homebaking.dtos;

import com.mindhub.homebaking.models.Account;
import com.mindhub.homebaking.models.AccountType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;
    private String number;
    private double balance;
    private LocalDateTime creationDate;

    private List<TransactionDTO> transaction;

    private boolean show_account;

    private AccountType accountType;

    private Set<CardDTO> cards;


    public AccountDTO(Account account){
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.creationDate = account.getCreationDate();
        this.id = account.getId();
        this.transaction = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
        this.show_account = account.getShow_account();
        this.accountType = account.getAccountType();
        this.cards = account.getCards().stream().filter(card -> card.getShow_card()).map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public boolean getShow_account() {
        return show_account;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<TransactionDTO> getTransaction() {
        return transaction;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
