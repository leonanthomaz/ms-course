package com.leonanthomaz.msappraiser.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataIssuanceCard {

    private Long idCartao;
    private String cpf;
    private String address;
    private BigDecimal limit;

}
