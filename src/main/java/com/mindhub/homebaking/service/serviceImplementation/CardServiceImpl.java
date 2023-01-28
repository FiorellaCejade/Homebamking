package com.mindhub.homebaking.service.serviceImplementation;

import com.mindhub.homebaking.models.Card;
import com.mindhub.homebaking.repositories.CardRepository;
import com.mindhub.homebaking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;


    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);

    }

    @Override
    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }
}
