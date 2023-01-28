package com.mindhub.homebaking.controllers;

import com.mindhub.homebaking.dtos.CardPaymentDTO;
import com.mindhub.homebaking.models.*;
import com.mindhub.homebaking.service.AccountService;
import com.mindhub.homebaking.service.CardService;
import com.mindhub.homebaking.service.ClientService;
import com.mindhub.homebaking.service.TransactionService;
import com.mindhub.homebaking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebaking.models.TransactionType.DEBIT;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(
            @RequestParam CardType type, @RequestParam ColorType color, @RequestParam String account, Authentication authentication) {
        Client client = clientService.getClientByEmail(authentication.getName());
        Set<Card> cards = client.getCard().stream().filter(c -> c.getShow_card()).collect(Collectors.toSet());
        Account accountCurrent = accountService.getAccountByNumber(account);
        List<Card> cardType = cards.stream().filter(card -> card.getType() == type).filter(card -> card.getShow_card()).collect(Collectors.toList());

        if(cards.size() ==3){
            return new ResponseEntity<>("403 Forbidden", HttpStatus.FORBIDDEN);
        }
        if(accountCurrent == null ){
            return new ResponseEntity<>("Account invalid", HttpStatus.FORBIDDEN);
        }
        if (cards.size() == 6) {
            return new ResponseEntity<>("I exceed the limit of cards", HttpStatus.FORBIDDEN);
        } else {
            if (cardType.size() == 3) {
                return new ResponseEntity<>("You cannot have more than 3 cards of the same type.", HttpStatus.FORBIDDEN);
            } else {
                if (cardType.stream().filter(card -> card.getColor() == color).count() == 1) {
                    return new ResponseEntity<>("You cannot request two identical cards", HttpStatus.FORBIDDEN);

                } else {
                    Card card = new Card(client, type, color, client.getFirstName() + " " + client.getLastName(), CardUtils.randomNumCard(), CardUtils.createCvv(), LocalDate.now(), LocalDate.now().plusYears(5), true, accountCurrent);
                    cardService.saveCard(card);
                    return new ResponseEntity<>("201 CREATED", HttpStatus.CREATED);
                }
            }
        }
    }

    @PatchMapping("/clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCard(Authentication authentication, @PathVariable Long id) {
        Client client = clientService.getClientByEmail(authentication.getName());
        Set<Card> cards = client.getCard();
        Card cardsCurrent = cardService.getCardById(id);
        Card cardFound = cards.stream().filter(card -> card == cardsCurrent).findFirst().orElse(null);
        cardService.getCardById(cardFound.getId()).setShow_card(false);
        cardService.saveCard(cardFound);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/clients/current/payment")
    public ResponseEntity<Object> paymentsCard(Authentication authentication, @RequestBody CardPaymentDTO cardPaymentDTO) {
        String date = String.valueOf(cardPaymentDTO.getThruDate());
        Client client = clientService.getClientByEmail(authentication.getName());
        Set<Card> cards = client.getCard();
        Card cardCurrent = cards.stream().filter(card -> card.getNumber().equals(cardPaymentDTO.getNumber())).findFirst().orElse(null);
        String dateCurrent = String.valueOf(cardCurrent.getThruDate());
        Account accountCurrent = accountService.getAccountByNumber(cardCurrent.getAccount().getNumber());


        if(cardPaymentDTO.getNumber().isEmpty()) {
            return new ResponseEntity<>("Missing Account Destiny", HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getCardHolder().isEmpty()){
            return new ResponseEntity<>("Missing Card Holder",HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getAmount() == 0){
            return new ResponseEntity<>("Missing amount",HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getNumber().isEmpty()){
            return new ResponseEntity<>("Missing Number",HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getDescription().isEmpty()){
            return new ResponseEntity<>("Missing description",HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getCvv() == 0){
            return new ResponseEntity<>("Missing cvv",HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getCvv() != cardCurrent.getCvv()){
            return new ResponseEntity<>("This is not valid cvv",HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getThruDate() == null) {
            return new ResponseEntity<>("This is not valid date",HttpStatus.FORBIDDEN);
        }
        if(!Objects.equals(date, dateCurrent)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(LocalDate.now().isAfter(cardCurrent.getThruDate())){
            return new ResponseEntity<>("Expired card",HttpStatus.FORBIDDEN);
        }
        if(cardCurrent.getAccount().getBalance() < cardPaymentDTO.getAmount()){
            return new ResponseEntity<>("Not amount",HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction(cardPaymentDTO.getAmount(), DEBIT, LocalDateTime.now(), cardPaymentDTO.getDescription(), accountCurrent, true);
        transaction.setBalanceCurrent(accountCurrent.getBalance() - cardPaymentDTO.getAmount());
        accountCurrent.setBalance(accountCurrent.getBalance() - cardPaymentDTO.getAmount());
        transactionService.saveTransaction(transaction);
        accountService.saveAccount(accountCurrent);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}




