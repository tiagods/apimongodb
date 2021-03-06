package com.tiagods.apimongodb.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${configuracao.cliente.service.url}", name = "cliente")
public interface ClienteRestClient {
    @GetMapping("/api/clientes/{id}")
    ResponseEntity buscarCliente(@PathVariable String id);
}
