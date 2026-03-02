package com.iolyoliveira.desafio05.services;

import com.iolyoliveira.desafio05.dto.CategoryDTO;
import com.iolyoliveira.desafio05.entities.Category;
import com.iolyoliveira.desafio05.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> result = repository.findAll();
        return result.stream().map(CategoryDTO::new).toList();
    }
}
