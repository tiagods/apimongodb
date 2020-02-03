package com.tiagods.apimongodb.service;

import com.tiagods.apimongodb.model.ClienteProduto;
import com.tiagods.apimongodb.exception.ProdutoJaExisteException;
import com.tiagods.apimongodb.exception.ProdutoNotFoundException;
import com.tiagods.apimongodb.repository.ClienteProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteProdutoService {

    @Autowired
    private ClienteProdutoRepository repository;

    public List<ClienteProduto> listar(){
        return repository.findAll();
    }

    public ClienteProduto salvar(ClienteProduto clienteProduto){
        try {
            ClienteProduto p = buscarPorNome(clienteProduto.getNome());
        } catch (ProdutoNotFoundException e) {
            clienteProduto.setNome(clienteProduto.getNome().toUpperCase());
            return repository.save(clienteProduto);
        }
    }

    public ClienteProduto buscarPorId(String id){
        Optional<ClienteProduto> optional = repository.findById(id);
        if(optional.isPresent())
            return optional.get();
        else throw new ProdutoNotFoundException("Produto não existe na base de dados");
    }

    public void atualizar(String id, ClienteProduto clienteProduto) {
        buscarPorId(id);
        clienteProduto.setId(id);
        repository.save(clienteProduto);
    }

    public void deletar(String id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

}
