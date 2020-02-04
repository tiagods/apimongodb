package com.tiagods.apimongodb.service;

import com.tiagods.apimongodb.model.ClienteProduto;
import com.tiagods.apimongodb.exception.ClienteProdutoNotFoundException;
import com.tiagods.apimongodb.repository.ClienteProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteProdutoService {

    @Autowired
    private ClienteProdutoRepository repository;

    @Autowired
    private ClienteRestClient clienteRest;

    @Autowired
    private ProdutoRestClient produtoRest;

    public List<ClienteProduto> listar(){
        return repository.findAll();
    }

    public ClienteProduto salvar(ClienteProduto clienteProduto){
        validarRegistros(clienteProduto);
        buscarExistenteEDesativarStatus(clienteProduto);
        return repository.save(clienteProduto);
    }

    /*
    Valida se o registro ja existe, se sim, irá atualizar o status para false
     */
    public void buscarExistenteEDesativarStatus(ClienteProduto clienteProduto){
        Optional<ClienteProduto> op = repository.findByClienteIdAndProdutoIdAndStatusIsTrue(clienteProduto.getClienteId(), clienteProduto.getProdutoId());
        if(op.isPresent()){
            ClienteProduto result = op.get();
            result.setStatus(false);
            repository.save(result);
        }
    }

    public void validarRegistros(ClienteProduto clienteProduto) {
        try {
            clienteRest.buscarCliente(clienteProduto.getClienteId());
            produtoRest.buscarProduto(clienteProduto.getProdutoId());
        }catch (feign.FeignException ex){
            throw new ClienteProdutoNotFoundException("Não existe cliente ou id com os parametros informados");
        }
    }
    public ClienteProduto buscarPorId(String id) {
        Optional<ClienteProduto> opt = repository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        else{
            throw new ClienteProdutoNotFoundException("Não existe umm registro com o id informado");
        }
    }
}
