package com.iolyoliveira.desafio05.services;

import com.iolyoliveira.desafio05.dto.ProductDTO;
import com.iolyoliveira.desafio05.entities.Product;
import com.iolyoliveira.desafio05.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id).get();
        return new ProductDTO(product);
    }
}
