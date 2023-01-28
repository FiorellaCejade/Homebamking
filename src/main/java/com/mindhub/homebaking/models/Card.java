package com.mindhub.homebaking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    private String cardHolder;
    private String number;
    private int cvv;
     private CardType type;
     private ColorType color;
     private LocalDate fromDate;
     private LocalDate thruDate;
     private boolean show_card;

     public Card() {
    }

    public Card( Client client,CardType type,ColorType color, String cardHolder, String number, int cvv,LocalDate fromDate, LocalDate thruDate, boolean show_card, Account account){
         this.client = client;
         this.type = type;
         this.color = color;
         this.cardHolder = cardHolder;
         this.number = number;
         this.cvv = cvv;
         this.fromDate = fromDate;
         this.thruDate = thruDate;
         this.show_card = show_card;
         this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public CardType getType() {
        return type;
    }

    public boolean getShow_card() {
        return show_card;
    }

    public void setShow_card(boolean show_card) {
        this.show_card = show_card;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public ColorType getColor() { return color; }

    public void setColor(ColorType color) {
        this.color = color;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
