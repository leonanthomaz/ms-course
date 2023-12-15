package com.leonanthomaz.mscard.enums;

public enum FlagsCard {
    MASTERCARD("MasterCard"),
    VISA("Visa");

    private String flag;

    FlagsCard(String flag) {
        this.flag = flag;
    }
}
