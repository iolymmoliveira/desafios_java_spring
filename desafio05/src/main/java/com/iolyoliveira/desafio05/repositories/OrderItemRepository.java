package com.iolyoliveira.desafio05.repositories;

import com.iolyoliveira.desafio05.entities.OrderItem;
import com.iolyoliveira.desafio05.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
