package com.leonanthomaz.mscard.dtos;

import com.leonanthomaz.mscard.enums.FlagsCard;
import com.leonanthomaz.mscard.models.Card;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardDTO {

    private String name;
    private FlagsCard flag;
    private BigDecimal income;
    private BigDecimal limit;

    public Card toModel(){
        return new Card(this.name, this.flag, this.income, this.limit);
    }

}
