package com.leonanthomaz.mscard.services;

import com.leonanthomaz.mscard.models.CardClient;
import com.leonanthomaz.mscard.repositories.CardClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardClientService {

    private final CardClientRepository repository;
    public List<CardClient> listByCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}
