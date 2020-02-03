package com.tiagods.apimongodb.service;

import com.tiagods.apimongodb.model.Produto;
import com.tiagods.apimongodb.exception.ProdutoJaExisteException;
import com.tiagods.apimongodb.exception.ProdutoNotFoundException;
import com.tiagods.apimongodb.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> listar(){
        return repository.findAll();
    }

    public Produto salvar(Produto produto){
        try {
            Produto p = buscarPorNome(produto.getNome());
            throw new ProdutoJaExisteException("Produto com mesmo nome ja existe na base foi informado para o cliente "+p.getNome());
        } catch (ProdutoNotFoundException e) {
            produto.setNome(produto.getNome().toUpperCase());
            return repository.save(produto);
        }
    }

    public Produto buscarPorId(String id){
        Optional<Produto> optional = repository.findById(id);
        if(optional.isPresent())
            return optional.get();
        else throw new ProdutoNotFoundException("Produto não existe na base de dados");
    }

    public void atualizar(String id, Produto produto) {
        buscarPorId(id);
        produto.setId(id);
        repository.save(produto);
    }

    public void deletar(String id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    public Produto buscarPorNome(String nome) {
        Optional<Produto> optional = repository.findByNome(nome);
        if(optional.isPresent()) return optional.get();
        else
            throw new ProdutoNotFoundException("Produto não existe na base de dados");
    }
}
