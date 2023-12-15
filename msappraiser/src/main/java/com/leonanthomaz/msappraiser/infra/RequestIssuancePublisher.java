package com.leonanthomaz.msappraiser.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonanthomaz.msappraiser.models.DataIssuanceCard;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestIssuancePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueIssuanceCard;

    public void requestCard(DataIssuanceCard data) throws JsonProcessingException {
        var json = convertToJson(data);
        rabbitTemplate.convertAndSend(queueIssuanceCard.getName(), json);
    }

    private String convertToJson(DataIssuanceCard obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(obj);
        return json;
    }

}
