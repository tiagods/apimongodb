package com.tiagods.apimongodb.repository;

import com.tiagods.apimongodb.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente,String> {

    Optional<Cliente> findByCpf(long cpf);

    List<Cliente> findByNome(String nome);
}
