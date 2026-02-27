package com.iolyoliveira.desafio05.services;

import com.iolyoliveira.desafio05.dto.OrderDTO;
import com.iolyoliveira.desafio05.entities.Order;
import com.iolyoliveira.desafio05.repositories.OrderRepository;
import com.iolyoliveira.desafio05.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado")
        );
        return new OrderDTO(order);
    }
}
