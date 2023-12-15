package com.leonanthomaz.msappraiser.controllers;

import com.leonanthomaz.msappraiser.exceptions.ClientNotFoundException;
import com.leonanthomaz.msappraiser.exceptions.ErrorMicroserviceException;
import com.leonanthomaz.msappraiser.exceptions.ErrorRequestException;
import com.leonanthomaz.msappraiser.models.*;
import com.leonanthomaz.msappraiser.services.AppraiserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("credit-analysis")
@RequiredArgsConstructor
@Slf4j
public class AppraiserController {

    private final AppraiserService appraiserService;

    @GetMapping
    public ResponseEntity<String> status(){
        return ResponseEntity.ok().body("APPRAISER OK");
    }

    @GetMapping(path = "situation", params = "cpf")
    public ResponseEntity querySituationClient(@RequestParam String cpf) {
        try{
            SituationClient situationClient = appraiserService.getSituationClient(cpf);
            return ResponseEntity.ok().body(situationClient);
        }catch (ClientNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (ErrorMicroserviceException ex){
            return ResponseEntity.status(HttpStatus.resolve(ex.getStatus())).body(ex.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity makeCreditAssessment(@RequestBody DataAssessment data){
        log.info("DADOS: " + data.getCpf() + " - " + data.getIncome());
        try{
            ReturnAssessment returnAssessment = appraiserService.returnAssessment(data.getCpf(), data.getIncome());
            return ResponseEntity.ok().body(returnAssessment);
        }catch (ClientNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (ErrorMicroserviceException ex){
            return ResponseEntity.status(HttpStatus.resolve(ex.getStatus())).body(ex.getMessage());
        }
    }

    @PostMapping("subscribe")
    public ResponseEntity requestCard(@RequestBody DataIssuanceCard data){
        try {

            ProtocolRequestCard protocol = appraiserService.requestCard(data);
            return ResponseEntity.ok(protocol);

        }catch (ErrorRequestException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
