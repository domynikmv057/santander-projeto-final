package com.example.santanderavaliacaospring;

import com.example.santanderavaliacaospring.modelos.Resistencia;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SantanderAvaliacaoSpringApplication {
	public static Resistencia resistencia = new Resistencia();
	public static void main(String[] args) {
		SpringApplication.run(SantanderAvaliacaoSpringApplication.class, args);
	}

}
