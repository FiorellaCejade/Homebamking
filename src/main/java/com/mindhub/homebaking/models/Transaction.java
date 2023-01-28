package com.mindhub.homebaking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;
    private double amount;
    private String description;

    private LocalDateTime date;
    private TransactionType type;
    private double balanceCurrent;
    private boolean show_transaction;

    public Transaction(){}
    public Transaction(double amount,TransactionType type,LocalDateTime date, String description, Account account, boolean show_transaction) {

        this.amount = amount;
        this.description = description;
        this.account = account;
        this.type = type;
        this.date = date;
        this.show_transaction = show_transaction;

    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalanceCurrent() {
        return balanceCurrent;
    }

    public void setBalanceCurrent(double balanceCurrent) {
        this.balanceCurrent = balanceCurrent;
    }

    public boolean getShow_transaction() {
        return show_transaction;
    }

    public void setShow_transaction(boolean show_transaction) {
        this.show_transaction = show_transaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() { return date; }

    public void setDate (LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

}
