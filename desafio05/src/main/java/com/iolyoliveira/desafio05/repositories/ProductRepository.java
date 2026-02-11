package com.iolyoliveira.desafio05.repositories;

import com.iolyoliveira.desafio05.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
