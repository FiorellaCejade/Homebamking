package com.mindhub.homebaking.dtos;

import com.mindhub.homebaking.models.Account;
import com.mindhub.homebaking.models.Card;
import com.mindhub.homebaking.models.CardType;
import com.mindhub.homebaking.models.ColorType;

import java.time.LocalDate;
import java.util.Set;

public class CardDTO {

    private String cardHolder;
    private String number;
    private int cvv;
    private CardType type;
    private ColorType color;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private boolean show_card;

    private Account account;

    private Long id;

    public CardDTO( Card card) {
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.type = card.getType();
        this.color = card.getColor();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.show_card = card.getShow_card();
        this.id = card.getId();
        this.account = card.getAccount();


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

    public ColorType getColor() {
        return color;
    }

    public Long getId() {
        return id;
    }

    public boolean getShow_card() {
        return show_card;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

}
