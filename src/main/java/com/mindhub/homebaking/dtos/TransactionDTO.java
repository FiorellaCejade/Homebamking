package com.mindhub.homebaking.dtos;

import com.mindhub.homebaking.models.Transaction;
import com.mindhub.homebaking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private long id;
    private double amount;
    private String description;

    private LocalDateTime date;
    private TransactionType type;

    private double balanceCurrent;

    private boolean show_transaction;

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.show_transaction = transaction.getShow_transaction();
        this.balanceCurrent = transaction.getBalanceCurrent();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean getShow_transaction() {
        return show_transaction;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public double getBalanceCurrent() {
        return balanceCurrent;
    }
}
