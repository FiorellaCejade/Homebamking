package com.mindhub.homebaking.controllers;

import com.mindhub.homebaking.dtos.PdfAplicationDTO;
import com.mindhub.homebaking.models.Account;
import com.mindhub.homebaking.models.Client;
import com.mindhub.homebaking.models.PDFGenerator;
import com.mindhub.homebaking.models.Transaction;
import com.mindhub.homebaking.service.AccountService;
import com.mindhub.homebaking.service.ClientService;
import com.mindhub.homebaking.service.TransactionService;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebaking.models.TransactionType.CREDIT;
import static com.mindhub.homebaking.models.TransactionType.DEBIT;


@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;


    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transaction(
            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String numberOrigen
            , @RequestParam String numberDestino, Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());
        Account accountOrigen = accountService.getAccountByNumber(numberOrigen);
        Set<Account> accounts = client.getAccounts().stream().filter(account1 -> account1.getNumber().equals(numberOrigen)).collect(Collectors.toSet());
        Account accountDestino = accountService.getAccountByNumber(numberDestino);


        if (amount == 0) {
            return new ResponseEntity<>("Missing amount", HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty()) {
            return new ResponseEntity<>("Missing description", HttpStatus.FORBIDDEN);
        }
        if (numberOrigen.isEmpty()) {
            return new ResponseEntity<>("Missing source account", HttpStatus.FORBIDDEN);
        }
        if (numberDestino.isEmpty()) {
            return new ResponseEntity<>("Missing destination account", HttpStatus.FORBIDDEN);
        }
        if (numberOrigen.equals(numberDestino)) {
            return new ResponseEntity<>("The account numbers cannot be the same", HttpStatus.FORBIDDEN);
        }
        if (accountOrigen == null) {
            return new ResponseEntity<>("The account number does not exist", HttpStatus.FORBIDDEN);
        }
        if (accounts.size() == 0) {
            return new ResponseEntity<>("The account number does not belong to you", HttpStatus.FORBIDDEN);
        }
        if (accountDestino == null) {
            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
        }

        if (accountOrigen.getBalance() < amount) {
            return new ResponseEntity<>("The account does not have enough amount", HttpStatus.FORBIDDEN);
        }

        Transaction transactionDebit = new Transaction(amount, DEBIT, LocalDateTime.now(), description + " " + accountDestino.getNumber(), accountOrigen, true);
        transactionDebit.setBalanceCurrent(accountOrigen.getBalance() - amount);
        transactionService.saveTransaction(transactionDebit);

        Transaction transactionCredit = new Transaction(amount, CREDIT, LocalDateTime.now(), description + " " + accountOrigen.getNumber(), accountDestino, true);
        transactionCredit.setBalanceCurrent(accountDestino.getBalance() + amount);
        transactionService.saveTransaction(transactionCredit);

        accountOrigen.setBalance(accountOrigen.getBalance() - amount);
        accountService.saveAccount(accountOrigen);

        accountDestino.setBalance(accountDestino.getBalance() + amount);
        accountService.saveAccount(accountDestino);

        return new ResponseEntity<>("201 CREATED", HttpStatus.CREATED);
    }

    @PostMapping("/transactions/pdf")
    public ResponseEntity<Object> generatePDF(HttpServletResponse httpServletResponse, Authentication authentication, @RequestBody PdfAplicationDTO pdfAplicationDTO)throws Exception {
        Client client = clientService.getClientByEmail(authentication.getName());
        Account accountCurrent = accountService.getAccountByNumber(pdfAplicationDTO.getAccount());
        Long accountId = accountCurrent.getId();
        Set<Transaction> transactions = accountCurrent.getTransactions();

        if(client == null) {
            return new ResponseEntity<>("Client no encontrado", HttpStatus.FORBIDDEN);
        }
        if(accountCurrent == null) {
            return new ResponseEntity<>("account no encontrado", HttpStatus.FORBIDDEN);
        }
        if(pdfAplicationDTO.getDateEnd() == null || pdfAplicationDTO.getDateStart()==null) {
            return new ResponseEntity<>("account no encontrado", HttpStatus.FORBIDDEN);
        }
        if(pdfAplicationDTO.getDateStart().isAfter(pdfAplicationDTO.getDateEnd())){
            return new ResponseEntity<>("account no encontrado", HttpStatus.FORBIDDEN);
        }

        Set<Transaction> transactions1 = transactionService.getTransactionsBetween( transactions,pdfAplicationDTO.getDateStart(),pdfAplicationDTO.getDateEnd());

        PDFGenerator.downloadPdf(transactions1,client,httpServletResponse);
        return new ResponseEntity<>("Created PDF",HttpStatus.CREATED);


    }

}