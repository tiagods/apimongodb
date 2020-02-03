package com.tiagods.apimongodb.controller;

import com.tiagods.apimongodb.Produto;
import com.tiagods.apimongodb.secundary.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

//@RestController
//@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @ApiOperation(value = "Retorna uma lista de produtos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de produtos"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<?> listarTodos(){
        return ResponseEntity.ok().body(service.listar());
    }

    @ApiOperation(value = "Retorna um produto pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de produtos por id"),
            @ApiResponse(code = 404, message = "Produto não existe na base"),
            @ApiResponse(code = 400, message = "Produto com parametro nulo ou invalido"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@Valid @PathVariable String id){
        return ResponseEntity.ok().body(service.buscarPorId(id));
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
    @ApiOperation(value = "Atualiza um produto")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Retorna NO_CONTENT caso produto for atualizado"),
            @ApiResponse(code = 404, message = "Produto não existe na base"),
            @ApiResponse(code = 400, message = "Produto com parametro nulo ou invalido"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @Valid @RequestBody Produto produto){
        service.atualizar(id,produto);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Salvar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna Created caso produto for salvo"),
            @ApiResponse(code = 403, message = "Nome duplicado, ja existe um produto com o cpf informado"),
            @ApiResponse(code = 400, message = "Produto com parametro nulo ou invalido"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<?> salvar(@Valid @RequestBody Produto produto){
        produto.setId(null);
        produto = service.salvar(produto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(produto.getId()).toUri();
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
