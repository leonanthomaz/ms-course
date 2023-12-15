package com.leonanthomaz.msclient.repositories;

import com.leonanthomaz.msclient.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCpf(String cpf);
}
