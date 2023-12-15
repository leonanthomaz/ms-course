package com.leonanthomaz.msappraiser.repositories;

import com.leonanthomaz.msappraiser.models.Card;
import com.leonanthomaz.msappraiser.models.CardData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscard", path = "cards")
public interface CardResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CardData>> getCards(@RequestParam("cpf") String cpf);

    @GetMapping(params = "income")
    ResponseEntity<List<Card>> getCardsForIncome(@RequestParam("income") Long income);
}
