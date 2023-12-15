package com.leonanthomaz.msappraiser.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReturnAssessment {

    List<CardApproved> cards;
}
