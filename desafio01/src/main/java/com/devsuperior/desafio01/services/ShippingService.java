package com.devsuperior.desafio01.services;

import com.devsuperior.desafio01.entities.Order;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    public double shipment(Order order) {
        double basicValue = order.getBasic();

        if (basicValue < 100.00) {
            return 20.0;
        } else if (basicValue <= 200.00) {
            return 12.0;
        } else {
            return 0.0;
        }
    }
}
