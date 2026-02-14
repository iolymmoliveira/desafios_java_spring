package com.iolyOliveira.desafio03.repositories;

import com.iolyOliveira.desafio03.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
