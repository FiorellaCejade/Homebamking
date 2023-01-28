package com.mindhub.homebaking.service.serviceImplementation;

import com.mindhub.homebaking.models.Transaction;
import com.mindhub.homebaking.repositories.TransactionRepository;
import com.mindhub.homebaking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);

    }

    @Override
    public Set<Transaction> getTransactionsBetween(Set<Transaction> transactions, LocalDateTime start, LocalDateTime end) {
        return transactions.stream().filter(transaction -> transaction.getDate().isAfter(start) && transaction.getDate().isBefore(end)).collect(Collectors.toSet());
    }

    @Override
    public void saveAllTransactions(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }
}
