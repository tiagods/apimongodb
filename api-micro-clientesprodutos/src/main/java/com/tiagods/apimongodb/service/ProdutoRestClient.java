package com.tiagods.apimongodb.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${configuracao.produto.service.url}", name = "produto")
public interface ProdutoRestClient {
    @GetMapping("/api/produtos/{id}")
    ResponseEntity buscarProduto(@PathVariable String id);
}
