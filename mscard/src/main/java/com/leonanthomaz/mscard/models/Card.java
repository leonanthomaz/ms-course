package com.leonanthomaz.mscard.models;

import com.leonanthomaz.mscard.enums.FlagsCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private FlagsCard flag;
    private BigDecimal income;
    private BigDecimal limit;

    public Card(String name, FlagsCard flag, BigDecimal income, BigDecimal limit) {
        this.name = name;
        this.flag = flag;
        this.income = income;
        this.limit = limit;
    }
}
