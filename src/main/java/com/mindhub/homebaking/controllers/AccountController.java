package com.mindhub.homebaking.controllers;

import com.mindhub.homebaking.dtos.AccountDTO;
import com.mindhub.homebaking.models.*;
import com.mindhub.homebaking.service.AccountService;
import com.mindhub.homebaking.service.ClientService;
import com.mindhub.homebaking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccounts().stream().map(account -> new AccountDTO(account)).collect(toList());
    };


    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){

        return new AccountDTO(accountService.getAccountById(id));
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount( Authentication authentication, @RequestParam AccountType accountType){

        Client client = clientService.getClientByEmail(authentication.getName());

        if(client.getAccounts().stream().filter(account -> account.getShow_account()).collect(Collectors.toSet()).size() == 3){
            return new ResponseEntity<>("403 Forbidden", HttpStatus.FORBIDDEN);
        }

        Account account = new Account("VIN"+ (int)(Math.random()*(999999 - 100000) + 100000), 0, LocalDateTime.now(), client, true, accountType);
        accountService.saveAccount(account);
        return new ResponseEntity<>("201 Created",HttpStatus.CREATED);
    }

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccountsClient(Authentication authentication) {
        Client client = clientService.getClientByEmail(authentication.getName());
        return client.getAccounts().stream().filter(account -> account.getShow_account()).map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    @PatchMapping("/clients/current/account/{id}")
    public ResponseEntity<Object> deleteAccount ( Authentication authentication, @PathVariable Long id){
        Client client = clientService.getClientByEmail(authentication.getName());
        Set<Account> accounts = client.getAccounts();
        Account accountCurrent = accountService.getAccountById(id);
        Account accountFound = accounts.stream().filter(account -> account == accountCurrent).findFirst().orElse(null);


        if (accountCurrent.getBalance() > 0) {
            return new ResponseEntity<>("You cannot delete an account with a balance",HttpStatus.FORBIDDEN);
        }
        if(accountCurrent.getCards().size() > 0) {
            return new ResponseEntity<>("You cannot delete an account with a balance",HttpStatus.FORBIDDEN);
        }

        accountService.getAccountById(accountFound.getId()).setShow_account(false);
        List<Transaction> transactionFound = accountFound.getTransactions().stream().map(transaction ->{
            transaction.setShow_transaction(false);
            return transaction;
        }).collect(Collectors.toList());
        transactionService.saveAllTransactions(transactionFound);
        accountService.saveAccount(accountFound);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
