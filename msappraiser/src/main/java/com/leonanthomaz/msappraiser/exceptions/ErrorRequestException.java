package com.leonanthomaz.msappraiser.exceptions;

public class ErrorRequestException extends RuntimeException{
    public ErrorRequestException(String message) {
        super(message);
    }
}
