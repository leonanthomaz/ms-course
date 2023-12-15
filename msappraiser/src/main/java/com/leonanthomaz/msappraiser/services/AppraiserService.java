package com.leonanthomaz.msappraiser.services;

import com.leonanthomaz.msappraiser.exceptions.ClientNotFoundException;
import com.leonanthomaz.msappraiser.exceptions.ErrorMicroserviceException;
import com.leonanthomaz.msappraiser.exceptions.ErrorRequestException;
import com.leonanthomaz.msappraiser.infra.RequestIssuancePublisher;
import com.leonanthomaz.msappraiser.repositories.CardResourceClient;
import com.leonanthomaz.msappraiser.repositories.ClientResourceClient;
import com.leonanthomaz.msappraiser.models.*;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppraiserService {

    private final ClientResourceClient fClient;
    private final CardResourceClient fCard;
    private final RequestIssuancePublisher requestIssuancePublisher;
    
    public SituationClient getSituationClient(String cpf) throws ClientNotFoundException, ErrorMicroserviceException {
        try{
            ResponseEntity<ClientData> resultclient = fClient.getClient(cpf);
            ResponseEntity<List<CardData>> resultcards = fCard.getCards(cpf);

            return SituationClient
                    .builder()
                    .client(resultclient.getBody())
                    .cards(resultcards.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new ClientNotFoundException();
            }

            throw new ErrorMicroserviceException(e.getMessage(), status);

        }
    }

    public ReturnAssessment returnAssessment(String cpf, Long income) throws ClientNotFoundException, ErrorMicroserviceException {
        try{
            ResponseEntity<ClientData> resultClient = fClient.getClient(cpf);
            ResponseEntity<List<Card>> resultCards = fCard.getCardsForIncome(income);

            List<Card> cards = resultCards.getBody();

            var listCardsApproved = cards.stream().map(card -> {

                ClientData clientData = resultClient.getBody();

                BigDecimal limitBasic = card.getLimit();
                BigDecimal ageDB = BigDecimal.valueOf(clientData.getAge());
                var factor = ageDB.divide(BigDecimal.valueOf(10));
                BigDecimal limitApproved = factor.multiply(limitBasic);

                CardApproved approved = new CardApproved();
                approved.setCard(card.getName());
                approved.setFlag(card.getFlag());
                approved.setLimit(limitApproved);

                return approved;
            }).collect(Collectors.toList());

            return new ReturnAssessment(listCardsApproved);

        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new ClientNotFoundException();
            }

            throw new ErrorMicroserviceException(e.getMessage(), status);

        }
    }

    public ProtocolRequestCard requestCard(DataIssuanceCard data){
        try {
            requestIssuancePublisher.requestCard(data);
            var protocol = UUID.randomUUID().toString();
            return new ProtocolRequestCard(protocol);

        }catch (Exception e){
            throw new ErrorRequestException("Erro ao processar pedido." + e.getMessage());
        }
    }
}
