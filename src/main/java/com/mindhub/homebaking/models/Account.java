package com.mindhub.homebaking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    Set<Card> cards = new HashSet<>();


    private String number;
    private double balance;
    private LocalDateTime creationDate;
    private boolean show_account;
    private AccountType accountType;

    public Account() { }

    public Account (String number, double balance , LocalDateTime creationDate, Client client, boolean show_account, AccountType accountType) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.client = client;
        this.show_account = show_account;
        this.accountType = accountType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean getShow_account() {
        return show_account;
    }

    public void setShow_account(boolean show_account) {
        this.show_account = show_account;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void addTransaction (Transaction transaction){
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addCard (Card card) {
        card.setAccount(this);
        this.cards.add(card);
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
