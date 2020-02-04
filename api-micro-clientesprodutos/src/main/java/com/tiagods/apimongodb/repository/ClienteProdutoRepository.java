package com.tiagods.apimongodb.repository;

import com.tiagods.apimongodb.model.ClienteProduto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteProdutoRepository extends MongoRepository<ClienteProduto,String> {
    Optional<ClienteProduto> findByClienteIdAndProdutoIdAndStatusIsTrue(String clienteId, String produtoId);
}
