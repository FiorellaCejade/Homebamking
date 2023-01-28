package com.mindhub.homebaking.controllers;

import com.mindhub.homebaking.dtos.LoanApplicationDTO;
import com.mindhub.homebaking.dtos.LoanDTO;
import com.mindhub.homebaking.models.*;
import com.mindhub.homebaking.repositories.*;
import com.mindhub.homebaking.service.AccountService;
import com.mindhub.homebaking.service.ClientService;
import com.mindhub.homebaking.service.LoanService;
import com.mindhub.homebaking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LoanService loanService;
    @Autowired
    ClientLoanRepository clientLoanRepository;


    @GetMapping("/loans")
    public List<LoanDTO> getLoansDTO(){
        return loanService.getLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Transactional
//    @RequestMapping(value ="/api/loan", method = RequestMethod.POST)
    @PostMapping("/loans")
    public ResponseEntity<Object> loan(@RequestBody LoanApplicationDTO loanApplication , Authentication authentication){



        Client client = clientService.getClientByEmail(authentication.getName());
        long loanId = loanApplication.getId();
        Loan currentLoan = loanService.getLoanById(loanId);
        Account accountDestino = accountService.getAccountByNumber(loanApplication.getAccounDestino());
        Set<Account> accounts = client.getAccounts().stream().filter(account1 -> account1.getNumber().equals(loanApplication.getAccounDestino())).collect(Collectors.toSet());
        int payments = loanApplication.getPayment();


        if (loanApplication.getAmount()== 0) {
            return new ResponseEntity<>("Missing amount", HttpStatus.FORBIDDEN);
        }
        if (!currentLoan.getPayments().contains(loanApplication.getPayment())) {
            return new ResponseEntity<>("Missing source account", HttpStatus.FORBIDDEN);
        }
        if (currentLoan == null) {
            return new ResponseEntity<>("The requested loan does not exist", HttpStatus.NOT_FOUND);
        }
        if (loanApplication.getAccounDestino().isEmpty()) {
            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if (currentLoan.getMaxAmount() < loanApplication.getAmount()) {
            return new ResponseEntity<>("The amount requested exceeds the limit", HttpStatus.FORBIDDEN);
        }
        if (accountDestino == null) {
            return new ResponseEntity<>("Missing account Destino", HttpStatus.FORBIDDEN);
        }
        if (accounts.size() == 0) {
            return new ResponseEntity<>("The account number does not belong to you", HttpStatus.FORBIDDEN);
        }
        if(client.getClientLoan().stream().filter(clientLoan -> clientLoan.getLoan().getId() == currentLoan.getId()).count() == 1) {
            return new ResponseEntity<>("You cannot request the same loan", HttpStatus.FORBIDDEN);
        }
        if(loanApplication.getAmount() < 80000){
            return new ResponseEntity<>("You cannot request more than 100,000 loans", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplication.getAmount() * currentLoan.getLoanPercentaje() ,loanApplication.getPayment(), client, currentLoan, LocalDateTime.now());
        clientLoanRepository.save(clientLoan);

        Transaction transaction = new Transaction( loanApplication.getAmount(), TransactionType.CREDIT, LocalDateTime.now(), currentLoan.getName() +" "+ "Loan Approved", accountDestino, true);
        transaction.setBalanceCurrent(accountDestino.getBalance() + loanApplication.getAmount());
        transactionService.saveTransaction(transaction);


        accountDestino.setBalance(accountDestino.getBalance() + loanApplication.getAmount());
        accountService.saveAccount(accountDestino);

        return new ResponseEntity<>("201 Created", HttpStatus.CREATED);



    }

    @PostMapping("/loans/admin")
    public ResponseEntity<Object> createLoanAdmin(Authentication authentication, @RequestParam String name, @RequestParam int maxAmount, @RequestParam Integer[] payments , @RequestParam double loanPercentaje) {
        Client client = clientService.getClientByEmail(authentication.getName());
        if(client.getFirstName().equals("admin")) {
            List<Integer> newList = Arrays.stream(payments).collect(Collectors.toList());
            Loan newLoan = new Loan(name, maxAmount, newList, loanPercentaje);
            loanService.saveLoan(newLoan);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}