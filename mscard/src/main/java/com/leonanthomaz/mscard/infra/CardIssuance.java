package com.leonanthomaz.mscard.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonanthomaz.mscard.models.Card;
import com.leonanthomaz.mscard.models.CardClient;
import com.leonanthomaz.mscard.models.DataIssuanceCard;
import com.leonanthomaz.mscard.repositories.CardClientRepository;
import com.leonanthomaz.mscard.repositories.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardIssuance {

    private final CardRepository repository;
    private final CardClientRepository cardClientRepository;

    @RabbitListener(queues = "${mq.queues.card-issuance}")
    public void receiveEequestIssue(@Payload String payload){

        try{
            var mapper = new ObjectMapper();
            DataIssuanceCard data = mapper.readValue(payload, DataIssuanceCard.class);
            Card card = repository.findById(data.getIdCartao()).orElseThrow();

            CardClient cc = new CardClient();
            cc.setCard(card);
            cc.setCpf(data.getCpf());
            cc.setLimit(data.getLimit());

            cardClientRepository.save(cc);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }
}
