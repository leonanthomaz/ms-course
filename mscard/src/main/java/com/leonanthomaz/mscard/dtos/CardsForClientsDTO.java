package com.leonanthomaz.mscard.dtos;

import com.leonanthomaz.mscard.models.CardClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardsForClientsDTO {

    private String name;
    private String flag;
    private BigDecimal limit;

    public static CardsForClientsDTO fromModel(CardClient model){
        return new CardsForClientsDTO( model.getCard().getName(), model.getCard().getFlag().toString(), model.getLimit());
    }

}
