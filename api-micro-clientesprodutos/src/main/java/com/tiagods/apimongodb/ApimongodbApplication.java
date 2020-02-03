package com.tiagods.apimongodb;

import com.tiagods.apimongodb.model.ClienteProduto;
import com.tiagods.apimongodb.repository.ClienteProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Arrays;

@SpringBootApplication
@EnableFeignClients
public class ApimongodbApplication{

	public static void main(String[] args) {
		SpringApplication.run(ApimongodbApplication.class, args);
	}

}
