package com.tiagods.apimongodb;

import com.tiagods.apimongodb.model.Cliente;
import com.tiagods.apimongodb.repositoy.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ApimongodbApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApimongodbApplication.class, args);
	}

	@Autowired
	private ClienteRepository repository;

	@Override
	public void run(String... args) throws Exception {
		if(repository.count()==0) {
			List list = Arrays.asList(
					new Cliente(null, "Joao Barbosa", 10055599912L, "Rua A", "1133335555", "joao@mail.com"),
					new Cliente(null, "Marcos Barbosa", 50055599913L, "Rua B", "1133335588", "marcos@mail.com"),
					new Cliente(null, "Fabiano Araujo", 10055596912L, "Rua A", "1133335555", "joao@mail.com"),
					new Cliente(null, "Juliano", 55555599913L, "Rua B", "1133335588", "marcos@mail.com"),
					new Cliente(null, "Joao Silva", 99955599912L, "Rua A", "1133335555", "joao@mail.com"),
					new Cliente(null, "Marcos Felix", 70055599913L, "Rua B", "1133335588", "marcos@mail.com")
			);
			repository.saveAll(list);
		}
	}
}
