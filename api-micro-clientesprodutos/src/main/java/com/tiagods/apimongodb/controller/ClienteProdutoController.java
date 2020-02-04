package com.tiagods.apimongodb.controller;

import com.tiagods.apimongodb.model.ClienteProduto;
import com.tiagods.apimongodb.service.ClienteProdutoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/clientesprodutos")
public class ClienteProdutoController {

    @Autowired
    private ClienteProdutoService service;

    @ApiOperation(value = "Retorna uma lista de vinculos cliente e produto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de produtos"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<?> listarTodos(){
        return ResponseEntity.ok().body(service.listar());
    }

    @ApiOperation(value = "Retorna um ClienteProduto pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um ClienteProduto"),
            @ApiResponse(code = 404, message = "ClienteProduto não existe na base"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@Valid @PathVariable String id){
        return ResponseEntity.ok().body(service.buscarPorId(id));
    }

    @ApiOperation(value = "Salvar um novo ClientePproduto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna Created caso ClienteProduto for salvo"),
           @ApiResponse(code = 400, message = "ClienteProduto com parametro nulo ou invalido"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<?> salvar(@Valid @RequestBody ClienteProduto clienteProduto){
        clienteProduto.setId(null);
        clienteProduto.setStatus(true);
        clienteProduto = service.salvar(clienteProduto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(clienteProduto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
