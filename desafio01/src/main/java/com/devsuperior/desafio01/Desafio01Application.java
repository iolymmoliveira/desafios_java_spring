package com.devsuperior.desafio01;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Desafio01Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Desafio01Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Pedido código ");
        System.out.println("Valor total: R$ ");
    }
}
