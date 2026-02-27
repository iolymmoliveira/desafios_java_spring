package com.iolyoliveira.desafio05.repositories;

import com.iolyoliveira.desafio05.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
