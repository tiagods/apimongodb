package com.tiagods.apimongodb.controller;

import com.tiagods.apimongodb.model.Cliente;
import com.tiagods.apimongodb.service.ClienteService;
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
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @ApiOperation(value = "Retorna uma lista de clientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de clientes"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<?> listarTodos(){
        return ResponseEntity.ok().body(clienteService.listar());
    }

    @ApiOperation(value = "Retorna um cliente pelo numero do cpf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de clientes por nome"),
            @ApiResponse(code = 404, message = "Cliente não existe na base"),
            @ApiResponse(code = 400, message = "Parametro nulo ou invalido"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{cpf}/cpf")
    public ResponseEntity<?> buscarPorCpf(@Valid @PathVariable long cpf){
        return ResponseEntity.ok().body(clienteService.buscarPorCpf(cpf));
    }

    @ApiOperation(value = "Retorna um cliente pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um cliente"),
            @ApiResponse(code = 404, message = "Cliente não existe na base"),
            @ApiResponse(code = 400, message = "Cliente com parametro nulo ou invalido"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@Valid @PathVariable String id){
        return ResponseEntity.ok().body(clienteService.buscarPorId(id));
    }

    @ApiOperation(value = "Retorna um cliente pelo nome")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de clientes por nome"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{nome}/nome")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome){
        return ResponseEntity.ok().body(clienteService.buscarPorNome(nome));
    }
    @ApiOperation(value = "Atualiza um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Retorna NO_CONTENT caso cliente for atualizado"),
            @ApiResponse(code = 404, message = "Cliente não existe na base"),
            @ApiResponse(code = 400, message = "Cliente com parametro nulo ou invalido"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @Valid @RequestBody Cliente cliente){
        clienteService.atualizar(id,cliente);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Salvar um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna Created caso cliente for salvo"),
            @ApiResponse(code = 403, message = "Cpf duplicado, ja existe um cliente com o cpf informado"),
            @ApiResponse(code = 400, message = "Cliente com parametro nulo ou invalido"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<?> salvar(@Valid @RequestBody Cliente cliente){
        cliente.setId(null);
        cliente = clienteService.salvar(cliente);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @ApiOperation(value = "Deleta um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Retorna NO_CONTENT caso cliente for deletado"),
            @ApiResponse(code = 404, message = "Cliente não existe na base"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id){
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
