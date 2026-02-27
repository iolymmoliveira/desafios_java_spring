package com.iolyoliveira.desafio05.services;

import com.iolyoliveira.desafio05.dto.OrderDTO;
import com.iolyoliveira.desafio05.dto.OrderItemDTO;
import com.iolyoliveira.desafio05.entities.Order;
import com.iolyoliveira.desafio05.entities.OrderItem;
import com.iolyoliveira.desafio05.entities.OrderStatus;
import com.iolyoliveira.desafio05.entities.Product;
import com.iolyoliveira.desafio05.repositories.OrderItemRepository;
import com.iolyoliveira.desafio05.repositories.OrderRepository;
import com.iolyoliveira.desafio05.repositories.ProductRepository;
import com.iolyoliveira.desafio05.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado")
        );
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        order.setClient(userService.authenticated());

        for (OrderItemDTO item : dto.getItems()) {
            Product product = productRepository.getReferenceById(item.getProductId());
            OrderItem orderItem = new OrderItem(order, product, item.getQuantity(), product.getPrice());
            order.getItems().add(orderItem);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }
}
