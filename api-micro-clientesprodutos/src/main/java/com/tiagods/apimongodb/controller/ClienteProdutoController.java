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

    @ApiOperation(value = "Retorna uma lista de produtos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de produtos"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<?> listarTodos(){
        return ResponseEntity.ok().body(service.listar());
    }


    @ApiOperation(value = "Retorna um produto pelo nome")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de produtos por nome"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{nome}/nome")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome){
        return ResponseEntity.ok().body(service.buscarPorNome(nome));
    }

    @ApiOperation(value = "Salvar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna Created caso produto for salvo"),
            @ApiResponse(code = 403, message = "Nome duplicado, ja existe um produto com o cpf informado"),
            @ApiResponse(code = 400, message = "Produto com parametro nulo ou invalido"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<?> salvar(@Valid @RequestBody ClienteProduto clienteProduto){
        clienteProduto.setId(null);
        clienteProduto = service.salvar(clienteProduto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(clienteProduto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @ApiOperation(value = "Deleta um produto")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Retorna NO_CONTENT caso produto for deletado"),
            @ApiResponse(code = 404, message = "Produto não existe na base"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
