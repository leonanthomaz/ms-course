package com.leonanthomaz.msclient.controllers;

import com.leonanthomaz.msclient.dtos.ClientSaveRequest;
import com.leonanthomaz.msclient.models.Client;
import com.leonanthomaz.msclient.services.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService service;

    @GetMapping("home")
    public ResponseEntity<String> home(){
        log.info("Obtendo status de consulta do cpf...");
        return ResponseEntity.ok().body("PÁGINA INICIAL");
    }

    @PostMapping
    public ResponseEntity<Client> save(@RequestBody ClientSaveRequest request){
        Client client = request.toModel();
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(client.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).body(service.save(client));
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<Client> getClient(@RequestParam("cpf") String cpf){
        return service.getByCPF(cpf).map(user -> ResponseEntity.ok().body(user)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
