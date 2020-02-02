package com.tiagods.apimongodb;

import com.tiagods.apimongodb.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ApimongodbApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApimongodbApplication.class, args);
	}

	@Autowired
	private ClienteService clienteService;

	@Override
	public void run(String... args) throws Exception {
		if(clienteService.listar().isEmpty()){

		}
	}
}
