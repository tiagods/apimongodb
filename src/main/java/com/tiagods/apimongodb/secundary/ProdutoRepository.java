package com.tiagods.apimongodb.secundary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto,String> {
    public Optional<Produto> findByNome(String nome);
}
