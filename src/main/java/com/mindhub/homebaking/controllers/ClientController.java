package com.mindhub.homebaking.controllers;

import com.mindhub.homebaking.dtos.ClientDTO;
import com.mindhub.homebaking.models.Account;
import com.mindhub.homebaking.models.AccountType;
import com.mindhub.homebaking.models.Client;
import com.mindhub.homebaking.service.AccountService;
import com.mindhub.homebaking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
//        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
        return clientService.findAllClient().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }


    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
//        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
        return new ClientDTO(clientService.getClientById(id));
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email
            , @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.getClientByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
       Account account =new Account("VIN-"+ (int)(Math.random()*(999999 - 100000) + 100000), 0, LocalDateTime.now(), client,true, AccountType.SAVING);
       accountService.saveAccount(account);
        return new ResponseEntity<>("Account created successfully",HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
        return new ClientDTO(clientService.getClientByEmail(authentication.getName()));
    }

}
