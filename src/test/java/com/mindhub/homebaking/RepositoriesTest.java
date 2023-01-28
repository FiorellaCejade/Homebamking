package com.mindhub.homebaking;

import com.mindhub.homebaking.models.*;
import com.mindhub.homebaking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

//    @Autowired
//    LoanRepository loanRepository;
//
//    @Autowired
//    CardRepository cardRepository;
//
//    @Autowired
//    ClientRepository clientRepository;
//
//    @Autowired
//    TransactionRepository transactionRepository;
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    //LoanRepository
//
//    @Test
//    public void existLoans(){
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans,is(not(empty())));
//
//    }
//
//    @Test
//    public void existPersonalLoan(){
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
//    }
//
//    //CardRepository
//
//    @Test
//    public void existCards() {
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, is(not(empty())));
//    }
//
//    @Test
//    public void existColorCard() {
//        List<Card> cards = cardRepository.findAll().stream().filter(card -> card.getColor() == ColorType.SILVER).collect(Collectors.toList());
//        assertThat(cards, is(not(empty())));
//    }
//
//    //ClientRepository
//
//    @Test
//    public void existClientByEmail() {
//        Client client = clientRepository.findByEmail("melba@mindHub.com");
//        assertThat(client, is(notNullValue()));
//    }
//    @Test
//    public void existClients() {
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients, is(not(empty())));
//    }
//
//
//
//
//    //TransactionRepository
//
//    @Test
//    public void existTransactionType() {
//        List<Transaction> transactions = transactionRepository.findAll().stream().filter(transaction -> transaction.getType() == TransactionType.DEBIT).collect(Collectors.toList());
//        assertThat(transactions, is(not(empty())));
//    }
//
//    @Test
//    public void existTransactionById() {
//        Transaction transaction = transactionRepository.findById(5L).orElse(null);
//        assertThat(transaction, is(notNullValue()));
//    }
//
//    //AccountRepository
//
//    @Test
//    public void existAccountByNumber() {
//        Account account = accountRepository.findByNumber("VIN001");
//        assertThat(account, is(notNullValue()));
//    }
//
//    @Test
//    public void existAccountByBalance() {
//        List<Account> accounts = accountRepository.findAll().stream().filter(account -> account.getBalance() > 150000).collect(Collectors.toList());
//        assertThat(accounts, is(not(empty())));
//    }
}


