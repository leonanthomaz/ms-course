package com.leonanthomaz.msappraiser.exceptions;

public class ClientNotFoundException extends Exception{

    public ClientNotFoundException(){
        super("Dados do cliente não encontrado.");
    }
}
