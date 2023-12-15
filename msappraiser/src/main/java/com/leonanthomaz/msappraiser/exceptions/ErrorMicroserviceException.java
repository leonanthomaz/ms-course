package com.leonanthomaz.msappraiser.exceptions;

import lombok.Getter;

public class ErrorMicroserviceException extends Exception{

    @Getter
    private Integer status;

    public ErrorMicroserviceException(String msg, Integer status){
        super(msg);
        this.status = status;
    }
}
