package com.leonanthomaz.mscard.controllers;

import com.leonanthomaz.mscard.dtos.CardDTO;
import com.leonanthomaz.mscard.dtos.CardsForClientsDTO;
import com.leonanthomaz.mscard.models.Card;
import com.leonanthomaz.mscard.models.CardClient;
import com.leonanthomaz.mscard.services.CardService;
import com.leonanthomaz.mscard.services.CardClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cards")
@RequiredArgsConstructor
@Slf4j
public class CardController {

    private final CardService cardService;
    private final CardClientService cardClientService;

    @GetMapping
    public ResponseEntity<String> home(){
        log.info("ACESSANDO PÁGINA INICIAL DO CARTÃO");
        return ResponseEntity.ok().body("CARTÃO!!!");
    }

    @PostMapping
    public ResponseEntity registerCard(@RequestBody CardDTO data){
        Card card = new Card();
        BeanUtils.copyProperties(data, card);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.save(card));
    }

    @GetMapping(params = "income")
    public ResponseEntity<List<Card>> getCardsForIncome(@RequestParam("income") Long income){
        return ResponseEntity.ok().body(cardService.getCardForIncome(income));
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CardsForClientsDTO>> getCardsForCpf(@RequestParam("cpf") String cpf){
        List<CardClient> list = cardClientService.listByCpf(cpf);
        List<CardsForClientsDTO> result = list.stream().map(CardsForClientsDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }
}
