package com.devsuperior.desafio01.services;

import com.devsuperior.desafio01.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private ShippingService shippingService;

    public double total(Order order) {
        double basicWithDiscount = order.getBasic() * (1 - order.getDiscount() / 100);
        double tax = shippingService.shipment(order);
        return basicWithDiscount + tax;
    }
}
