package com.tiagods.apimongodb;

import com.tiagods.apimongodb.model.Produto;
import com.tiagods.apimongodb.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ApimongodbApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApimongodbApplication.class, args);
	}

	@Autowired
	private ProdutoRepository repository;

	@Override
	public void run(String... args) throws Exception {

		if(repository.count()==0){
			repository.saveAll(
					Arrays.asList(
							new Produto(null,"COMUM"),
							new Produto(null, "ESCOLAR"),
							new Produto(null, "SENIOR")
		));}

	}
}
