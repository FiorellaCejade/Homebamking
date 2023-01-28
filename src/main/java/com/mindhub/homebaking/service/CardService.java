package com.mindhub.homebaking.service;

import com.mindhub.homebaking.models.Card;

public interface CardService {

    void saveCard(Card card);
    Card getCardById(Long id);
}
