package com.iolyoliveira.desafio05.repositories;

import com.iolyoliveira.desafio05.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
