package com.mindhub.homebaking.service;

import com.mindhub.homebaking.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface TransactionService {

    void saveTransaction(Transaction transaction);

    Set<Transaction> getTransactionsBetween(Set<Transaction> transactions, LocalDateTime start, LocalDateTime end);


    void saveAllTransactions(List<Transaction> transactions);
}
