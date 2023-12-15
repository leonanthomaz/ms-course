package com.leonanthomaz.mscard.services;

import com.leonanthomaz.mscard.models.Card;
import com.leonanthomaz.mscard.repositories.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository repository;

    @Transactional
    public Card save (Card card){
        return repository.save(card);
    }

    public List<Card> getCardForIncome(Long income){
        BigDecimal obj = BigDecimal.valueOf(income);
        return repository.findByIncomeLessThanEqual(obj);
    }
}
